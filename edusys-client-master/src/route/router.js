import {createRouter, createWebHistory} from "vue-router";
import api from "../axios/api.js";
import {ElMessage} from "element-plus";

const routes = [
    {
        path: '/',
        redirect: '/index'
    },
    {
        path: '/index',
        redirect: '/index/user',
        component: () => import('/src/view/Index.vue'),
        beforeEnter: (to, from) => {
            api.isLogin()
                .then(r => {
                    if (r.data.code == 200 || to.path == '/login') {
                        return true
                    } else {
                        router.push('/login')
                        ElMessage.info("请登录")
                    }
                })
        },
        children: [
            {
                path: 'user',
                component: () => import('/src/components/User.vue')
            },
            {
                path: 'count',
                component: () => import('/src/components/Count.vue')
            },
            {
                path: 'score',
                component: () => import('/src/components/Score.vue')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('/src/view/Login.vue'),
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router