package com.javaee.onlinehosbackend.service;

import com.javaee.onlinehosbackend.entity.Chatrecord;
import com.javaee.onlinehosbackend.entity.ChatrecordId;
import com.javaee.onlinehosbackend.repository.ChatrecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatrecordService {

    @Autowired
    private ChatrecordRepository chatrecordRepository;

    public List<Chatrecord> getChatRecords(String recordId) {
        return chatrecordRepository.findByRecordidOrderByTimestampAsc(recordId);
    }

    public boolean existsById(ChatrecordId chatrecordId){
        return chatrecordRepository.existsById(chatrecordId);
    }

    public Chatrecord addChatRecord(Chatrecord chatRecord) {
        return chatrecordRepository.save(chatRecord);
    }
}
