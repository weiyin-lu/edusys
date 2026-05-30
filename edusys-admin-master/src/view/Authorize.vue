<script setup>
import {inject, onBeforeMount, onMounted, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import session from "../store/session.js";
// 全局函数
const api = inject("$api")
const store = useStore()
const router = useRouter()
// 请求携带数据：登录信息
const loginData = ref({
  username: null,
  password: null
})
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 函数：登录
const login = () => {
  mainLoading.value = true
  // 登录请求
  api.login(loginData.value)
      .then(r => {
        if (r.data.code == 200) {
          // 登录信息维护到vuex
          store.dispatch("loginAction", r.data.data)
          ElMessage.success(r.data.msg)
          mainLoading.value = false
          router.push("/index/info")
        }
      })
}
</script>

<template>
  <el-container class="container" v-loading="mainLoading">
    <el-header class="header">
      <div class="title">
        高校学生军训信息化管理系统
      </div>
    </el-header>
    <el-main class="main">
      <el-card class="card">
        <div class="form">
          <el-col class="space">
            <el-input class="input" v-model="loginData.username" size="large"
                      placeholder="输入账号"/>
          </el-col>
          <el-col class="space">
            <el-input class="input" v-model="loginData.password" type="password"
                      size="large"
                      placeholder="输入密码"/>
          </el-col>
          <el-col class="space">
            <el-button class="input" @click="login()" type="primary"
                       size="large" plain>
              登录
            </el-button>
          </el-col>
        </div>
      </el-card>
    </el-main>
    <el-footer class="footer">
      <p>高校学生军训信息化管理系统 Version 1.0 BETA © 2023 Weiyin Lu. All rights reserved.</p>
      <p><a href="https://beian.miit.gov.cn" target="_blank">辽ICP备2023006002号</a></p>
    </el-footer>
  </el-container>
</template>

<style scoped>
.container {
  position: relative;
  width: 100%;
  height: 100%;
}

.header {
  position: relative;
  height: 30%;
}

.title {
  position: relative;
  text-align: center;
  top: 50%;
  font-size: 3rem;
}

.card {
  position: relative;
  width: 40%;
  height: 60%;
  margin: 0 auto;
}

.form {
  position: relative;
  text-align: center;
  padding: 10% 0 10% 0;
}

.input {
  position: relative;
  width: 90%;
}

.footer {
  position: absolute;
  width: 100%;
  bottom: 5%;
  text-align: center;
  font-size: 15px;
  color: darkgray;
}

.space {
  padding: 0 0 20px;
}
</style>