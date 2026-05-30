import http from "./axios.js";


const api = {}

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

/**
 * 获得指定连队的学生考勤信息
 * @param {object} params 获取连队学生相关清单时使用
 * @param {string} params.year 数据源学年
 * @param {string} params.deptId 学生所在连队
 * @returns
 */
api.getCountListByDept = function getCountListByDept(params) {
    return http.post(`/api/sources/counts/getCountListByDept`, params);
}

/**
 * 获取数据源操作记录
 * @returns
 */
api.getOperateRecord = function getOperateRecord() {
    return http.get(`/api/manages/sources/getOperateRecord`);
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
 * 登记出勤-缺勤学生（区分上午、下午）
 * @param {object} params 批量记录当日考勤信息
 * @param {string} params.year 数据源（学年）
 * @param {array} params.indexId 数据源键列表
 * @param {boolean} params.status 考勤状态 true=出勤 false=缺勤
 * @returns
 */
api.setCount = function setCount(params) {
    return http.put(`/api/sources/counts/setCount`, params);
}
/**
 * 获取有效考核项目列表
 * @returns
 */
api.getUsefulScore = function getUsefulScore() {
    return http.get(`/api/sources/scores/getUsefulScore`);
}
/**
 * 获得指定连队的学生成绩信息
 * @param {object} params 获取连队学生相关清单时使用
 * @param {string} params.year 数据源学年
 * @param {string} params.deptId 学生所在连队
 * @returns
 */
api.getScoreListByDept = function getScoreListByDept(params) {
    return http.post(`/api/sources/scores/getScoreListByDept`, params);
}
/**
 * 登记成绩
 * @param {object} params 记录学生指定科目成绩信息
 * @param {string} params.year 数据源（学年）
 * @param {string} params.id 学号
 * @param {string} params.rawValue 考核项目
 * @param {object} params.score 考核成绩
 * @returns
 */
api.updateScoreByIdAndRaw = function updateScoreByIdAndRaw(params) {
    return http.post(`/api/sources/scores/updateScoreByIdAndRaw`, params);
}
export default api