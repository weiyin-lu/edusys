<script setup>
import {inject, onMounted, ref} from "vue";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject('$api')
const store = useStore()
// 变量：时钟信息
let clock = ref();
let amOrPm = ref()
// 数据列表：数据源信息列表
const sourceList = ref([])
// 数据列表：当前连队学生名单
const basicStudentList = ref([])
// 数据列表：选中学生信息
const countList = ref([])
// 标识：数据源学年
const year = ref()
// 标识：学生信息表格加载动画
const loading = ref(false)
// 选中学生信息装填
const selectChange = val => {
  console.log(val)
  countList.value = []
  for (const student in val) {
    console.log(student)
    countList.value.push(val[student].indexId)
  }
}
// 学生出勤/缺勤
const setCount = status => {
  // 签到请求对象
  let countRequest = ref({
    year: year.value,
    indexId: countList.value,
    status: status
  })
  // 学生信息请求对象
  let studentRequest = ref({
    year: year.value,
    deptId: store.state.info.department
  })
  loading.value = true
  api.setCount(countRequest.value)
      .then(r => {
        if(r.data.code == 200) {
          ElMessage.success(r.data.msg)
          // 重新获取一次学生信息
          api.getCountListByDept(studentRequest.value)
              .then(r => {
                basicStudentList.value = r.data.data
                loading.value = false
              })
        }
      })
}
// 初始化
onMounted(() => {
  // 生成一个实时时钟
  setInterval(() => {
    let now = new Date()
    clock.value = now.toLocaleString()
    if (now.getHours() >= 0 && now.getHours() < 12) {
      amOrPm.value = "上午"
    } else {
      amOrPm.value = "下午"
    }
  })
  // 获取数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
})
// 数据源学年相关的初始化
const setYear = () => {
  loading.value = true
  // 获取当前教官所在连队的所有学生信息
  let request = ref({
    year: year.value,
    deptId: store.state.info.department
  })
  api.getCountListByDept(request.value)
      .then(r => {
        basicStudentList.value = r.data.data
        ElMessage.success(r.data.msg)
        loading.value = false
      })
}
</script>

<template>
  <div class="space">
    考勤时间段
    <el-tag>{{ amOrPm }}</el-tag>
    当前时间
    <el-tag> {{ clock }}</el-tag>
  </div>
  <div class="space">
    <el-select class="input" placeholder="数据源切换..." v-model="year" size="large" @change="setYear">
      <el-option v-for="item in sourceList" :key="item" :label="item" :value="item"/>
    </el-select>
  </div>
  <el-table @selection-change="selectChange" v-loading="loading" :data="basicStudentList" height="500"
            border stripe class="input">
    <el-table-column type="selection"/>
    <el-table-column sortable prop="name" label="姓名"/>
    <el-table-column sortable prop="sexual" label="性别"/>
    <el-table-column sortable prop="amStatus" label="上午">
      <template #default="scope">
        <el-tag v-if="scope.row.amStatus == '出勤'" type="success">
          {{ scope.row.amStatus }}
        </el-tag>
        <el-tag v-if="scope.row.amStatus == '缺勤'" type="danger">
          {{ scope.row.amStatus }}
        </el-tag>
        <el-tag v-if="scope.row.amStatus == '请假'" type="warning">
          {{ scope.row.amStatus }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column sortable prop="pmStatus" label="下午">
      <template #default="scope">
        <el-tag v-if="scope.row.pmStatus == '出勤'" type="success">
          {{ scope.row.pmStatus }}
        </el-tag>
        <el-tag v-if="scope.row.pmStatus == '缺勤'" type="danger">
          {{ scope.row.pmStatus }}
        </el-tag>
        <el-tag v-if="scope.row.pmStatus == '请假'" type="warning">
          {{ scope.row.pmStatus }}
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
  <div class="space">
      <el-row>
        <el-col :span="12">
          <el-button class="input2" type="success" plain size="large" @click="setCount(true)">
            标记选中学生 出勤
          </el-button>
        </el-col>
        <el-col :span="12">
          <el-button class="input2" type="danger" plain size="large" @click="setCount(false)">
            标记选中学生 缺勤</el-button>
        </el-col>
      </el-row>
  </div>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}

.input {
  position: relative;
  width: 100%;
}

.input2 {
  position: relative;
  width: 100%;
}
</style>