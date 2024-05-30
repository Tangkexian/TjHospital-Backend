package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.dto.*;
import com.javaee.onlinehosbackend.entity.*;
import com.javaee.onlinehosbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicineService {

    @Autowired
    private MedicineDescriptionRepository medicineDescriptionRepository;

    @Autowired
    private MedicineOutRepository medicineOutRepository;

    @Autowired
    private MedicinePurchaseRepository medicinePurchaseRepository;

    @Autowired
    private MedicineSellRepository medicineSellRepository;

    @Autowired
    private MedicineStockRepository medicineStockRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    public List<MedicineResponse> getAllMedicinesInfo() {
        // 从MedicineDescriptionRepository获取药品描述信息
        List<MedicineDescription> medicineDescriptions = medicineDescriptionRepository.findAll();

        // 从MedicineSellRepository获取销售价格信息
        List<MedicineSell> medicineSells = medicineSellRepository.findAll();

        // 从MedicineStockRepository获取库存信息
        List<MedicineStock> medicineStocks = medicineStockRepository.findAll();

        // 使用Java Stream API进行数据整合和映射到MedicineResponse对象

        return medicineStocks.stream()
                .filter(stock -> stock.getMedicineAmount() != 0)
                .map(stock -> {
                    // 使用匹配条件来查找MedicineDescription
                    MedicineDescription description = medicineDescriptions.stream()
                            .filter(desc -> desc.getMedicineName().equals(stock.getId().getMedicineName()))
                            .findFirst()
                            .orElse(null);

                    // 使用匹配条件来查找MedicineSell
                    MedicineSell sell = medicineSells.stream()
                            .filter(s -> s.getId().getMedicineName().equals(stock.getId().getMedicineName()))
                            .findFirst()
                            .orElse(null);

                    // 创建MedicineResponse对象并映射数据
                    MedicineResponse response = new MedicineResponse();
                    response.setMedicineName(stock.getId().getMedicineName());
                    response.setManufacturer(stock.getId().getManufacturer());
                    response.setSellingPrice((sell != null) ? sell.getSellingPrice() : null);
                    response.setMedicineType((description != null) ? description.getMedicineType() : null);
                    response.setApplicableSymptom((description != null) ? description.getApplicableSymptom() : null);
                    response.setVulgo((description != null) ? description.getVulgo() : null);
                    response.setSpecification((description != null) ? description.getSpecification() : null);
                    response.setSingledose((description != null) ? description.getSingledose() : null);
                    response.setAdministration((description != null) ? description.getAdministration() : null);
                    response.setAttention((description != null) ? description.getAttention() : null);
                    response.setFrequency((description != null) ? description.getFrequency() : null);

                    return response;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public String updateStock(String medicineName, String manufacturer, LocalDate productionDate, BigDecimal newAmount, String patientId) {
        validateStockUpdateParams(newAmount, medicineName, manufacturer, patientId);

        MedicineStockId medicineStockId = new MedicineStockId();
        medicineStockId.setMedicineName(medicineName);
        medicineStockId.setManufacturer(manufacturer);
        medicineStockId.setProductionDate(productionDate);
        MedicineStock medicineStock = medicineStockRepository.findById(medicineStockId)
                .orElseThrow(EntityNotFoundException::new);

        BigDecimal purchaseAmount = calculatePurchaseAmount(medicineStock, newAmount);
        updateMedicineStock(medicineStock, newAmount);
        recordMedicineOut(medicineName, manufacturer, productionDate, purchaseAmount, patientId);

        return "Medicine stock and purchase record updated successfully.";
    }

    private void validateStockUpdateParams(BigDecimal newAmount, String medicineName, String manufacturer, String patientId) {
        if (newAmount.signum() < 0 || medicineName.isEmpty() || manufacturer.isEmpty() || patientId.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
    }

    private BigDecimal calculatePurchaseAmount(MedicineStock medicineStock, BigDecimal newAmount) {
        BigDecimal temp = BigDecimal.valueOf(medicineStock.getMedicineAmount());
        return temp.subtract(newAmount);
    }

    private void updateMedicineStock(MedicineStock medicineStock, BigDecimal newAmount) {
        medicineStock.setMedicineAmount(newAmount.intValue());
        medicineStockRepository.save(medicineStock);
    }

    private void recordMedicineOut(String medicineName, String manufacturer, LocalDate productionDate, BigDecimal purchaseAmount, String patientId) {
        MedicineOutId medicineOutId = new MedicineOutId();
        medicineOutId.setMedicineName(medicineName);
        medicineOutId.setManufacturer(manufacturer);
        medicineOutId.setProductionDate(productionDate);
        medicineOutId.setPurchaseAmount(purchaseAmount);
        medicineOutId.setDeliverDate(Instant.now());
        medicineOutId.setPatientId(patientId);

        MedicineOut medicineOut = new MedicineOut();
        medicineOut.setId(medicineOutId);
        Patient patient = patientRepository.findById(patientId).orElseThrow(EntityNotFoundException::new);
        medicineOut.setPatient(patient);
        medicineOutRepository.save(medicineOut);
    }

    @Transactional
    public String addStock(MedicineStockRequest medicineStockRequest) {
        MedicineStockId stockId = new MedicineStockId(medicineStockRequest.getMedicineName(), medicineStockRequest.getManufacturer(), medicineStockRequest.getProductionDate());
        MedicineStock existingStock = medicineStockRepository.findById(stockId).orElse(null);

        if (existingStock != null) {
            existingStock.setMedicineAmount(existingStock.getMedicineAmount() + medicineStockRequest.getMedicineAmount().intValue());
        } else {
            existingStock = new MedicineStock();
            existingStock.setId(stockId);
            existingStock.setMedicineShelflife(medicineStockRequest.getMedicineShelflife().intValue());
            existingStock.setMedicineAmount(medicineStockRequest.getMedicineAmount().intValue());
            existingStock.setThresholdValue(medicineStockRequest.getThresholdValue().intValue());
        }
        medicineStockRepository.save(existingStock);
        Administrator administrator = administratorRepository.findById(medicineStockRequest.getAdministratorId()).orElse(null);
        if (administrator == null) {
            return "Not found this administrator";
        }
        MedicinePurchase newPurchase = new MedicinePurchase();
        newPurchase.setId(new MedicinePurchaseId(medicineStockRequest.getMedicineName(), medicineStockRequest.getManufacturer(), medicineStockRequest.getProductionDate(), LocalDate.now()));
        newPurchase.setAdministrator(administrator);
        newPurchase.setPurchaseAmount(medicineStockRequest.getMedicineAmount());
        newPurchase.setPurchasePrice(medicineStockRequest.getPurchasePrice());
        medicinePurchaseRepository.save(newPurchase);

        MedicineDescription newDescription = medicineDescriptionRepository.findById(medicineStockRequest.getMedicineName()).orElse(new MedicineDescription());
        newDescription.setMedicineName(medicineStockRequest.getMedicineName());
        newDescription.setMedicineType(medicineStockRequest.getMedicineType());
        newDescription.setApplicableSymptom(medicineStockRequest.getApplicableSymptom());
        newDescription.setSpecification(medicineStockRequest.getSpecification());
        newDescription.setSingledose(medicineStockRequest.getSingledose());
        newDescription.setAdministration(medicineStockRequest.getAdministration());
        newDescription.setAttention(medicineStockRequest.getAttention());
        newDescription.setFrequency(medicineStockRequest.getFrequency());
        medicineDescriptionRepository.save(newDescription);

        MedicineSell newSell = medicineSellRepository.findById(new MedicineSellId(medicineStockRequest.getMedicineName(), medicineStockRequest.getManufacturer())).orElse(new MedicineSell());
        newSell.setId(new MedicineSellId(medicineStockRequest.getMedicineName(), medicineStockRequest.getManufacturer()));
        newSell.setSellingPrice(medicineStockRequest.getSellingprice());
        System.out.println(medicineStockRequest.getSellingprice());
        medicineSellRepository.save(newSell);

        return "成功";
    }

    @Transactional
    public String cleanMedicine(String medicineName, String manufacturer, LocalDate productionDate, String administratorId) {
        // 使用 MedicineStockId 对象创建主键实例
        MedicineStockId stockId = new MedicineStockId(medicineName, manufacturer, productionDate);
        MedicineStock stock = medicineStockRepository.findById(stockId).orElse(null);

        if (stock == null) {
            return "未找到该药品库存";
        }

        Administrator administrator = administratorRepository.findById(administratorId).orElse(null);
        if (administrator == null) {
            return "Not found this administrator";
        }

        stock.setCleanDate(LocalDate.now());
        stock.setCleanAdministrator(administrator);
        stock.setMedicineAmount(0);
        medicineStockRepository.save(stock);

        checkAndRemoveMedicineSellInfo(medicineName, manufacturer);

        return "药品清理成功";
    }

    private void checkAndRemoveMedicineSellInfo(String medicineName, String manufacturer) {
        List<MedicineStock> allStocks = medicineStockRepository.findByMedicineNameAndManufacturer(medicineName, manufacturer);

        if (allStocks.stream().allMatch(stock -> stock.getCleanDate() != null)) {
            MedicineSellId sellId = new MedicineSellId(medicineName, manufacturer);
            Optional<MedicineSell> sellOpt = medicineSellRepository.findById(sellId);
            sellOpt.ifPresent(medicineSellRepository::delete);
        }
    }

    public List<CleanedMedicineResponse> getCleanedMedicines() {
        List<MedicineStock> cleanedMedicines = medicineStockRepository.findByCleanDateNotNull();
        return cleanedMedicines.stream().map(stock -> {
            Administrator administrator = administratorRepository.findById(stock.getCleanAdministrator().getAdministratorId()).orElse(null);
            return new CleanedMedicineResponse(
                    stock.getId().getMedicineName(),
                    stock.getCleanDate(),
                    stock.getCleanAdministrator().getAdministratorId(),
                    administrator != null ? administrator.getName() : null,
                    stock.getId().getManufacturer(),
                    stock.getId().getProductionDate()
            );
        }).collect(Collectors.toList());
    }


    public MedicineStatisticsResponse getMedicineStatistics() {
        List<PurchaseStatistic> purchaseStatistics = medicinePurchaseRepository.calculateMonthlyPurchaseStatistics();
        List<SellStatistic> sellStatistics = medicineOutRepository.calculateMonthlySellStatistics();
        return new MedicineStatisticsResponse(purchaseStatistics, sellStatistics);
    }

    public List<MedicineStock> getAllMedicineStocks() {
        return medicineStockRepository.findByCleanDateIsNull();
    }
}
