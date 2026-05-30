<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")

// 请求对象：数据列表查询对象
const searchData = ref({
  pageNumber: 1,
  pageSize: 5,
  searchDate: [new Date(), new Date()],
  year: "2023",
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null,
  deptId: null
})
// 请求对象：考勤信息变更对象
const updateData = ref({
  year: "",
  date: new Date(),
  amOrPm: null,
  newCountStatus: null,
  indexId: []
})
// 数据列表：可用数据源信息
const sourceList = ref([])
// 数据列表：查询结果
const basicStudentList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：当前数据源下的所有学院
const academy = ref([]);
// 数据列表：当前数据源下，当前学院的所有班级
const clazz = ref([]);
// 数据列表：选中学生信息
const countList = ref([])
// 数据列表：部门信息（树形嵌套列表）
const treeDeptList = ref([])
// 标识：时间段列表
const dateStatusList = ref([
  {value: 0, label: '上午'},
  {value: 1, label: '下午'}
])
// 标识：考勤状态列表
const countStatusList = ref([
  {value: 0, label: '缺勤'},
  {value: 1, label: '出勤'},
  {value: 2, label: '请假'},
])
// 标识：当前页码
const currentPage = ref(1)
// 标识：当前每页数量
const currentSize = ref(5)
// 标识：级联选择器配置
const cascaderConfig = ref({
  checkStrictly: true,
  value: "deptCode",
  label: "deptName",
  children: "childList"
})
// 标识：级联选择器选择的路径数组
const cascaderPath = ref([])
// 标识：学年
const year = ref("")
// 标识：页面功能区
const componentVisible = ref(false)
// 标识：变更考勤信息对话框
const updateCountDialogVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)

// 函数：搜索当前学院的所有班级
const searchClassByAcademy = () => {
  api.searchClassByAcademy(year.value, searchData.value.academy)
      .then(r => {
        clazz.value = r.data.data
      })
}
// 函数：条件复杂查询学生考勤信息列表
const getCountListDetail = () => {
  // 打开加载动画
  mainLoading.value = true
  // 传入当前页码
  searchData.value.pageNumber = currentPage.value
  // 传入当前分页量
  searchData.value.pageSize = currentSize.value
  // 将级联选择器选择的最末级路径装填到请求数据中
  if (cascaderPath.value.length > 0) {
    searchData.value.deptId = cascaderPath.value[cascaderPath.value.length - 1]
  }
  // 查询数据库
  api.getCountListDetail(searchData.value)
      .then(r => {
        basicStudentList.value = r.data.data
        // 关闭加载动画
        mainLoading.value = false
      })
}

// 函数：清空查询条件
const clearCondition = () => {
  // 清空查询使用的对象
  searchData.value.pageNumber = 1
  searchData.value.pageSize = 10
  searchData.value.academy = null
  searchData.value.clazz = null
  searchData.value.name = null
  searchData.value.sexual = null
  searchData.value.id = null
  searchData.value.searchDate = [new Date(), new Date()]
  cascaderPath.value = []
  // 重新查询一次完整列表
  getCountListDetail()
  ElMessage.warning("已重置查询")
}

// 函数：装填选中学生信息临时列表
const selectChange = val => {
  countList.value = []
  for (const student in val) {
    countList.value.push(val[student].indexId)
  }
}
// 函数：单独修改考勤信息 数据装填
const updateCountAlone = value => {
  // 把当前选中的单个学生压入列表
  countList.value = []
  countList.value.push(value)
  // 转到通用数据装填
  updateCountBefore()
}
// 函数：修改考勤信息 数据装填
const updateCountBefore = () => {
  // 展示对话框
  updateCountDialogVisible.value = true
  // 填入选择列表
  updateData.value.indexId = countList.value
}
// 函数:修改考勤信息
const updateCount = () => {
  if (updateData.value.amOrPm == null) {
    ElMessage.warning("时间段不得为空")
  } else if (updateData.value.newCountStatus == null) {
    ElMessage.warning("考勤状态不得为空")
  }
  api.updateStudentCount(updateData.value)
      .then(r => {
        ElMessage.success(r.data.msg)
        updateCountDialogVisible.value = false
        getCountListDetail()
      })
}
// 初始化
onMounted(() => {
  // 查询所有可用的数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
  // 装填所有部门信息嵌套数据列表
  api.getDeptList('MAIN')
      .then(r => {
        treeDeptList.value = r.data.data
      })
})
// 特殊的界面信息初始化方式：必须先输入学年才能进行初始化，否则没有操作意义
const setYear = () => {
  if (!(/^.+$/.test(year.value))) {
    ElMessage.error("必须输入学年")
  } else {
    // 显示功能区（检索展示相关）
    componentVisible.value = true
    // 打开加载动画
    mainLoading.value = true
    // 把学年信息放到查询对象里
    searchData.value.year = year.value
    // 把学年信息放到更新对象里
    updateData.value.year = year.value
    // 不包含条件查询第一页
    api.getCountListDetail(searchData.value)
        .then(r => {
          ElMessage.success("数据源获取成功")
          basicStudentList.value = r.data.data
          // 关闭加载动画
          mainLoading.value = false
        })
    // 获取当前数据源所有有效学院
    api.getAcademyList(year.value)
        .then(r => {
          academy.value = r.data.data
        })
  }
}
</script>

<template>
  <h1>考勤综合管理</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag type="danger">选择数据源（必需）</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-select style="width: 100%" placeholder="数据源切换..." v-model="year"
                 @change="setYear">
        <el-option v-for="item in sourceList" :key="item" :label="item" :value="item"/>
      </el-select>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="setYear">确定</el-button>
    </el-col>
  </el-row>
  <!--操作区-->
  <div v-show="componentVisible">
    <div class="space">
      <el-tag>检索/操作</el-tag>
    </div>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-date-picker
            v-model="searchData.searchDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"/>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-cascader placeholder="所属连队..." style="width: 100%" v-model="cascaderPath"
                     :options="treeDeptList"
                     :props="cascaderConfig"
                     clearable/>
      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="性别..." clearable v-model="searchData.sexual">
          <el-option key="1" label="男" value="男"/>
          <el-option key="1" label="女" value="女"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 100%" placeholder="姓名..." clearable v-model="searchData.name"/>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="学院..." v-model="searchData.academy"
                   @change="searchClassByAcademy">
          <el-option v-for="item in academy" :key="item" :label="item" :value="item"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="班级..." clearable v-model="searchData.clazz">
          <el-option v-for="item in clazz" :key="item" :label="item" :value="item"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 100%" placeholder="学号..." clearable v-model="searchData.id"/>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="4">
        <el-space size="10">
          <el-button type="primary" plain @click="getCountListDetail">查询</el-button>
          <el-button type="warning" plain @click="clearCondition">清空查询</el-button>
        </el-space>
      </el-col>
    </el-row>
    <!--  数据展示区-->
    <div class="space">
      <el-tag>数据列表</el-tag>
    </div>
    <el-table v-loading="mainLoading" @selection-change="selectChange" :data="basicStudentList.records" stripe
              height="430">
      <el-table-column type="selection" min-width="40"/>
      <el-table-column prop="academy" label="学院" min-width="150"/>
      <el-table-column prop="clazz" label="班级" min-width="100"/>
      <el-table-column prop="id" label="学号" min-width="100"/>
      <el-table-column prop="name" label="姓名" min-width="100"/>
      <el-table-column prop="sexual" label="性别" min-width="60"/>
      <el-table-column prop="deptName" label="所属连队" min-width="100"/>
      <el-table-column label="考勤信息(点击展开)" type="expand" min-width="100">
        <template #default="scope">
          <el-table :data="scope.row.countStatus">
            <el-table-column :label="scope.row.name + ' 考勤信息'">
              <el-table-column prop="date" label="日期"/>
              <el-table-column prop="amStatus" label="上午">
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
              <el-table-column prop="pmStatus" label="下午" min-width="200">
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
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column>
        <template #header>
          <el-button plain type="success" @click="updateCountBefore">批量变更考勤</el-button>
        </template>
        <template #default="scope">
          <el-button plain type="primary" @click="updateCountAlone(scope.row.indexId)">变更学生考勤
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  分页区-->
    <el-pagination background layout="total, ->, sizes, prev, pager, next, jumper"
                   @current-change="getCountListDetail"
                   @size-change="getCountListDetail"
                   :total="basicStudentList.totalRow"
                   v-model:current-page="currentPage"
                   v-model:page-size="currentSize"
                   :page-sizes="[5, 10, 15, 20]"
                   :page-count="basicStudentList.totalPage"/>
  </div>
  <!--考勤信息变更对话框-->
  <el-dialog v-model="updateCountDialogVisible" width="400px">
    <template #title>
      <h1>考勤信息变更</h1>
    </template>
    <el-row>
      <el-text>当前共有</el-text>
      <el-text type="danger">{{ countList.length }}</el-text>
      <el-text>名学生的考勤信息会被修改。</el-text>
      <el-col style="text-align: center" class="space">
        <el-date-picker style="width: 300px" size="large"
                        v-model="updateData.date" type="date"
                        format="YYYY-MM-DD"
                        value-format="YYYY-MM-DD"
                        placeholder="待修改日期"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-select style="width: 300px" size="large" placeholder="时间段" v-model="updateData.amOrPm">
          <el-option
              v-for="dateStatus in dateStatusList"
              :key="dateStatus.value"
              :label="dateStatus.label"
              :value="dateStatus.value"/>
        </el-select>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-select style="width: 300px" size="large" placeholder="变更状态"
                   v-model="updateData.newCountStatus">
          <el-option
              v-for="countStatus in countStatusList"
              :key="countStatus.value"
              :label="countStatus.label"
              :value="countStatus.value"/>
        </el-select>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="updateCount">确定变更
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