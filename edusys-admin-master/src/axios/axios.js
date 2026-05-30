import axios from 'axios'
import {ElMessage} from "element-plus";
import session from "../store/session.js";

const http = axios.create({
    baseURL: 'http://localhost:8080',
    // withCredentials: true,
})
http.interceptors.request.use(
    config => {
        config.headers['edusys'] = session.get('token').tokenValue
        return config
    },
    error => {
        return error
    })
http.interceptors.response.use(
    response => {
        if (response.data.code != 200) {
            ElMessage.error(response.data.msg + ":" + response.data.cause)
        }
        return Promise.resolve(response)
    },
    error => {
        if (error.data.code != 200) {
            ElMessage.error(error.response.data.msg + ":" + error.response.data.cause)
        }
        return false
    })

export default http