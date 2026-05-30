import axios from 'axios'
import {layer} from "@layui/layui-vue";

const http = axios.create({
    baseURL: "http://localhost:8080", //配置 baseURL
    timeout: 3000, //配置请求超时时间
})
//请求拦截器
http.interceptors.request.use((config) => {
    //在发送请求之前的业务逻辑
    return config
}, (error) => {
    //请求发生错误的业务逻辑
    return error
})

//响应拦截器
http.interceptors.response.use((response) => {
    //对响应数据的处理
    //如果后端响应的状态不是200，那么直接抛出异常
    if (response.data.code == 200) {
        return Promise.resolve(response);
    } else {
        layer.msg(response.data.msg + ":" + response.data.cause, {icon: 2, time: 1000})
        return Promise.reject(response)
    }
}, (error) => {
    if (error.response.status) {
        //响应错误
        let status = error.response.status
        //根据不同的状态码，提示不同的信息
        console.log(error.response.data.message) // 其他的错误，抛出错误提示
        return Promise.reject(error)
    }
})

export default http