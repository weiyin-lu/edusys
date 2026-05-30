# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概览

高校学生军训信息化管理系统——面向新生军训的服装、考勤、成绩全流程管理平台。1 个 Java 后端 + 3 个 Vue 3 前端（管理后台、学生端、自助采集端）。

| 目录 | 用途 | 技术栈 |
|---|---|---|
| `edusys-java-master/` | 后端 API | Spring Boot 3.1.1, Java 17, MyBatis-Flex, Sa-Token, MySQL, Redis |
| `edusys-admin-master/` | 后台系统（管理端） | Vue 3, Element Plus, Vite |
| `edusys-client-master/` | 教官管理终端 | Vue 3, Element Plus, Vite |
| `edusys-vue-master/` | 尺码登记终端（学生自助） | Vue 3, LayUI Vue, Vite |

## 系统业务说明

基于辽宁工程技术大学新生军训实际业务流程设计，每年约 3000 名新生参与，军训时长 14 天。核心参与者：学生军训领导小组（统筹管理）、教官（一线教学与考勤）、学生（受训者，含拳术方队、战术方队、国旗护卫队、助训办等特殊连队）。

系统采用 B/S 架构 + RBAC 权限模型，按三端分离设计：

- **后台系统**（`edusys-admin-master`）：系统配置（角色/菜单/权限/组织）、人员信息管理、服装业务（尺码字典→发放→审核→统计）、军训业务（指导文件、人员分配、考勤综合管理、成绩核算）
- **尺码登记终端**（`edusys-vue-master`）：学生自助多步向导（身份验证 → 填写身高体重鞋码 → 自动匹配服装尺码 → 提交）
- **教官管理终端**（`edusys-client-master`）：教官查看连队学生信息、录入考勤与科目成绩

三个前端角色关系：学生通过尺码登记终端提交服装数据 → 管理员在后台审核服装、管理考勤成绩 → 教官在管理终端录入和查看数据

## 常用命令

```bash
# 后端启动 (dev, 端口8080)
cd edusys-java-master && mvn spring-boot:run

# 后端测试
cd edusys-java-master && mvn test

# 任一前端开发服务器启动
cd edusys-<name>-master && npm run dev
cd edusys-<name>-master && npm run build
```

启动后 API 文档: `/doc.html` (Knife4j), `/swagger-ui.html`

## 后端架构 (edusys-java-master)

**基础包路径:** `online.weiyin.edusys`

### 分层结构

- **`controller/`** — 按业务域分目录:
  - `admin/` — 系统管理: 部门/角色/权限/菜单 CRUD 及其关联
  - `authorize/` — 认证: 登录注册(`SignController`)、登录检查、获取用户权限/角色/菜单(`AuthController`)
  - `humanManage/` — 人事管理: 用户、部门、生源 CRUD、用户-角色关联
  - `serviceManage/` — 军训业务: 服装尺码与发放审核、考勤录入与审核、成绩管理、学生分配、字典、文件管理
- **`service/`** — 业务逻辑层，一个接口对应一个 mapper
- **`mapper/`** — MyBatis-Flex，部分复杂查询用 XML (`ScoreMapper.xml`, `SourceMapper.xml`)
- **`entity/`** — DTO (`dto/request/`, `dto/response/`)、表实体 (`table/`)、视图对象 (`view/`)
- **`common/`** — `Result<T>` 统一响应 (`code`, `msg`, `cause`, `data`) 和 `Code` 错误码枚举
- **`config/`** — CORS（允许所有来源）、MyBatis-Flex 动态表名、Redis 序列化、Swagger、Sa-Token 拦截器
- **`interceptor/`** — `ExceptionInterceptor` (`@RestControllerAdvice`)，统一处理 `SaTokenException`、`RuntimeException`、通用异常

### 关键模式

- **响应格式**: 静态工厂方法 `Result.success(data/msg)`, `Result.failed(cause, code)`
- **鉴权**: Sa-Token，cookie 名 `edusys`，有效期 30 天。Controller 默认 `@SaCheckLogin`，公开接口加 `@SaIgnore`
- **动态表名**: `FlexConfig` 中配置，不以 `sys_` 开头的表自动加 `StudentSource_` 前缀。`SourceServiceImpl` 手写 SQL 中需单独处理
- **文件上传**: 存储路径 `D:/javaproject/edusys/edusys-java/upload`，通过 `/sources/**` 静态资源映射访问

### 数据库

MySQL `edusys` @ `localhost:3306`。核心表：
- 系统: `sys_user`, `sys_user_info`, `sys_role`, `sys_permission`, `sys_menu`, `sys_department`, `sys_dictionary`
- 关联: `sys_user_role_relevance`, `sys_role_menu_relevance`, `sys_role_permission_relevance`
- 业务: `student_source`, `student_source_score`, `source_operate_record`

Redis `localhost:6379` 用于 Sa-Token 会话存储。

## 前端架构

三个 SPA 结构一致:
```
src/
  main.js           — 入口 (Vue, Router, Vuex, UI 库)
  App.vue           — 根组件
  axios/axios.js    — Axios 实例 (baseURL, 拦截器)
  axios/api.js      — API 调用函数
  route/router.js   — 路由 (懒加载)
  store/store.js    — Vuex
  store/session.js  — SessionStorage 封装 (get/set/clear, JSON 自动解析)
  components/       — 页面组件
  view/             — 布局视图
```

### 各端要点

- **edusys-admin-master**（后台系统）: Element Plus 中文版。登录后 token 存入 sessionStorage + Vuex，`/index` 路由 `beforeEnter` 调 `api.isLogin()` 校验。涵盖系统管理(部门/角色/权限/菜单)、人事管理(学生/工作人员)、服装管理(尺码字典/发放/审核)、军训管理(文件/分配/考勤/成绩)
- **edusys-client-master**（教官管理终端）: Element Plus。路由: `/login`(公开), `/index/user`, `/index/count`, `/index/score`(需登录)。供教官查看连队学生、录入考勤与成绩
- **edusys-vue-master**（尺码登记终端）: LayUI Vue。多步向导 `/user/authorize` → `/user/form` → `/user/complete`，Vuex 保存 `step`、`studentInfo`、`parseInfo`（身高/体重/鞋码及对应服装尺码）
