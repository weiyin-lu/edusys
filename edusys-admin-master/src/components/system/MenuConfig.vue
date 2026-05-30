<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
// 数据列表：菜单信息
const basicMenuList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 请求数据：修改菜单信息
const menuEditData = ref({
  menuId: null,
  url: null,
  componentPath: null,
  description: null
})
// 请求数据：添加菜单信息
const menuAddData = ref({
  menuId: null,
  url: null,
  componentPath: null,
  description: null
})
// 请求数据：查询菜单信息
const searchData = ref({
  menuId: null,
  page: 1
})
// 标识：当前页码
const currentPage = ref(1)
// 标识：修改对话框显示
const editDialogVisible = ref(false)
// 标识：添加对话框显示
const addDialogVisible = ref(false)
const searchMenuList = () => {
  searchData.value.page = currentPage.value
  api.getMenuListByCondition(searchData.value)
      .then(r => {
        basicMenuList.value = r.data.data
      })
}
// 函数：修改菜单信息 数据装填
const setMenuBefore = value => {
  editDialogVisible.value = true
  menuEditData.value.menuId = value.menuId
  menuEditData.value.url = value.url
  menuEditData.value.componentPath = value.componentPath
  menuEditData.value.description = value.description
}
// 函数：修改菜单信息
const setMenu = () => {
  api.setMenuByMenuId(menuEditData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          // 关闭菜单、清空请求数据、重新获取当前列表
          editDialogVisible.value = false
          getNowPage()
        }
      })
}
// 函数：添加菜单信息
const addMenu = () => {
  api.addMenu(menuAddData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          addDialogVisible.value = false
          Object.keys(menuAddData.value).forEach((i) => menuAddData.value[i] = null)
          getNowPage()
        }
      })
}
// 函数：删除菜单信息
const removeMenu = (value) => {
  api.removeMenuByMenuId(value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          getNowPage()
        }
      })
}
// 函数：获取当前页的数据
const getNowPage = () => {
  // 如果查询输入框有值，就通过模糊匹配函数获取数据；如果没有，就通过正常函数查询
  if (searchData.value.menuId == null || searchData.value.MenuId == "") {
    api.getMenuListByPage(currentPage.value)
        .then(r => {
          basicMenuList.value = r.data.data
        })
  } else {
    searchMenuList()
  }
}

// 初始化
onMounted(() => {
  // 获得部门信息数据列表第一页
  api.getMenuListByPage(1)
      .then(r => {
        basicMenuList.value = r.data.data
      })
})
</script>

<template>
  <h1>菜单信息配置</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag>检索/操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-input v-model="searchData.menuId" @input="searchMenuList()"
                placeholder="菜单编码..."/>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="searchMenuList()">查询</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="success" plain @click="addDialogVisible=true">配置新菜单</el-button>
    </el-col>
  </el-row>
  <!--  数据显示区-->
  <div class="space">
    <el-tag>数据列表</el-tag>
  </div>
  <el-table :data="basicMenuList.records" stripe height="550">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column prop="menuId" sortable label="菜单编码"/>
    <el-table-column prop="description" label="菜单描述"/>
    <el-table-column prop="url" label="菜单路径"/>
    <el-table-column prop="componentPath" label="组件路径"/>
    <el-table-column label="操作" width="130px">
      <template #default="scope">
        <el-button type="warning" plain circle @click="setMenuBefore(scope.row)">
          <el-icon color="#222222">
            <Edit/>
          </el-icon>
        </el-button>
        <el-popconfirm title="删除菜单可能会导致所有用户都无法访问该菜单，确定删除？"
                       @confirm="removeMenu(scope.row.menuId)"
                       hide-after="100">
          <template #reference>
            <el-button type="danger" plain circle>
              <el-icon color="#222222">
                <Delete/>
              </el-icon>
            </el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <!--  分页区-->
  <el-pagination background layout="total, ->, prev, pager, next, jumper" @current-change="getNowPage()"
                 :total="basicMenuList.totalRow"
                 v-model:current-page="currentPage"
                 :page-count="basicMenuList.totalPage"/>
  <!--  修改对话框-->
  <el-dialog v-model="editDialogVisible" width="400px">
    <template #title>
      <h1>修改菜单信息</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" v-model="menuEditData.menuId" disabled/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="访问路径"
                  v-model="menuEditData.url"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组件路径"
                  v-model="menuEditData.componentPath"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组件描述"
                  v-model="menuEditData.description"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="setMenu()">提交修改
        </el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  添加对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>配置新菜单</h1>
    </template>
    <el-text type="danger">菜单ID必须以"menu-component"的形式命名</el-text>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="菜单ID" v-model="menuAddData.menuId"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="访问路径"
                  v-model="menuAddData.url"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组件路径"
                  v-model="menuAddData.componentPath"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组件描述"
                  v-model="menuAddData.description"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addMenu()">添加</el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>