import http from "./axios"

const api = {}

/**
 * 校验学生身份
 * @param {object} params 校验学生信息时使用
 * @param {string} params.year 数据源学年
 * @param {string} params.name 姓名
 * @param {string} params.cardId 身份证号
 * @returns
 */
api.checkStudent = function checkStudent(params) {
    return http.post(`/api/services/sources/cloths/checkStudent`, params);
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
 * 登记学生的基本信息
 * @param {object} params 学生登记时使用
 * @param {string} params.year 入学年份（同时为数据源生成表名）
 * @param {string} params.name 姓名
 * @param {string} params.id 学号
 * @param {string} params.height 身高
 * @param {string} params.weight 体重
 * @param {string} params.shoeSize 鞋码
 * @param {string} params.phone 手机号
 * @returns
 */
api.updateSourceInfoByIdAndName = function updateSourceInfoByIdAndName(params) {
    return http.put(`/api/services/sources/cloths/updateSourceInfoByIdAndName`, params);
}

/**
 * 获取一个学生转换后的服装信息
 * @param {string} id 学号
 * @param {string} year 数据源学年
 * @returns
 */
api.searchSourceInfoBeforeParse = function searchSourceInfoBeforeParse(id, year) {
    return http.get(`/api/services/sources/cloths/searchSourceInfoBeforeParse/${id}/${year}`);
}

export default api