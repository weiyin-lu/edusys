//store.js:

import {createStore} from 'vuex'
import session from "./session.js";

const store = createStore({
    state() {
        return {
            info: session.get('info'),
            permission: session.get('permission'),
            role: session.get('role'),
            token: session.get('token')
        }
    },
    getters: {
        getInfo(state) {
            return state.info
        },
        getPermission(state) {
            return state.permission
        },
        getRole(state) {
            return state.role
        },
        getToken(state) {
            return state.token
        }
    },
    mutations: {
        login(state, dto) {
            // 将当前赋值给store一次
            state.token = dto.token
            state.role = dto.role
            state.permission = dto.permission
            state.info = dto.info
            // 将这些值赋值给sessionstorage
            session.set('token', dto.token)
            session.set('role', dto.role)
            session.set('permission', dto.permission)
            session.set('info', dto.info)
        },
        logout() {
            session.clear('token')
            session.clear('role')
            session.clear('permission')
            session.clear('info')
        },
        setInfo(state, dto) {
            state.info = dto
        }
    },
    actions: {
        loginAction(context, dto) {
            context.commit('login', dto)
        },
        logoutAction(context) {
            context.commit('logout')
        },
        setInfoAction(context, dto) {
            context.commit('setInfo', dto)
        }
    }
})

export default store