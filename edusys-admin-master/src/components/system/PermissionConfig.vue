<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
// 数据列表：权限信息
const basicPermissionList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 请求数据：添加权限信息
const permissionEditData = ref({
  permissionId: null,
  permissionName: null
})
// 请求数据：查询权限信息
const searchData = ref({
  permissionId: null,
  permissionName: null,
  page: 1
})
// 标识：当前页码
const currentPage = ref(1)
// 标识：添加对话框显示
const addDialogVisible = ref(false)
const searchPermissionList = () => {
  searchData.value.page = currentPage.value
  api.getPermissionListByCondition(searchData.value)
      .then(r => {
        basicPermissionList.value = r.data.data
      })
}
// 函数：添加权限
const addPermission = () => {
  api.addPermission(permissionEditData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          addDialogVisible.value = false
          getNowPage()
        }
      })
}
// 函数：获取当前页的数据
const getNowPage = () => {
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  let result = regax.test(searchData.value.permissionId) ||
      regax.test(searchData.value.permissionName);
  if (result) {
    searchPermissionList()
  } else {
    api.getPermissionListByPage(currentPage.value)
        .then(r => {
          basicPermissionList.value = r.data.data
        })
  }
}
// 初始化
onMounted(() => {
  // 获取权限信息数据列表第一页
  api.getPermissionListByPage(1)
      .then(r => {
        basicPermissionList.value = r.data.data
      })
})
</script>

<template>
  <h1>权限信息配置</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag>检索/操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-input placeholder="权限标签..." v-model="searchData.permissionId"
                @input="searchPermissionList()"/>
    </el-col>
    <el-col :span="5">
      <el-input placeholder="权限名称..." v-model="searchData.permissionName"
                @input="searchPermissionList()"/>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="searchPermissionList()">查询</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="success" plain @click="addDialogVisible=true">添加权限</el-button>
    </el-col>
  </el-row>
  <!--  数据展示区-->
  <div class="space">
    <el-tag>数据列表</el-tag>
  </div>
  <el-table :data="basicPermissionList.records" stripe height="550">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column prop="permissionId" sortable label="权限编码"/>
    <el-table-column prop="permissionName" label="权限含义"/>
  </el-table>
  <!--  分页区-->
  <el-pagination background layout="total, ->, prev, pager, next, jumper" @current-change="getNowPage()"
                 :total="basicPermissionList.totalRow"
                 v-model:current-page="currentPage"
                 :page-count="basicPermissionList.totalPage"/>
  <!--  添加对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>添加权限</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="权限ID"
                  v-model="permissionEditData.permissionId"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="权限含义"
                  v-model="permissionEditData.permissionName"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addPermission()">添加
        </el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>