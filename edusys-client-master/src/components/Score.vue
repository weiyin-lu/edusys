<script setup>
import {inject, onMounted, ref} from "vue";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject('$api')
const store = useStore()
// 请求对象：教官所在连队的学生信息
let searchData = ref({
  year: null,
  deptId: null
})
// 请求对象：修改学生成绩
const updateData = ref({
  name: null, // 只作显示用，不作请求参数
  year: null,
  id: null,
  rawValue: null,
  score: null
})
// 数据列表：数据源信息列表
const sourceList = ref([])
// 数据列表：当前连队学生名单
const basicStudentList = ref([])
// 数据列表：有效考核科目清单
const usefulScoreList = ref([])
// 标识：数据源学年
const year = ref()
// 标识：登记项目成绩对话框
const addDialogVisible = ref(false)
// 标识：学生信息表格加载动画
const loading = ref(false)
// 函数：登记项目成绩 数据装填
const updateScoreByIdAndRawBefore = (arg1, arg2) => {
  addDialogVisible.value = true
  updateData.value.id = arg1
  updateData.value.name = arg2
}
// 函数：登记项目成绩
const updateScoreByIdAndRaw = () => {
  api.updateScoreByIdAndRaw(updateData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          addDialogVisible.value = false
          // 重新查询一次当前列表
          loading.value = true
          api.getScoreListByDept(searchData.value)
              .then(r => {
                basicStudentList.value = r.data.data
                loading.value = false
              })
        }
      })
}
// 函数：检查有效考核科目
const checkScoreItem = value => {
  for (let valueElement of usefulScoreList.value) {
    if (value == valueElement.rawValue) {
      return true;
    }
  }
  return false;
}
// 函数：获取对应考核科目的标题信息
const getScoreInfo = value => {
  for (let valueElement of usefulScoreList.value) {
    if (value == valueElement.rawValue) {
      return valueElement.description + "(" + valueElement.parseValue + ")"
    }
  }
  return "无效科目";
}
// 初始化
onMounted(() => {
  // 获取数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
  // 装填有效考核科目
  api.getUsefulScore()
      .then(r => {
        usefulScoreList.value = r.data.data
      })
})
// 数据源学年相关的初始化
const setYear = () => {
  loading.value = true
  // 获取当前教官所在连队的所有学生信息
  searchData.value.year = year.value
  searchData.value.deptId = store.state.info.department
  api.getScoreListByDept(searchData.value)
      .then(r => {
        ElMessage.success(r.data.msg)
        basicStudentList.value = r.data.data
        loading.value = false
      })
  // 把学年信息放到更新对象里
  updateData.value.year = year.value


}
</script>

<template>
  <div class="space">
    <el-select class="input" placeholder="数据源切换..." v-model="year" size="large" @change="setYear">
      <el-option v-for="item in sourceList" :key="item" :label="item" :value="item"/>
    </el-select>
  </div>
  <el-table v-loading="loading" :data="basicStudentList" height="500" stripe
            class="input">
    <el-table-column sortable prop="name" label="姓名"/>
    <el-table-column sortable prop="sexual" label="性别"/>
    <el-table-column sortable v-if="checkScoreItem('groupScore')" prop="groupScore"
                     :label="getScoreInfo('groupScore')"/>
    <el-table-column sortable v-if="checkScoreItem('weaponScore')" prop="weaponScore"
                     :label="getScoreInfo('weaponScore')"/>
    <el-table-column sortable v-if="checkScoreItem('tacticalScore')" prop="tacticalScore"
                     :label="getScoreInfo('tacticalScore')"/>
    <el-table-column sortable v-if="checkScoreItem('fightScore')" prop="fightScore"
                     :label="getScoreInfo('fightScore')"/>
    <el-table-column sortable v-if="checkScoreItem('rescueScore')" prop="rescueScore"
                     :label="getScoreInfo('rescueScore')"/>
    <el-table-column sortable v-if="checkScoreItem('nuclearScore')" prop="nuclearScore"
                     :label="getScoreInfo('nuclearScore')"/>
    <el-table-column sortable v-if="checkScoreItem('runScore')" prop="runScore"
                     :label="getScoreInfo('runScore')"/>
    <el-table-column label="操作" fixed="right">
      <template #default="scope">
        <el-button type="primary" plain circle
                   @click="updateScoreByIdAndRawBefore(scope.row.id,scope.row.name)">
          <el-icon color="#222222">
            <Edit/>
          </el-icon>
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <!--登记项目成绩对话框-->
  <el-dialog v-model="addDialogVisible" width="90%">
    <template #title>
      <h1>登记项目成绩</h1>
    </template>
    <div class="space">
      <el-text>当前学生：{{ updateData.name }}</el-text>
    </div>
    <el-row class="space">
      <el-select class="input" placeholder="考核项目" size="large"
                 v-model="updateData.rawValue">
        <el-option v-for="item in usefulScoreList" :key="item.id" :label="item.description"
                   :value="item.rawValue"/>
      </el-select>
    </el-row>
    <el-row class="space">
      <el-input class="input" size="large" placeholder="分数"
                v-model="updateData.score"/>
    </el-row>
    <el-row class="space">
      <el-button class="input" type="success" size="large" plain @click="updateScoreByIdAndRaw()">修改
      </el-button>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}

.input {
  position: relative;
  width: 100%;
}
</style>