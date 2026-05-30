<script setup>
import {useRouter} from "vue-router";
import {inject, onBeforeMount, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {useStore} from "vuex";
import session from "../store/session.js";
// 全局函数
const api = inject("$api")
const router = useRouter()
const store = useStore()
// 标识：菜单显示控制
const menuVisible = ref({
  system: false,
  roleConfig: false,
  menuConfig: false,
  permissionConfig: false,
  deptConfig: false,
  humanManage: false,
  student: false,
  worker: false,
  serviceCloth: false,
  cloth: false,
  dictionary: false,
  clothAudit: false,
  serviceTrain: false,
  distribute: false,
  file: false,
  countAudit: false,
  score: false,
  count: false
})
// 函数：账号注销
const logout = () => {
  api.logout()
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.info(r.data.msg + '...');
          store.dispatch("logoutAction")
          router.push("/sign")
        }
      })
}
//初始化
onBeforeMount(() => {
  // 从sessionStorage获取一套认证信息到store里
  let sessionInfo = {
    info: null,
    permission: null,
    role: null,
    token: null
  }
  sessionInfo.info = session.get('info')
  sessionInfo.role = session.get('role')
  sessionInfo.permission = session.get('permission')
  sessionInfo.token = session.get('token')
  store.dispatch("loginAction", sessionInfo)
})
// 初始化
onMounted(() => {
  // 从后台获取数据判断菜单是否应该显示
  api.getMenuListByUserId(store.state.token.loginId)
      .then(r => {
        // 遍历请求得到的菜单列表，通过正则表达式进行权限判断
        for (let menu of r.data.data) {
          // 1.如果菜单列表中有all，把所有菜单设置为true
          if (/^all$/.test(menu.menuId)) {
            Object.keys(menuVisible.value).forEach((i) => menuVisible.value[i] = true)
            return
          }
          // 2.如果有system菜单相关的菜单权限，把system分类设置为true
          if (/^system/.test(menu.menuId)) {
            menuVisible.value.system = true
          }
          // 3.如果有humanManage菜单相关的菜单权限，把humanManage分类设置为true
          if (/^humanManage/.test(menu.menuId)) {
            menuVisible.value.humanManage = true
          }
          // 4.如果有serviceCloth菜单相关的菜单权限，把serviceCloth分类设置为true
          if (/^serviceCloth/.test(menu.menuId)) {
            menuVisible.value.serviceCloth = true
          }
          // 5.如果有serviceTrain菜单相关的菜单权限，把serviceCount分类设置为true
          if (/^serviceTrain/.test(menu.menuId)) {
            menuVisible.value.serviceCount = true
          }
          // 常规处理：把菜单id对应的菜单权限显示出来
          let index = menu.menuId.indexOf("-") + 1
          let flag = menu.menuId.substring(index)
          menuVisible.value[flag] = true;
        }
      })
})
</script>

<template>
  <el-container class="container">
    <el-header class="header">
      <el-menu default-active="info" mode="horizontal" :ellipsis="false" router>
        <el-menu-item index="info"><h1>高校学生军训信息化管理系统</h1></el-menu-item>
        <el-menu-item @click="logout">注销</el-menu-item>
      </el-menu>
    </el-header>
    <el-container class="main">
      <el-aside class="aside">
        <el-menu router index="1" >
          <el-sub-menu v-if="menuVisible.system">
            <template #title>
              <span>系统配置</span>
            </template>
            <el-menu-item v-if="menuVisible.roleConfig" index="system-roleConfig">
              角色信息配置
            </el-menu-item>
            <el-menu-item v-if="menuVisible.menuConfig" index="system-menuConfig">
              菜单信息配置
            </el-menu-item>
            <el-menu-item v-if="menuVisible.permissionConfig" index="system-permissionConfig">
              权限信息配置
            </el-menu-item>
            <el-menu-item v-if="menuVisible.deptConfig" index="system-deptConfig">
              组织信息配置
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu v-if="menuVisible.humanManage" index="2">
            <template #title>
              <span>人员管理</span>
            </template>
            <el-menu-item v-if="menuVisible.student" index="humanManage-student">
              学生信息管理
            </el-menu-item>
            <el-menu-item v-if="menuVisible.worker" index="humanManage-worker">
              工作人员信息管理
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu v-if="menuVisible.serviceCloth" index="3">
            <template #title>
              <span>服装业务管理</span>
            </template>
            <el-menu-item v-if="menuVisible.cloth" index="serviceCloth-cloth">
              服装分发管理
            </el-menu-item>
            <el-menu-item v-if="menuVisible.dictionary" index="serviceCloth-dictionary">
              字典管理
            </el-menu-item>
            <el-menu-item v-if="menuVisible.clothAudit" index="serviceCloth-clothAudit">
              服装领取指标大屏
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu v-if="menuVisible.serviceTrain" index="4">
            <template #title>
              <span>军训业务管理</span>
            </template>
            <el-menu-item v-if="menuVisible.file" index="serviceTrain-file">
              军训指导文件
            </el-menu-item>
            <el-menu-item v-if="menuVisible.distribute" index="serviceTrain-distribute">
              人员分配管理
            </el-menu-item>
            <el-menu-item v-if="menuVisible.count" index="serviceTrain-count">
              考勤综合管理
            </el-menu-item>
            <el-menu-item v-if="menuVisible.countAudit" index="serviceTrain-countAudit">
              军训考勤指标大屏
            </el-menu-item>
            <el-menu-item v-if="menuVisible.score" index="serviceTrain-score">
              军训成绩核算
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
    <el-footer class="footer">
      <p>高校学生军训信息化管理系统 Version 1.0 BETA © 2023 Weiyin Lu. All rights reserved.</p>
      <p><a href="https://beian.miit.gov.cn" target="_blank">辽ICP备2023006002号</a></p>
    </el-footer>
  </el-container>
</template>

<style scoped>
.container {
  position: relative;
  height: 100%;
  width: 100%;
}

.header {
  position: relative;
  width: 100%;
  height: 6%;
  left: 0;
  background-color: #215E21;
  --el-menu-text-color: white;
  --el-menu-bg-color: #215E21;
  --el-menu-hover-bg-color: #215E21;
  --el-menu-active-color: #fcc307;
  --el-menu-hover-text-color: #fcc307;
  --el-menu-border-color: #215E21;
}

.main {
  position: relative;
  height: 86%;
  --el-menu-text-color: white;
  --el-menu-bg-color: #215E21;
  --el-menu-hover-bg-color: #215E21;
  --el-menu-active-color: #fcc307;
  --el-menu-hover-text-color: #fcc307;
  --el-menu-border-color: #215E21;
}

.aside {
  background-color: #215E21;
}

.footer {
  position: relative;
  width: 100%;
  height: 7%;
  bottom: 0;
  text-align: center;
  font-size: 15px;
  color: darkgray;
}
</style>