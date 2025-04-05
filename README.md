# OJ-LION

<p>
  <a href="#公众号"><img src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E5%85%AC%E4%BC%97%E5%8F%B7-macrozheng-blue.svg" alt="公众号"></a>
  <a href="#公众号"><img src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E4%BA%A4%E6%B5%81-%E5%BE%AE%E4%BF%A1%E7%BE%A4-2BA245.svg" alt="交流"></a>
  <a href="https://github.com/macrozheng/mall-learning"><img src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E5%AD%A6%E4%B9%A0%E6%95%99%E7%A8%8B-mall--learning-green.svg" alt="学习教程"></a>
  <a href="https://github.com/macrozheng/mall-swarm"><img src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/Cloud%E7%89%88%E6%9C%AC-mall--swarm-brightgreen.svg" alt="SpringCloud版本"></a>
  <a href="https://github.com/macrozheng/mall-admin-web"><img src="https://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F-mall--admin--web-green.svg" alt="后台管理系统"></a>
  <a href="https://github.com/macrozheng/mall-app-web"><img src="https://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E5%89%8D%E5%8F%B0%E5%95%86%E5%9F%8E%E9%A1%B9%E7%9B%AE-mall--app--web-green.svg" alt="前台商城项目"></a>
  <a href="https://gitee.com/macrozheng/mall"><img src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/badge/%E7%A0%81%E4%BA%91-%E9%A1%B9%E7%9B%AE%E5%9C%B0%E5%9D%80-orange.svg" alt="码云"></a>
</p>


## UI界面

### 一、用户认证模块(/ui目录)
| 界面名称     | 截图文件       | 功能说明                                                           |
| ------------ | -------------- | ------------------------------------------------------------------ |
| 注册页面     | `注册.png`     | 新用户填写账号、密码、验证码等信息完成注册                         |
| 登录页面     | `登录.png`     | 用户通过账号密码登录系统，支持第三方登录入口                       |
| 用户信息管理 | `用户信息.png` | 展示用户基本信息（昵称/头像/联系方式），支持个人信息修改和密码重置 |

### 二、用户功能模块
| 界面名称     | 截图文件         | 功能说明                                          |
| ------------ | ---------------- | ------------------------------------------------- |
| 普通用户主页 | `普通用户.png`   | 用户登录后的主操作界面，展示功能导航和个人动态    |
| 问题交流版   | `用户回复版.png` | 用户之间的问答交流区，支持富文本回复和@功能       |
| 提交记录     | `普通提交）.png` | 显示代码/答案提交历史记录，包含执行结果和耗时统计 |

### 三、管理后台模块
| 界面名称 | 截图文件         | 功能说明                                                 |
| -------- | ---------------- | -------------------------------------------------------- |
| 仪表盘   | `管理员 (1).png` | 管理员工作台，展示系统关键指标（用户量/题目数/日活等）   |
| 用户管理 | `管理员 (2).png` | 用户账号管理界面，支持封禁/权限分配/行为日志查询         |
| 题目管理 | `管理员 (3).png` | 编程题目CRUD操作界面，包含测试用例配置和难度分级         |
| 内容审核 | `管理员 (4).png` | 用户生成内容（评论/题解）审核面板，支持批量通过/驳回操作 |

### 四、题目交互模块
| 界面名称   | 截图文件           | 功能说明                                                        |
| ---------- | ------------------ | --------------------------------------------------------------- |
| 题目列表   | `题目页面 (1).png` | 按分类/难度筛选的题目列表，显示通过率和热度标签                 |
| 题目详情   | `题目页面 (2).png` | 题目描述+示例代码区，支持多语言切换和题目收藏功能               |
| 代码编辑器 | `题目页面 (3).png` | 集成Monaco Editor的在线编码环境，提供调试/提交/历史版本对比功能 |

### 五、首页展示
| 界面名称   | 截图文件             | 功能说明                                           |
| ---------- | -------------------- | -------------------------------------------------- |
| 未登录首页 | `首页（未登录）.png` | 系统入口页面，展示平台特色、热门题目和注册登录入口 |

### 界面设计规范
1. **响应式布局**：支持PC/平板/手机多端适配
2. **视觉层级**：使用Ant Design Vue组件库，符合WCAG 2.1无障碍标准
3. **交互反馈**：关键操作提供Toast提示，表单错误实时校验
4. **主题定制**：通过`scss`变量支持深色/浅色主题切换



## 友情提示

> 1. **快速体验项目**：[在线访问地址]() 。
> 2. **全套学习教程**：[《mall学习教程》]() 。
> 3. **视频教程（最新版）**：[《mall视频教程》]() 。


## 前言

`OJ-LION`:全栈OJ系统，自动判题，ai生成题，检错，评论注册登录，等功能。


## 项目介绍

`OJ-LION`项目是一套OJ判题系统，。

### 项目演示

#### 后台管理系统


项目演示地址： [https://www.macrozheng.com/admin/index.html](https://www.macrozheng.com/admin/index.html)  



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

### 搭建步骤

> Windows环境部署

- Windows环境搭建请参考：[mall项目后端开发环境搭建](https://www.macrozheng.com/mall/start/mall_deploy_windows.html);
- 注意：如果只启动`mall-admin`模块，仅需安装MySQL、Redis即可;
- 克隆`mall-admin-web`项目，并导入到IDEA中完成编译：[前端项目地址](https://github.com/macrozheng/mall-admin-web);
- `mall-admin-web`项目的安装及部署请参考：[mall项目前端发环境搭建](https://www.macrozheng.com/mall/start/mall_deploy_web.html) 。

> Docker环境部署

- 使用虚拟机安装CentOS7.6请参考：[虚拟机安装及使用Linux，看这一篇就够了](https://www.macrozheng.com/mall/deploy/linux_install.html);
- 本项目Docker镜像构建请参考：[使用Maven插件为SpringBoot应用构建Docker镜像](https://www.macrozheng.com/project/maven_docker_fabric8.html);
- 本项目在Docker容器下的部署请参考：[mall在Linux环境下的部署（基于Docker容器）](https://www.macrozheng.com/mall/deploy/mall_deploy_docker.html);
- 本项目使用Docker Compose请参考： [mall在Linux环境下的部署（基于Docker Compose）](https://www.macrozheng.com/mall/deploy/mall_deploy_docker_compose.html);
- 本项目在Linux下的自动化部署请参考：[mall在Linux环境下的自动化部署（基于Jenkins）](https://www.macrozheng.com/mall/deploy/mall_deploy_jenkins.html);

