# OJ-LION
## 前言

`OJ-LION`:全栈OJ系统，自动判题，ai生成题，检错，评论注册登录，等功能。

## 项目介绍

`OJ-LION`项目是一套OJ判题系统,docker沙箱,本地沙箱,用户判题返回的一套系统包含前后端，前端采用权限。

### 搭建步骤

> Windows(本地搭建)
- git clone https://github.com/Musicys
- 前端（lioj-fonont）：npm i 或者 yarn i
- 运行npm run serve 打包 npm run bulid `部署推荐Nginx`
- 后端（li-code-sandbox-master）：配置 .env配置 数据库，启动 打包即可
- 后端沙箱(本地li-code-sandbox-master)：可以使用第三方远程沙箱，或者docker配置沙箱



### 项目演示
`管理员账号`：liwang
`密码`：12345678
项目演示地址： [http://101.42.172.99/](http://101.42.172.99/)  

``` 请勿dos攻击，服务器崩溃不在上线```


# 系统界面展示

### 注册界面
![用户注册界面](./ui/注册.png)  
- **功能**：新用户通过邮箱/手机号注册账号
- **亮点**：实时密码强度检测 + 图形验证码验证
### 登录界面
![用户登录界面](./ui/登录.png)  
- **功能**：支持账号密码/短信验证码双模式登录
### 未登录门户页
![系统首页](/ui/首页（未登录）.png)  
- **设计**：响应式瀑布流布局
- **内容**：平台特色展示/热门题目榜单
- **动效**：卡片悬停3D翻转效果

### 个人信息管理
![用户信息界面](./ui/用户信息.png)  
- **功能**：头像裁剪上传、绑定社交账号、修改安全设置
- **技术**：基于Ant Design Vue表单组件实现
---
## 核心功能模块
### 普通用户主页
![用户主页](./ui/普通用户.png)  
- **布局**：仪表盘式设计展示提交统计/积分排名
- **交互**：可拖拽卡片实现界面个性化定制
- 
### 题目交流区
![交流界面](./ui/用户回复版.png)  
- **功能**：Markdown富文本讨论区
- **特色**：@用户通知 + 代码片段分享功能
### 提交记录
![提交记录](./ui/普通提交）.png)  
- **展示**：代码执行状态/耗时/内存消耗对比
- **扩展**：支持提交记录导出为CSV文件
---
## 管理后台模块
### 数据仪表盘
![管理面板1](./ui/管理员%20(1).png)  
- **监控**：实时显示系统健康度/访问量趋势
### Ai题目生成
![管理面板2](./ui/管理员%20(2).png)  
- **功能**：批量操作/权限分配/登录日志查询
- **安全**：敏感操作二次验证机制
### 题目管理界面
![管理面板3](./ui/管理员%20(3).png)  
- **编辑器**：支持Markdown+LaTeX混合编排
- **测试**：可视化测试用例配置工具
### 内容审核中心
![管理面板4](./ui/管理员%20(4).png)  
- **机制**：AI预审（敏感词/代码查重）+人工复核
- **效率**：支持批量通过/驳回操作

---

## 题目交互系统
### 题目列表页
![题目列表](./ui/题目页面%20(1).png)  
- **筛选**：难度标签/通过率区间/热门排序
- **标识**：每日精选题目角标提示

### 题目详情页
![题目详情](./ui/题目页面%20(2).png)  
- **组件**：交互式示例代码沙盒
- **辅助**：题目收藏/讨论区快捷入口

### 在线代码编辑器
![代码编辑](./ui/题目页面%20(3).png)  
- **工具**：调试用例/历史版本对比/模板库

---

## 系统首页

> 注：所有图片路径基于`/ul/`目录，实际使用请确保路径配置正确。界面采用Ant Design Vue 4.x设计规范，主色系为科技蓝（#1890ff）+ 活力橙（#ff7a45）搭配。
### 界面设计规范
1. **视觉层级**：使用Ant Design Vue组件库，符合WCAG 2.1无障碍标准








### 技术选型
#### 后端技术

| 技术                      | 说明                | 官网                                                         |
| ------------------------- | ------------------- | ------------------------------------------------------------ |
| Spring Boot 2.7.2         | Web应用开发框架     | [官网](https://spring.io/projects/spring-boot)               |
| MyBatis-Plus 3.5.2        | 增强型ORM框架       | [官网](https://baomidou.com/)                                |
| Spring Data Redis         | Redis集成           | [官网](https://spring.io/projects/spring-data-redis)         |
| Spring Data Elasticsearch | 搜索引擎集成        | [官网](https://spring.io/projects/spring-data-elasticsearch) |
| Knife4j 3.0.3             | Swagger增强文档工具 | [官网](https://doc.xiaominfo.com/)                           |
| Hutool 5.8.8              | Java工具类库        | [官网](https://hutool.cn/)                                   |
| Lombok                    | 代码简化库          | [官网](https://projectlombok.org/)                           |
| Spring Mail               | 邮件服务支持        | [官网](https://spring.io/projects/spring-mail)               |
| 腾讯云COS SDK 5.6.89      | 对象存储服务        | [文档](https://cloud.tencent.com/document/product/436)       |
| EasyExcel 3.1.1           | Excel读写工具       | [官网](https://easyexcel.opensource.alibaba.com/)            |
| OkHttp 4.10.0             | HTTP客户端          | [官网](https://square.github.io/okhttp/)                     |
| Gson 2.9.1                | JSON序列化库        | [GitHub](https://github.com/google/gson)                     |
| WxJava 4.4.0              | 微信公众号开发SDK   | [GitHub](https://github.com/Wechat-Group/WxJava)             |
| Spring AOP                | 面向切面编程        | [官网](https://spring.io/projects/spring-aop)                |
#### 前端技术

| 技术                 | 说明               | 官网                                               |
| -------------------- | ------------------ | -------------------------------------------------- |
| Vue 3                | 前端框架           | [官网](https://vuejs.org/)                         |
| Vue Router 4         | 路由管理           | [官网](https://router.vuejs.org/)                  |
| Vuex 4               | 全局状态管理       | [官网](https://vuex.vuejs.org/)                    |
| Ant Design Vue 4.2.6 | UI组件库           | [文档](https://www.antdv.com/docs/vue/introduce)   |
| Axios 1.8.1          | HTTP请求库         | [官网](https://axios-http.com/)                    |
| ECharts 5.6.0        | 数据可视化图表库   | [官网](https://echarts.apache.org/)                |
| Monaco Editor 0.41.0 | 代码编辑器         | [官网](https://microsoft.github.io/monaco-editor/) |
| ByTMD 1.22.0         | Markdown编辑器插件 | [GitHub](https://github.com/bytedance/bytemd)      |
| Moment 2.29.4        | 时间处理库         | [官网](https://momentjs.com/)                      |


### 开发环境

| 工具  | 版本号 | 下载                                                                                 |
| ----- | ------ | ------------------------------------------------------------------------------------ |
| JDK   | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| MySQL | 5.7    | https://www.mysql.com/                                                               |
| Redis | 7.0    | https://redis.io/download                                                            |
| Nginx | 1.22   | http://nginx.org/en/download.html                                                    |

