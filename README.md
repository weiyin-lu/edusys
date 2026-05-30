# 高校学生军训信息化管理系统

本人2020 年毕业设计作品。面向高校新生军训的服装、考勤、成绩全流程信息化管理平台。

## 项目背景

高校学生军训是必修课程，通常 14-30 天，涉及筹备、管理、教学、考核等多个环节，任务量大。以辽宁工程技术大学为例，每年参训学生近 3000 人，服装统计靠人工、分发需 1-2 天且缺码断号频发，考勤与成绩由教官手写记录后人工汇总核算，数据追溯困难。本系统旨在通过信息化手段解决上述问题。

系统采用 B/S 架构 + RBAC 权限模型，按三端分离设计：

| 终端 | 前端项目 | 用户 | 职责 |
|---|---|---|---|
| 后台系统 | `edusys-admin-master` | 管理人员 | 系统配置、人员管理、服装审核、考勤管理、成绩核算 |
| 尺码登记终端 | `edusys-vue-master` | 学生 | 自助登记身高体重，自动匹配服装尺码 |
| 教官管理终端 | `edusys-client-master` | 教官 | 查看连队学生，录入考勤与科目成绩 |

## 技术栈

| 层级 | 技术 |
|---|---|
| 后端框架 | Spring Boot 3.1.1 (Java 17) + Maven |
| ORM | MyBatis-Flex |
| 鉴权 | Sa-Token（RBAC 模型） |
| 数据库 | MySQL 8.0 + Redis |
| 文件处理 | EasyExcel |
| API 文档 | Knife4j / SpringDoc OpenAPI |
| 前端框架 | Vue 3 + Vue Router + Vuex |
| 构建工具 | Vite |
| 后台 UI | Element Plus |
| 自助端 UI | LayUI Vue |
| 部署方案 | Docker + Nginx（容器化部署） |

## 系统架构

```
edusys/
├── edusys-java-master/    # 后端 API 服务 (Spring Boot, 端口 8080)
├── edusys-admin-master/   # 后台系统 (Element Plus)
├── edusys-client-master/  # 教官管理终端 (Element Plus)
└── edusys-vue-master/     # 尺码登记终端 (LayUI Vue)
```

后端按分层架构设计：Controller（按 admin/authorize/humanManage/serviceManage 分域）→ Service → Mapper，统一 `Result<T>` 响应格式，全局异常拦截器兜底。

## 功能模块

### 系统配置管理
- 角色配置：管理角色及其持有的菜单和权限
- 菜单配置：维护系统菜单数据
- 权限配置：维护系统权限数据
- 组织配置：管理机构信息

### 人员信息管理
- 学生信息管理：维护学生基本数据
- 工作人员管理：维护工作人员账号及权限配置

### 服装业务管理
- 尺码登记：学生自助填写身高/体重/鞋码，自动匹配服装尺码
- 字典管理：维护服装尺码转换规则
- 服装审核：审核发放信息，展示统计指标

### 军训业务管理
- 指导文件管理：上传和维护军训指导文件
- 人员分配管理：管理学生与教官的连队分配
- 考勤综合管理：录入与查看学生考勤
- 军训成绩核算：科目成绩录入、总分核算与查询

## 快速启动

### 前置条件

- JDK 17+, Maven 3.8+
- Node.js 18+, npm
- MySQL 8.0+
- Redis

### 1. 数据库

创建 MySQL 数据库并导入表结构：

```sql
CREATE DATABASE edusys DEFAULT CHARACTER SET utf8mb4;
```

### 2. 后端

```bash
cd edusys-java-master

# 修改 application-dev.yml 中的数据库连接和 Redis 配置
# 修改 spring.servlet.multipart.location 为实际上传路径

mvn spring-boot:run
```

启动后访问：
- API 文档: http://localhost:8080/doc.html
- Swagger: http://localhost:8080/swagger-ui.html

### 3. 前端

```bash
# 后台系统
cd edusys-admin-master && npm install && npm run dev

# 教官管理终端
cd edusys-client-master && npm install && npm run dev

# 尺码登记终端
cd edusys-vue-master && npm install && npm run dev
```

## 目录说明

```
edusys/
├── edusys-java-master/       # 后端 API（Spring Boot）
│   ├── src/main/java/online/weiyin/edusys/
│   │   ├── controller/       # 按 admin/authorize/humanManage/serviceManage 分域
│   │   ├── service/          # 业务逻辑
│   │   ├── mapper/           # MyBatis-Flex 数据访问
│   │   ├── entity/           # DTO/表实体/视图
│   │   ├── common/           # Result 统一响应、Code 错误码
│   │   ├── config/           # CORS/MyBatis/Redis/Swagger/Sa-Token 配置
│   │   └── interceptor/      # 全局异常拦截器
│   ├── src/main/resources/
│   │   ├── application-dev.yml  # 开发环境配置（含数据库/Redis/上传路径）
│   │   └── mapper/              # MyBatis XML（ScoreMapper, SourceMapper）
│   └── upload/               # 运行时文件存储（指导文件/头像/Excel模板）
├── edusys-admin-master/      # 后台系统（Element Plus）
├── edusys-client-master/     # 教官管理终端（Element Plus）
└── edusys-vue-master/        # 尺码登记终端（LayUI Vue）
```

## License

本项目基于 GPLv3 修改的定制许可证发布，**严格禁止商业用途**。详见 [LICENSE](LICENSE)。
