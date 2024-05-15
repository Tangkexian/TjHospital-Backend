目前项目中包含6个层。

- config

    用来存放配置文件

- controller

    用来存放和用户交互部分的控制器类

- service

    介于controller和repository两层之间，对数据进行处理

- repository

    对接数据库，包含具体的查询方法

- dto

    用来存放封装返回的数据或者接收的数据的类

- entity

    实体类，也就是model类，放数据库对应的表