import http from "./axios.js";


const api = {}
// 工作人员管理
/**
 * 查询所有用户列表
 * @param {string} page 页码
 * @returns
 */
api.getUserInfoByPage = function getUserInfoByPage(page) {
    return http.get(`/api/manages/users/getUserInfoByPage/${page}`);
}
/**
 * 创建单个用户
 * @param {object} params 注册单独账号使用
 * @param {string} params.userId 账号（用户id）
 * @param {string} params.name 姓名
 * @param {string} params.phone 联系电话
 * @param {string} params.sexual 性别
 * @param {string} params.post 岗位及其他
 * @returns
 */
api.registerAlone = function registerAlone(params) {
    return http.post(`/api/manages/users/registerAlone`, params);
}
/**
 * 删除工作人员账号
 * @param {string} userId 用户账号
 * @returns
 */
api.removeUser = function removeUser(userId) {
    return http.delete(`/api/manages/users/removeUser/${userId}`);
}
/**
 * 修改密码
 * @param {object} params 修改秘密时使用
 * @param {string} params.username 账号
 * @param {string} params.oldPwd 原密码
 * @param {string} params.newPwd 新密码
 * @returns
 */
api.rePassword = function rePassword(params) {
    return http.post(`/api/manages/users/rePassword`, params);
}
/**
 * 为用户添加角色
 * @param {object} params 用户和角色的映射关系
 * @param {string} params.userId 用户id
 * @param {string} params.roleId 角色id
 * @returns
 */
api.addRoleForUser = function addRoleForUser(params) {
    return http.post(`/api/manages/users/roles/addRoleForUser`, params);
}
/**
 * 为用户移除角色
 * @param {object} params 用户和角色的映射关系
 * @param {string} params.userId 用户id
 * @param {string} params.roleId 角色id
 * @returns
 */
api.removeRoleForUser = function removeRoleForUser(params) {
    return http.put(`/api/manages/users/roles/removeRoleForUser`, params);
}
/**
 * 根据条件模糊查找用户
 * @param {object} params 包含分页参数
 * @param {number} params.pageNumber 页码
 * @param {number} params.pageSize 每页查询数量
 * @param {string} params.userId 账号（用户id）
 * @param {string} params.name 姓名
 * @param {string} params.phone 联系电话
 * @param {string} params.department 部门
 * @param {string} params.post 岗位及其他
 * @param {string} params.sexual 性别
 * @returns
 */
api.searchUserInfoListByCondition = function searchUserInfoListByCondition(params) {
    return http.post(`/api/manages/users/searchUserInfoListByCondition`, params);
}
/**
 * 修改基本信息
 * @param {object} params 个人信息中的业务相关信息
 * @param {string} params.name 姓名
 * @param {string} params.phone 手机号
 * @param {string} params.sexual 性别
 * @returns
 */
api.setInfoByUserId = function setInfoByUserId(params) {
    return http.put(`/api/manages/users/setInfo`, params);
}
/**
 * 修改工作人员个人信息
 * @param {object} params UserInfo
 * @param {number} params.rowKey
 * @param {string} params.userId
 * @param {string} params.name
 * @param {string} params.phone
 * @param {string} params.department
 * @param {string} params.post
 * @param {string} params.sexual
 * @param {string} params.image
 * @returns
 */
api.updateUserInfoByUserId = function updateUserInfoByUserId(params) {
    return http.put(`/api/manages/users/updateUserInfoByUserId`, params);
}
// 人员分配管理
/**
 * 查询当前数据源人员分配情况统计
 * @param {string} year
 * @returns
 */
api.getDistributeAudit = function getDistributeAudit(year) {
    return http.get(`/api/manages/distribute/getDistributeAudit/${year}`);
}
/**
 * 为学生分配连队（批量）
 * @param {object} params 包含分页参数
 * @param {number} params.pageNumber 页码
 * @param {number} params.pageSize 每页查询数量
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.academy 学院
 * @param {string} params.clazz 班级
 * @param {string} params.name 姓名
 * @param {string} params.sexual 性别
 * @param {string} params.id 学号
 * @param {string} params.description 备注
 * @param {string} params.height 身高
 * @param {string} params.weight 体重
 * @param {string} params.shoeSize 鞋码
 * @param {string} params.phone 联系电话
 * @param {number} params.checkStatus 服装登记状态 0=未登记、1=已登记
 * @param {number} params.receiveStatus 服装领取状态 0=未领取 1=已领取
 * @param {string} params.deptId 连队编号
 * @param {string} params.newDeptId 连队编号（仅在更新时使用）
 * @returns
 */
api.updateStudentDeptByCondition = function updateStudentDeptByCondition(params) {
    return http.put(`/api/manages/distribute/updateStudentDeptByCondition`, params);
}
/**
 * 为学生分配连队（单个）
 * @param {object} params 传输单独学生的组织分配信息
 * @param {string} params.year 数据源学年
 * @param {string} params.id 学号
 * @param {string} params.newDeptId 新的组织ID
 * @returns
 */
api.updateStudentDeptById = function updateStudentDeptById(params) {
    return http.put(`/api/manages/distribute/updateStudentDeptById`, params);
}
/**
 * 为教官分配连队
 * @param {object} params 传输教官组织分配信息
 * @param {string} params.userId 用户ID
 * @param {string} params.newDeptId 新的连队ID
 * @returns
 */
api.updateUserDeptByCondition = function updateUserDeptByCondition(params) {
    return http.put(`/api/manages/distribute/updateUserDeptByCondition`, params);
}
// 学生管理（通用）
/**
 * 添加一个学生
 * @param {object} params 添加学生数据时使用
 * @param {string} params.year 数据源学年
 * @param {string} params.academy 学院
 * @param {string} params.clazz 班级
 * @param {string} params.name 姓名
 * @param {string} params.cardId 身份证号
 * @param {string} params.sexual 性别
 * @param {string} params.id 学号
 * @param {string} params.description 备注
 * @returns
 */
api.addForYear = function addForYear(params) {
    return http.put(`/api/manages/sources/addForYear`, params);
}
/**
 * 查询所有学院
 * @param {string} year 数据源学年
 * @returns
 */
api.getAcademyList = function getAcademyList(year) {
    return http.get(`/api/manages/sources/getAcademyList/${year}`);
}
/**
 * 获取数据源操作记录
 * @returns
 */
api.getOperateRecord = function getOperateRecord() {
    return http.get(`/api/manages/sources/getOperateRecord`);
}
/**
 * 删除一个学生
 * @param {string} id
 * @param {string} year
 * @returns
 */
api.removeById = function removeById(id, year) {
    return http.delete(`/api/manages/sources/removeById/${id}/${year}`);
}
/**
 * 根据若干条件查询数据源信息
 * @param {object} params 包含分页参数
 * @param {number} params.pageNumber 页码
 * @param {number} params.pageSize 每页查询数量
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.academy 学院
 * @param {string} params.clazz 班级
 * @param {string} params.name 姓名
 * @param {string} params.sexual 性别
 * @param {string} params.id 学号
 * @param {string} params.description 备注
 * @param {string} params.height 身高
 * @param {string} params.weight 体重
 * @param {string} params.shoeSize 鞋码
 * @param {string} params.phone 联系电话
 * @param {number} params.checkStatus 服装登记状态 0=未登记、1=已登记
 * @param {number} params.receiveStatus 服装领取状态 0=未领取 1=已领取
 * @param {string} params.deptId 连队编号
 * @param {string} params.newDeptId 连队编号（仅在更新时使用）
 * @returns
 */
api.searchByCondition = function searchByCondition(params) {
    return http.post(`/api/manages/sources/searchByCondition`, params);
}
/**
 * 查询某一学院下的所有班级
 * @param {string} year 数据源学年
 * @param {string} academy 学院名
 * @returns
 */
api.searchClassByAcademy = function searchClassByAcademy(year, academy) {
    return http.get(`/api/manages/sources/searchClassByAcademy/${year}/${academy}`);
}
/**
 * 查询所有学生列表
 * @param {string} page 页码
 * @param {string} year 数据源学年
 * @returns
 */
api.searchFullListByPage = function searchFullListByPage(page, year) {
    return http.get(`/api/manages/sources/searchFullListByPage/${page}/${year}`);
}
// 组织机构管理
/**
 * 查询组织列表
 * @param {string} deptCode 查询的起始组织编码
 * @returns
 */
api.getDeptList = function getDeptList(deptCode) {
    return http.get(`/api/manages/departments/getDeptList/${deptCode}`);
}
// 登录和注销
/**
 * 登录
 * @param {object} params 登录时使用
 * @param {string} params.username 账号
 * @param {string} params.password 密码
 * @returns
 */
api.login = function login(params) {
    return http.post(`/api/authorizes/signs/login`, params);
}
/**
 * 注销
 * @returns
 */
api.logout = function logout() {
    return http.get(`/api/authorizes/signs/logout`);
}
// 认证信息获取
/**
 * 获取菜单列表
 * @param {string} userId 登录用户id
 * @returns
 */
api.getMenuListByUserId = function getMenuListByUserId(userId) {
    return http.get(`/api/authorizes/informations/getMenuList/${userId}`);
}
/**
 * 获取用户权限信息
 * @param {string} userId 用户id
 * @returns
 */
api.getPermissionListByUserId = function getPermissionListByUserId(userId) {
    return http.get(`/api/authorizes/informations/getPermissionList/${userId}`);
}
/**
 * 获取用户角色信息
 * @param {string} userId 用户id
 * @returns
 */
api.getRoleListByUserId = function getRoleListByUserId(userId) {
    return http.get(`/api/authorizes/informations/getroleList/${userId}`);
}
/**
 * 检查登录状态
 * @returns
 */
api.isLogin = function isLogin() {
    return http.get(`/api/authorizes/informations/isLogin`);
}
// 菜单配置
/**
 * [admin]配置新菜单
 * @param {object} params 传输菜单相关信息
 * @param {string} params.menuId
 * @param {string} params.url 菜单路径
 * @param {string} params.componentPath 组件路径
 * @param {string} params.description 菜单描述
 * @returns
 */
api.addMenu = function addMenu(params) {
    return http.post(`/api/admins/menus/addMenu`, params);
}

/**
 * [admin]根据条件模糊查找菜单
 * @param {string} menuId 菜单id
 * @param {string} page 页码，默认为1
 * @returns
 */
api.getMenuListByCondition = function getMenuListByCondition(dto) {
    return http.post(`/api/admins/menus/getMenuList`, dto);
}

/**
 * [admin]查询所有菜单列表
 * @param {string} page 页码
 * @returns
 */
api.getMenuListByPage = function getMenuListByPage(page) {
    return http.get(`/api/admins/menus/getMenuList/${page}`);
}

/**
 * [admin]删除菜单
 * @param {string} menuId 菜单id
 * @returns
 */
api.removeMenuByMenuId = function removeMenuByMenuId(menuId) {
    return http.delete(`/api/admins/menus/removeMenu/${menuId}`);
}

/**
 * [admin]修改菜单
 * @param {object} params 传输菜单相关信息
 * @param {string} params.menuId
 * @param {string} params.url 菜单路径
 * @param {string} params.componentPath 组件路径
 * @param {string} params.description 菜单描述
 * @returns
 */
api.setMenuByMenuId = function setMenuByMenuId(params) {
    return http.put(`/api/admins/menus/setMenu`, params);
}
// 角色-菜单配置
/**
 * [admin]为角色添加菜单（批量）
 * @param {object} params 批量记录考勤信息
 * @param {string} params.year 数据源（学年）
 * @param {array} params.indexId 数据源键列表
 * @returns
 */
api.addMenuForRole = function addMenuForRole(params) {
    return http.post(`/api/admins/relevances/menu/addMenu`, params);
}

/**
 * [admin]查看所有菜单列表
 * @returns
 */
api.getMenuListAll = function getMenuListAll() {
    return http.get(`/api/admins/relevances/menu/getMenuList`);
}

/**
 * [admin]查看角色拥有的菜单列表
 * @param {string} roleId 账号
 * @returns
 */
api.getMenuListByRoleId = function getMenuListByRoleId(roleId) {
    return http.get(`/api/admins/relevances/menu/getMenuList/${roleId}`);
}

/**
 * [admin]为角色移除菜单
 * @param {object} params 角色和菜单的映射关系
 * @param {string} params.roleId 角色id
 * @param {string} params.menuId 菜单id
 * @returns
 */
api.removeMenuForRole = function removeMenuForRole(params) {
    return http.put(`/api/admins/relevances/menu/removeMenu`, params);
}
// 角色-权限配置
/**
 * [admin]为角色添加权限（批量）
 * @param {object} params 批量记录考勤信息
 * @param {string} params.year 数据源（学年）
 * @param {array} params.indexId 数据源键列表
 * @returns
 */
api.addPermissionForRole = function addPermissionForRole(params) {
    return http.post(`/api/admins/relevances/permission/addPermission`, params);
}

/**
 * [admin]查看所有权限列表
 * @returns
 */
api.getPermissionListAll = function getPermissionListAll() {
    return http.get(`/api/admins/relevances/permission/getPermissionList`);
}

/**
 * [admin]查看角色拥有的权限列表
 * @param {string} roleId 角色id
 * @returns
 */
api.getPermissionListByRoleId = function getPermissionListByRoleId(roleId) {
    return http.get(`/api/admins/relevances/permission/getPermissionList/${roleId}`);
}

/**
 * [admin]为角色移除权限
 * @param {object} params 角色和权限的映射关系
 * @param {string} params.roleId 角色id
 * @param {string} params.permissionId 权限id
 * @returns
 */
api.removePermissionForRole = function removePermissionForRole(params) {
    return http.put(`/api/admins/relevances/permission/removePermission`, params);
}
// 角色配置
/**
 * [admin]添加角色
 * @param {object} params 传输角色相关信息
 * @param {string} params.roleId 角色id
 * @param {string} params.roleName 角色描述
 * @returns
 */
api.addRole = function addRole(params) {
    return http.post(`/api/admins/roles/addRole`, params);
}

/**
 * [admin]根据条件模糊查找角色
 * @param {string} roleId 角色id
 * @param {string} roleName 角色含义
 * @param {string} page 页码，默认为1
 * @returns
 */
api.getRoleListByCondition = function getRoleListByCondition(dto) {
    return http.post(`/api/admins/roles/getRoleList`, dto);
}

/**
 * [admin]查询所有角色列表
 * @param {string} page 页码
 * @returns
 */
api.getRoleListByPage = function getRoleListByPage(page) {
    return http.get(`/api/admins/roles/getRoleList/${page}`);
}

/**
 * [admin]删除角色
 * @param {string} roleId 角色id
 * @returns
 */
api.removeRoleByRoleId = function removeRoleByRoleId(roleId) {
    return http.delete(`/api/admins/roles/removeRole/${roleId}`);
}

/**
 * [admin]修改角色
 * @param {object} params 传输角色相关信息
 * @param {string} params.roleId 角色id
 * @param {string} params.roleName 角色描述
 * @returns
 */
api.setRoleByRoleId = function setRoleByRoleId(params) {
    return http.put(`/api/admins/roles/setRole`, params);
}
// 权限配置
/**
 * [admin]添加权限
 * @param {object} params 传输权限相关信息
 * @param {string} params.permissionId 权限id
 * @param {string} params.permissionName 权限描述
 * @returns
 */
api.addPermission = function addPermission(params) {
    return http.post(`/api/admins/permissions/addPermission`, params);
}

/**
 * [admin]根据条件模糊查找权限
 * @param {string} permissionId 权限id
 * @param {string} permiisionName 权限含义
 * @param {string} page 页码，默认为1
 * @returns
 */
api.getPermissionListByCondition = function getPermissionListByCondition(dto) {
    return http.post(`/api/admins/permissions/getPermissionList`, dto);
}

/**
 * [admin]查询所有权限列表
 * @param {string} page
 * @returns
 */
api.getPermissionListByPage = function getPermissionListByPage(page) {
    return http.get(`/api/admins/permissions/getPermissionList/${page}`);
}
// 组织机构配置
/**
 * [admin]添加组织机构
 * @param {object} params 部门相关信息dto
 * @param {string} params.deptCode 部门编码
 * @param {string} params.deptName 部门名称
 * @param {string} params.superior 上级部门
 * @returns
 */
api.addDept = function addDept(params) {
    return http.post(`/api/admins/departments/addDept`, params);
}

/**
 * [admin]根据条件模糊查找组织机构
 * @param {string} deptCode 部门编码
 * @param {string} deptName 部门名称
 * @param {string} page 页码，默认为1
 * @returns
 */
api.getDeptListByCondition = function getDeptListByCondition(dto) {
    return http.post(`/api/admins/departments/getDeptList`, dto);
}

/**
 * [admin]查询所有组织机构列表
 * @param {string} page 页码
 * @returns
 */
api.getDeptListByPage = function getDeptListByPage(page) {
    return http.get(`/api/admins/departments/getDeptList/${page}`);
}

/**
 * [admin]删除组织机构
 * @param {string} deptCode
 * @returns
 */
api.removeDeptByDeptCode = function removeDeptByDeptCode(deptCode) {
    return http.delete(`/api/admins/departments/removeDept/${deptCode}`);
}

/**
 * [admin]修改组织机构
 * @param {object} params 部门相关信息dto
 * @param {string} params.deptCode 部门编码
 * @param {string} params.deptName 部门名称
 * @param {string} params.superior 上级部门
 * @returns
 */
api.setDeptByCode = function setDeptByCode(params) {
    return http.put(`/api/admins/departments/setDept`, params);
}
// 服装业务指标
/**
 * 获取服装领取指标
 * @param {string} year
 * @returns
 */
api.getClothAuditAtAcademy = function getClothAuditAtAcademy(year) {
    return http.get(`/api/audit/cloth/getClothAuditAtAcademy/${year}`);
}
// 考勤业务指标
/**
 * 获取军训考勤指标
 * @param {object} params 查询考勤指标时使用
 * @param {object} params.date 查询日期
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.amOrPm 上午/下午状态(0=上午 1=下午)
 * @param {boolean} params.isNormal 是否过滤考勤正常学生（仅在导出文件时使用）
 * @returns
 */
api.getCountAuditAtDept = function getCountAuditAtDept(params) {
    return http.post(`/api/audit/count/getCountAuditAtDept`, params);
}
/**
 * 获取考勤详情文件
 * @param {object} params 查询考勤指标时使用
 * @param {object} params.date 查询日期
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.amOrPm 上午/下午状态(0=上午 1=下午)
 * @param {boolean} params.isNormal 是否过滤考勤正常学生（仅在导出文件时使用）
 * @returns
 */
api.getCountAuditFile = function getCountAuditFile(params) {
    return http.post(`/api/audit/count/getCountAuditFle`, params);
}
// 学生管理（成绩业务相关）
/**
 * 导出成绩详情
 * @param {object} params 导出学生明细信息的请求对象
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {array} params.searchDate 考勤核算范围
 * @returns
 */
api.getScoreFile = function getScoreFile(params) {
    return http.post(`/api/sources/scores/getScoreFile`, params);
}
/**
 * 查询特定条件学生成绩信息
 * @param {object} params 包含分页参数
 * @param {number} params.pageNumber 页码
 * @param {number} params.pageSize 每页查询数量
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.academy 学院
 * @param {string} params.clazz 班级
 * @param {string} params.name 姓名
 * @param {string} params.sexual 性别
 * @param {string} params.id 学号
 * @param {string} params.description 备注
 * @param {string} params.height 身高
 * @param {string} params.weight 体重
 * @param {string} params.shoeSize 鞋码
 * @param {string} params.phone 联系电话
 * @param {number} params.checkStatus 服装登记状态 0=未登记、1=已登记
 * @param {number} params.receiveStatus 服装领取状态 0=未领取 1=已领取
 * @param {string} params.deptId 连队编号
 * @param {string} params.newDeptId 连队编号（仅在更新时使用）
 * @returns
 */
api.getScoreListDetail = function getScoreListDetail(params) {
    return http.post(`/api/sources/scores/getScoreListDetail`, params);
}
/**
 * 获取所有考核项目列表
 * @returns
 */
api.getScoreTotalAll = function getScoreTotalAll() {
    return http.get(`/api/sources/scores/getScoreTotalAll`);
}
/**
 * 获取有效考核项目列表
 * @returns
 */
api.getUsefulScore = function getUsefulScore() {
    return http.get(`/api/sources/scores/getUsefulScore`);
}
/**
 * 登记成绩
 * @param {object} params 记录学生指定科目成绩信息
 * @param {string} params.year 数据源（学年）
 * @param {string} params.id 学号
 * @param {string} params.rawValue 考核项目
 * @param {number} params.score 考核成绩
 * @returns
 */
api.updateScoreByIdAndRaw = function updateScoreByIdAndRaw(params) {
    return http.post(`/api/sources/scores/updateScoreByIdAndRaw`, params);
}
/**
 * 修改指定考核项目的总分
 * @param {object} params 传输字典相关信息
 * @param {string} params.id 数据ID
 * @param {string} params.rawValue 原始值
 * @param {string} params.parseValue 转换值
 * @returns
 */
api.updateScoreTotal = function updateScoreTotal(params) {
    return http.post(`/api/sources/scores/updateScoreTotal`, params);
}
// 学生管理（服装业务相关）
/**
 * 获取一个学生转换后的服装信息
 * @param {string} id 学号
 * @param {string} year 数据源学年
 * @returns
 */
api.searchSourceInfoBeforeParse = function searchSourceInfoBeforeParse(id, year) {
    return http.get(`/api/services/sources/cloths/searchSourceInfoBeforeParse/${id}/${year}`);
}
/**
 * 修改某一学生的登记状态
 * @param {string} year 数据源学年
 * @param {string} id 学号
 * @param {string} status 修改状态1/0
 * @returns
 */
api.updateCheckStatusById = function updateCheckStatusById(year, id, status) {
    return http.get(`/api/services/sources/cloths/updateCheckStatus/${year}/${id}/${status}`);
}
/**
 * 更新某一学生的领取状态
 * @param {string} year 数据源学年
 * @param {string} id 学号
 * @param {string} status 修改状态1/0
 * @returns
 */
api.updateReceiveStatusById = function updateReceiveStatusById(year, id, status) {
    return http.get(`/api/services/sources/cloths/updateReceiveStatus/${year}/${id}/${status}`);
}
// 学生管理（考勤业务相关）
/**
 * 查询某一时间范围内特定条件的学生的考勤情况
 * @param {object} params 根据时间段获取考勤信息DTO
 * @param {number} params.pageNumber 页码
 * @param {number} params.pageSize 每页查询数量
 * @param {array} params.searchDate 查询日期范围
 * @param {string} params.year 入学年份，同时为数据源生成表名
 * @param {string} params.academy 学院
 * @param {string} params.clazz 班级
 * @param {string} params.name 姓名
 * @param {string} params.sexual 性别
 * @param {string} params.id 学号
 * @param {string} params.deptId 连队编号
 * @returns
 */
api.getCountListDetail = function getCountListDetail(params) {
    return http.post(`/api/sources/counts/getCountListDetail`, params);
}
/**
 * 变更考勤状态
 * @param {object} params 批量变更指定日期指定时间段的考勤信息
 * @param {string} params.year 数据源（学年）
 * @param {object} params.date 选定时间
 * @param {string} params.amOrPm 上午/下午状态(0=上午 1=下午)
 * @param {string} params.newCountStatus 变更的考勤状态(0=缺勤 1=出勤 2=请假)
 * @param {array} params.indexId 数据源键列表
 * @returns
 */
api.updateStudentCount = function updateStudentCount(params) {
    return http.post(`/api/sources/counts/updateStudentCount`, params);
}
// 字典管理
/**
 * 添加字典字段
 * @param {object} params Dictionary
 * @param {number} params.id
 * @param {string} params.dicId
 * @param {string} params.description
 * @param {string} params.rawValue
 * @param {string} params.parseValue
 * @returns
 */
api.addDic = function addDic(params) {
    return http.put(`/api/services/dictionarys/addDic`, params);
}
/**
 * 删除一个字典字段
 * @param {string} id 字典的数据id
 * @returns
 */
api.removeDicById = function removeDicById(id) {
    return http.delete(`/api/services/dictionarys/removeDicById/${id}`);
}
/**
 * 查询字典转换表内容
 * @param {string} dicId 字典id
 * @returns
 */
api.searchDicList = function searchDicList(dicId) {
    return http.get(`/api/services/dictionarys/searchDicList/${dicId}`);
}
/**
 * 修改字典值内容
 * @param {object} params 传输字典相关信息
 * @param {string} params.id 数据ID
 * @param {string} params.rawValue 原始值
 * @param {string} params.parseValue 转换值
 * @returns
 */
api.updateDicById = function updateDicById(params) {
    return http.put(`/api/services/dictionarys/updateDicById`, params);
}
export default api