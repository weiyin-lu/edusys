// 测试函数，生成随机的服装登记数据
import http from "../axios/axios.js";
import RandExp from "randexp";

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
const set = function updateSourceInfoByIdAndName(params) {
    return http.put(`/api/services/sources/cloths/updateSourceInfoByIdAndName`, params);
}
/**
 * 获得指定连队的学生名单
 * @param {object} params 获取连队学生清单时使用
 * @param {string} params.year 数据源学年
 * @param {string} params.deptId 学生所在连队
 * @returns
 */
const getInfo = function getStudentListByDept(params) {
    return http.post(`/api/sources/counts/getStudentListByDept`, params);
}
const test1 = async () => {
    let param1 = {deptId: "ZXB", year: "2023"}
    let param2 = {
        year: "2023",
        name: null,
        id: null,
        height: null,
        weight: null,
        shoeSize: null,
        phone: null
    }
    await getInfo(param1)
        .then(r => {
            console.log(r.data.data)
            for (let i = 2760; i < 2973; i++) {
                param2.id = r.data.data[i].id
                param2.name = r.data.data[i].name
                param2.phone = new RandExp(/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/)
                    .gen();
                param2.weight = new RandExp(/^[4-8][0-9]$/).gen();
                param2.height = new RandExp(/^1[4-9][0-9]$/).gen();
                param2.shoeSize = new RandExp(/^[3-4][5-9]$/).gen();
                console.log("第" + (i + 1) + "个学生 当前请求信息：" + JSON.stringify(param2));
                set(param2)
                    .then(r2 => {
                        console.log(r2.data.msg)
                    })
            }
        })
}