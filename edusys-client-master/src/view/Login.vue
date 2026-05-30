<script setup>
import {inject, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
const store = useStore()
const router = useRouter()
// 请求携带数据：登录信息
const loginData = ref({
  username: null,
  password: null
})
// 函数：登录
const login = () => {
  // 登录请求
  api.login(loginData.value)
      .then(r => {
        if (r.data.code == 200) {
          // 登录信息维护到vuex
          store.dispatch("loginAction", r.data.data)
          ElMessage.success(r.data.msg)
          router.push("/index")
        } else {
          ElMessage.error(r.data.msg + r.data.cause)
        }
      })
}
</script>

<template>
  <el-row>
    <el-col class="space">
      <h2 class="title">教官管理终端</h2>
    </el-col>
    <el-col class="space">
      <el-input class="input" v-model="loginData.username" size="large" placeholder="输入账号"/>
    </el-col>
    <el-col class="space">
      <el-input class="input" v-model="loginData.password" type="password" size="large"
                placeholder="输入密码"/>
    </el-col>
    <el-col class="space">
      <el-button class="input" @click="login()" type="primary" size="large" plain>登录
      </el-button>
    </el-col>
  </el-row>
  <el-footer class="footer">
    <p>高校学生军训信息化管理系统 Version 1.0 BETA</p>
    <p>© 2023 Weiyin Lu. All rights reserved.</p>
    <p><a href="https://beian.miit.gov.cn" target="_blank">辽ICP备2023006002号</a></p>
  </el-footer>
</template>
<style scoped>
.space {
  padding: 0 0 10px;
  width: 100%;
}

.title {
  text-align: center;
}

.input {
  position: relative;
  width: 90%;
  left: 5%;
  margin: auto;
}

.footer {
  position: absolute;
  width: 100%;
  height: 7%;
  bottom: 10%;
  text-align: center;
  font-size: 15px;
  color: darkgray;
}
</style>