# Ecloud WeChat Java SDK

这个Java SDK为与Ecloud E云管家 WeChat API交互提供了一种简化方式。它提供了多种功能来管理和自动化与微信的互动，包括好友管理、消息处理、群操作等。

## 功能

- **好友管理**: 添加、删除、修改好友，管理好友列表和标签。
- **消息处理**: 发送和接收文本、图片、文件等类型的消息。
- **群操作**: 创建、管理和互动群组。
- **朋友圈**: 发布、点赞和管理微信朋友圈。

## 安装

```shell
mvn clean install
```

## 使用方法

1. **初始化SDK**:
    ```java
   import com.lambdua.ecloud.ECloudService;
   ECloudService service = new ECloudService("token","baseUrl");
    ```

2. **发送文本消息**:
    ```java

service.sendTextMsg(
SendRequest.builder()
.wId(message.getWId())
.wcId(message.getFromUser())
.content("hello word")
.build()
);

```

## 文档

详细的API文档，请访问 [Ecloud WeChat API Docs](https://wkteam.cn/).

## 贡献

欢迎贡献！请在 [GitHub](https://github.com/Lambdua/ecloud-wechat-java-sdk) 提交issue或pull request。

## 许可证

此项目根据MIT许可证授权。详情请参见 [LICENSE](LICENSE) 文件。
