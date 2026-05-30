import {createRouter, createWebHistory} from "vue-router";
import api from "../axios/api.js";
import session from "../store/session.js";

const routes = [
    {
        path: '/',
        redirect: '/sign'
    },
    {
        path: '/sign',
        component: () => import('/src/view/Authorize.vue'),
    },
    {
        path: '/index',
        redirect: '/index/default',
        component: () => import('/src/view/Index.vue'),
        beforeEnter: (to, from, next) => {
            api.isLogin(session.get('token'))
                .then(r => {
                    if (r.data.code == 200) {
                        next()
                    } else {
                        next("/sign")
                    }
                })
        },
        children: [
            {
                path: 'info',
                component: () => import('/src/components/Information.vue')
            },
            {
                path: 'system-deptConfig',
                component: () => import('/src/components/system/DeptConfig.vue')
            },
            {
                path: 'system-roleConfig',
                component: () => import('/src/components/system/RoleConfig.vue')
            },
            {
                path: 'system-permissionConfig',
                component: () => import('/src/components/system/PermissionConfig.vue')
            },
            {
                path: 'system-menuConfig',
                component: () => import('/src/components/system/MenuConfig.vue')
            },
            {
                path: 'humanManage-student',
                component: () => import('/src/components/humanManage/Student.vue')
            },
            {
                path: 'humanManage-worker',
                component: () => import('/src/components/humanManage/Worker.vue')
            },
            {
                path: 'serviceCloth-cloth',
                component: () => import('/src/components/serviceCloth/Cloth.vue')
            },
            {
                path: 'serviceCloth-dictionary',
                component: () => import('/src/components/serviceCloth/Dictionary.vue')
            },
            {
                path: 'serviceCloth-clothAudit',
                component: () => import('/src/components/serviceCloth/ClothAudit.vue')
            },
            {
                path: 'serviceTrain-distribute',
                component: () => import('/src/components/serviceTrain/Distribute.vue')
            },
            {
                path: 'serviceTrain-file',
                component: () => import('/src/components/serviceTrain/File.vue')
            },
            {
                path: 'serviceTrain-count',
                component: () => import('/src/components/serviceTrain/Count.vue')
            },
            {
                path: 'serviceTrain-countAudit',
                component: () => import('/src/components/serviceTrain/CountAudit.vue')
            },
            {
                path: 'serviceTrain-score',
                component: () => import('/src/components/serviceTrain/Score.vue')
            },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router