import {createApp} from 'vue'
import App from './App.vue'
import route from './route/router'
import store from './store/store'
import layUi from '@layui/layui-vue'
import '@layui/layui-vue/lib/index.css'

const app = createApp(App)
app.use(route)
app.use(store)
app.use(layUi)
app.mount('#app')
