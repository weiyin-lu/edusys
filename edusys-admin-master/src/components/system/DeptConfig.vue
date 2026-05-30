<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
// 数据列表：组织信息
const basicDeptList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：组织信息（树形嵌套列表）
const treeDeptList = ref([])
// 请求数据：查询组织信息
const searchData = ref({
  deptCode: null,
  deptName: null,
  page: 1
})
// 请求数据：添加组织信息
const deptAddData = ref({
  deptCode: null,
  deptName: null,
  superior: null
})
// 请求数据：修改组织信息
const deptEditData = ref({
  deptCode: null,
  deptName: null,
  superior: null
})
// 标识：级联选择器选择的路径数组
const cascaderPath = ref([])
// 标识：级联选择器配置
const cascaderConfig = ref({
  checkStrictly: true,
  value: "deptCode",
  label: "deptName",
  children: "childList"
})
// 标识：添加对话框显示
const addDialogVisible = ref(false)
// 标识：修改对话框显示
const EditDialogVisible = ref(false)
// 标识：当前页码
const currentPage = ref(1)
// 函数：数据模糊匹配查询组织
const searchDeptList = () => {
  searchData.value.page = currentPage.value
  api.getDeptListByCondition(searchData.value)
      .then(r => {
        basicDeptList.value = r.data.data
      })
}
// 函数：获取当前页的数据
const getNowPage = () => {
  // 如果查询输入框有值，就通过模糊匹配函数获取数据；如果没有，就通过正常函数查询
  if ((searchData.value.deptCode == null || searchData.value.deptCode == "") &&
      (searchData.value.deptName == null || searchData.value.deptName == "")) {
    api.getDeptListByPage(currentPage.value)
        .then(r => {
          basicDeptList.value = r.data.data
        })
  } else {
    searchDeptList()
  }
}
// 函数：添加组织
const addDept = () => {
  // 将级联选择器选择的最末级路径装填到请求数据中
  deptAddData.value.superior = cascaderPath.value[cascaderPath.value.length - 1]
  api.addDept(deptAddData.value)
      .then(r => {
        if (r.data.code == 200) {
          addDialogVisible.value = false
          Object.keys(deptAddData.value).forEach((i) => deptAddData.value[i] = null)
          ElMessage.success(r.data.msg)
          getNowPage()
          // 重新装填嵌套列表
          api.getDeptList('MAIN')
              .then(r => {
                treeDeptList.value = r.data.data
              })
        }
      })
}
// 函数：删除组织
const removeDept = value => {
  api.removeDeptByDeptCode(value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          getNowPage()
          // 重新装填嵌套列表
          api.getDeptList('MAIN')
              .then(r => {
                treeDeptList.value = r.data.data
              })
        }
      })
}
// 函数：修改组织 数据装填
const setDeptBefore = (value) => {
  EditDialogVisible.value = true
  deptEditData.value.deptCode = value.deptCode
  deptEditData.value.deptName = value.deptName
}
// 函数：修改组织
const setDept = () => {
  // 将级联选择器选择的最末级路径装填到请求数据中
  deptEditData.value.superior = cascaderPath.value[cascaderPath.value.length - 1]
  api.setDeptByCode(deptEditData.value)
      .then(r => {
        if (r.data.code == 200) {
          addDialogVisible.value = false
          Object.keys(deptEditData.value).forEach((i) => deptEditData.value[i] = null)
          ElMessage.success(r.data.msg)
          getNowPage()
          // 重新装填嵌套列表
          api.getDeptList('MAIN')
              .then(r => {
                treeDeptList.value = r.data.data
              })
        }
      })
}
// 初始化
onMounted(() => {
  // 获得组织信息数据列表第一页
  api.getDeptListByPage(1)
      .then(r => {
        basicDeptList.value = r.data.data
      })
  // 装填所有组织信息嵌套数据列表
  api.getDeptList('MAIN')
      .then(r => {
        treeDeptList.value = r.data.data
      })
})
</script>

<template>
  <h1>组织信息配置</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag>检索/操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-input placeholder="组织编码..." v-model="searchData.deptCode"
                @input="searchDeptList()"/>
    </el-col>
    <el-col :span="5">
      <el-input placeholder="组织名称..." v-model="searchData.deptName"
                @input="searchDeptList()"/>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="searchDeptList">查询</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="success" plain @click="addDialogVisible=true">添加组织</el-button>
    </el-col>
  </el-row>
  <!--  数据展示区-->
  <div class="space">
    <el-tag>数据列表</el-tag>
  </div>
  <el-table :data="basicDeptList.records" stripe height="550">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column prop="deptCode" sortable label="组织编码"/>
    <el-table-column prop="deptName" label="组织名称"/>
    <el-table-column prop="superiorName" label="上级组织"/>
    <el-table-column label="操作" width="130px">
      <template #default="scope">
        <el-button type="warning" plain circle @click="setDeptBefore(scope.row)">
          <el-icon color="#222222">
            <Edit/>
          </el-icon>
        </el-button>
        <el-popconfirm title="删除组织可能会导致学生和其他用户失去组织信息，确定删除？"
                       @confirm="removeDept(scope.row.deptCode)"
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
                 :total="basicDeptList.totalRow"
                 v-model:current-page="currentPage"
                 :page-count="basicDeptList.totalPage"/>
  <!--  添加对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>添加组织</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组织编码"
                  v-model="deptAddData.deptCode"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组织名称"
                  v-model="deptAddData.deptName"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-cascader style="width: 300px" size="large" v-model="cascaderPath" :options="treeDeptList"
                     :props="cascaderConfig"
                     clearable/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addDept()">添加</el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  修改对话框-->
  <el-dialog v-model="EditDialogVisible" width="400px">
    <template #title>
      <h1>修改组织信息</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组织编码"
                  v-model="deptEditData.deptCode" disabled/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="组织名称"
                  v-model="deptEditData.deptName"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-cascader style="width: 300px" size="large" v-model="cascaderPath"
                     :options="treeDeptList" :props="cascaderConfig"
                     :placeholder="deptEditData.superior" clearable/>
      </el-col>
      <el-col style="text-align: center;padding-bottom: 5px">
        <el-button style="width: 300px" type="success" size="large" plain @click="setDept()">添加</el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>