<script setup>
import store from "../store/store.js";

</script>
<template>
  <lay-layout>
    <lay-header class="header">
      <h3 class="title">军训服装登记终端</h3>
    </lay-header>
    <lay-body>
      <lay-container fluid>
        <div style="padding: 10px 0px 10px">
          <lay-progress :percent="store.state.step"/>
        </div>
        <router-view/>
      </lay-container>
    </lay-body>
    <lay-footer class="footer">
      <div class="footer-text">
        <p>高校学生军训信息化管理系统 Version 1.0 BETA</p>
        <p>© 2023 Weiyin Lu. All rights reserved.</p>
        <p><a href="https://beian.miit.gov.cn" target="_blank">辽ICP备2023006002号</a></p>
      </div>
    </lay-footer>
  </lay-layout>
</template>

<style scoped>
.header {
  position: relative;
  line-height: 20%;
}

.title {
  width: 100%;
  padding: 40px 0 0 0;
  text-align: center;
  font-weight: bolder;
  font-size: 1.5rem;
}

.footer {
  position: relative;
}

.footer-text {
  color: gray;
  position: absolute;
  text-align: center;
  width: 100%;
  left: 50%;
  transform: translate(-50%);
}
</style>