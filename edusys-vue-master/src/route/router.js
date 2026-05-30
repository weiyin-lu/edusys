import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: "/",
        redirect: '/user/authorize'
    },
    {
        path: "/user",
        component: () => import("/src/components/UserIndex.vue"),
        children: [
            {
                path: "authorize",
                component: () => import("/src/components/user/Step1.vue")
            },
            {
                path: "form",
                component: () => import("/src/components/user/Step2.vue")
            },
            {
                path: "complete",
                component: () => import("/src/components/user/Step3.vue")
            }
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router