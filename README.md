# 计算机网络用户管理系统

基于SpringBoot+MyBatis+PageHelper的用户管理系统，用于计算机网络学习平台的用户管理。

## 技术栈

- Spring Boot 3.4.4
- MyBatis
- PageHelper
- JWT
- MySQL

## 功能特性

- 用户注册
- 用户登录（支持不同角色：学生、教师、管理员）
- 基于JWT的身份验证

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 5.7+

### 数据库配置

系统使用已有的`cnlearning`数据库，确保该数据库已经存在并包含`user`表。

数据库配置信息：
- URL: `jdbc:mysql://127.0.0.1:3306/cnlearning?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false`
- 用户名: `root`
- 密码: `20020627zwl`

### 运行应用

```bash
mvn spring-boot:run
```

应用将在 http://localhost:8080 上运行。

## API文档

### 测试接口

- GET `/test` - 测试应用是否正常运行

### 用户接口

- POST `/register` - 用户注册
  - 请求体：
    ```json
    {
      "username": "stu002",
      "password": "pass002",
      "role": "学生"
    }
    ```
  - 响应：
    ```json
    {
      "code": 1,
      "msg": "注册成功"
    }
    ```

- POST `/login` - 用户登录
  - 请求体：
    ```json
    {
      "username": "stu001",
      "password": "pass001",
      "role": "学生"
    }
    ```
  - 响应：
    ```json
    {
      "code": 1,
      "msg": "登录成功",
      "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "userId": "1",
        "username": "stu001",
        "role": "学生"
      }
    }
    ```

## 预设账户

系统预设了三个测试账户，密码均为 `123456`：

1. 管理员账户：`admin001`
2. 教师账户：`teacher001`
3. 学生账户：`student001`

### 资源接口

- POST `/resource/upload` - 上传资源
  - 请求体：
    ```
    POST /resource/upload
    Content-Type: multipart/form-data
    Authorization: Bearer {JWT_TOKEN}
    
    title=TCP三次握手&type=视频&file=@tcp_handshake.mp4
    ```
  - 响应：
    ```json
    {
      "code": 1,
      "msg": "资源上传成功"
    }
    ```

## 预设资源

系统预设了两个测试资源，分别是：

1. 视频资源：`TCP三次握手`
2. 课件资源：`TCP三次握手`

GET /resources?page=1&pageSize=10&type=视频 