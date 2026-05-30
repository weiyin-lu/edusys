import { createApp } from 'vue'
import App from './App.vue'
import router from '/src/route/router'
import store from "/src/store/store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import api from "./axios/api.js";

const app = createApp(App)
// 全局变量
app.provide('$api', api)
// 全局引入所有icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router).use(store).use(ElementPlus, {locale: zhCn}).mount('#app')
