<script setup>
import {Pointer} from "@element-plus/icons-vue";
</script>

<template>
  <div class="container">
    <el-header class="header">
      <div class="title">
        高校学生军训信息化管理系统
      </div>
    </el-header>
    <el-main class="main">
      <RouterView/>
    </el-main>
    <el-menu class="footer" mode="horizontal" :ellipsis="false" default-active="user" router>
      <el-menu-item class="menusub" index="count">
        <el-icon :size="20">
          <Pointer/>
        </el-icon>
        考勤管理
      </el-menu-item>
      <el-menu-item class="menusub" index="score">
        <el-icon :size="20">
          <Pointer/>
        </el-icon>
        成绩录入
      </el-menu-item>
      <el-menu-item class="menusub" index="user">
        <el-icon :size="20">
          <User/>
        </el-icon>
        个人中心
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style scoped>
.container {
  height: 100%;
  width: 100%;
}

.header {
  width: 100%;
  height: 5%;
  background-color: #215E21;
  position: absolute;
  top: 0;
  left: 0;
}

.title {
  position: relative;
  text-align: center;
  top: 25%;
  color: white;
  font-size: 1.2rem;
}

.footer {
  width: 100%;
  height: 8%;
  position: absolute;
  bottom: 0;
  left: 0;
  color: white;
  --el-menu-bg-color: #215E21;
  --el-menu-hover-bg-color: #215E21;
  --el-menu-active-color: #fcc307;
  --el-menu-hover-text-color: #fcc307;
}

.main {
  position: absolute;
  width: 100%;
  left: 0;
  top: 5%;
  bottom: 8%;
}

.menusub {
  color: white;
  width: 33%;
}
</style>