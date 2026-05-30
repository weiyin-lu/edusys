<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import http from "../../axios/axios.js";
// 全局函数
const api = inject("$api")

// 请求对象：数据列表查询对象
const searchData = ref({
  pageNumber: 1,
  pageSize: 5,
  year: "",
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null,
  deptId: null,
})
// 请求对象：修改学生成绩
const updateData = ref({
  name: null, // 只作显示用，不作请求参数
  year: null,
  id: null,
  rawValue: null,
  score: null
})
// 请求对象：修改考核项目总分
const updateScoreTotalData = ref({
  id: "score",
  rawValue: null,
  parseValue: null
})
const caculateData = ref({
  year: null,
  searchDate: []
})
// 数据列表：查询结果
const basicStudentList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：所有考核科目列表
const basicScoreList = ref([])
// 数据列表：有效考核科目清单
const usefulScoreList = ref([])
// 数据列表：可用数据源信息
const sourceList = ref([])
// 数据列表：当前数据源下的所有学院
const academy = ref([]);
// 数据列表：当前数据源下，当前学院的所有班级
const clazz = ref([]);
// 数据列表：部门信息（树形嵌套列表）
const treeDeptList = ref([])
// 数据列表：文件下载链接
const downLoadURL = ref('')
// 标识：学年
const year = ref("")
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
// 标识：页面功能区
const componentVisible = ref(false)
// 标识：登记项目成绩对话框
const addDialogVisible = ref(false)
// 标识：配置考核科目对话框
const updateDialogVisible = ref(false)
// 标识：核算军训成绩对话框
const caculateDialogVisible = ref(false)
// 标识：下载链接按钮
const urlVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 标识：文件下载加载动画
const downLoadLoading = ref(false)
// 函数：搜索当前学院的所有班级
const searchClassByAcademy = () => {
  api.searchClassByAcademy(year.value, searchData.value.academy)
      .then(r => {
        clazz.value = r.data.data
      })
}
// 函数：查询学生成绩列表
const getScoreListDetail = () => {
  // 打开加载动画
  mainLoading.value = true
  searchData.value.pageNumber = currentPage.value
  searchData.value.pageSize = currentSize.value
  // 将级联选择器选择的最末级路径装填到请求数据中
  if (cascaderPath.value.length > 0) {
    searchData.value.deptId = cascaderPath.value[cascaderPath.value.length - 1]
  }
  api.getScoreListDetail(searchData.value)
      .then(r => {
        mainLoading.value = false
        basicStudentList.value = r.data.data
      })
}
// 函数：清空查询条件
const clearCondition = () => {
  // 清空查询使用的对象
  searchData.value.pageNumber = 1
  searchData.value.pageSize = 5
  searchData.value.academy = null
  searchData.value.clazz = null
  searchData.value.name = null
  searchData.value.sexual = null
  searchData.value.id = null
  searchData.value.deptId = null
  cascaderPath.value = []
  // 重新查询一次完整列表
  getScoreListDetail()
  ElMessage.warning("已重置查询")
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
// 函数：配置考核科目 数据装填
const updateScoreTotalBefore = () => {
  updateDialogVisible.value = true
  // 装填当前的所有考核项目情况
  api.getScoreTotalAll()
      .then(r => {
        basicScoreList.value = r.data.data
      })
}
// 函数：配置考核科目
const updateScoreTotal = () => {
  api.updateScoreTotal(updateScoreTotalData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          // 重新装填当前的所有考核项目情况
          api.getScoreTotalAll()
              .then(r => {
                basicScoreList.value = r.data.data
              })
          // 重新装填有效考核科目
          api.getUsefulScore()
              .then(r => {
                usefulScoreList.value = r.data.data
              })
        }
      })
}
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
          getScoreListDetail()
        }
      })
}
// 函数：导出文件
const getScoreFile = () => {
  if (caculateData.value.searchDate.length < 2) {
    ElMessage.warning("必须选择考勤核算时间段")
  } else {
    downLoadLoading.value = true
    api.getScoreFile(caculateData.value)
        .then(r => {
          if (r.data.code == 200) {
            ElMessage.success("导出成功")
            downLoadURL.value = http.defaults.baseURL + r.data.data
            urlVisible.value = true
            downLoadLoading.value = false
          }
        })
  }
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
  // 装填有效考核科目
  api.getUsefulScore()
      .then(r => {
        usefulScoreList.value = r.data.data
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
    // 把学年信息放到文件输出对象里
    caculateData.value.year = year.value
    // 不包含条件查询第一页
    api.getScoreListDetail(searchData.value)
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
  <h1>军训成绩核算</h1>
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
    <el-row class="space" gutter="10">
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
        <el-cascader placeholder="所属组织..." style="width: 100%" v-model="cascaderPath"
                     :options="treeDeptList"
                     :props="cascaderConfig"
                     clearable/>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-input style="width: 100%" placeholder="学号..." clearable v-model="searchData.id"/>

      </el-col>
      <el-col :span="5">
        <el-input style="width: 100%" placeholder="姓名..." clearable v-model="searchData.name"/>

      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="性别..." clearable v-model="searchData.sexual">
          <el-option key="1" label="男" value="男"/>
          <el-option key="1" label="女" value="女"/>
        </el-select>
      </el-col>
    </el-row>
    <el-row class="space">
      <el-col :span="4">
        <el-space size="10">
          <el-button type="primary" plain @click="getScoreListDetail">查询</el-button>
          <el-button type="warning" plain @click="clearCondition">清空查询</el-button>
        </el-space>
      </el-col>
    </el-row>
    <!--  数据展示区-->
    <div class="space">
      <el-tag>数据列表</el-tag>
    </div>
    <el-row class="space" :gutter="10">
      <el-col :span="8">
        <el-space>
          <el-button plain type="success" @click="updateScoreTotalBefore">配置考核科目</el-button>
          <el-button plain type="primary" @click="caculateDialogVisible = true">核算军训成绩</el-button>
        </el-space>
      </el-col>
    </el-row>
    <el-table v-loading="mainLoading" :data="basicStudentList.records" stripe
              height="400">
      <el-table-column prop="academy" label="学院" min-width="150"/>
      <el-table-column prop="clazz" label="班级" min-width="100"/>
      <el-table-column prop="id" label="学号" min-width="100"/>
      <el-table-column prop="name" label="姓名" min-width="100"/>
      <el-table-column prop="sexual" label="性别" min-width="60"/>
      <el-table-column prop="deptName" label="所属连队" min-width="100"/>
      <el-table-column label="成绩详情" min-width="300px">
        <el-table-column v-if="checkScoreItem('groupScore')" prop="groupScore"
                         :label="getScoreInfo('groupScore')"/>
        <el-table-column v-if="checkScoreItem('weaponScore')" prop="weaponScore"
                         :label="getScoreInfo('weaponScore')"/>
        <el-table-column v-if="checkScoreItem('tacticalScore')" prop="tacticalScore"
                         :label="getScoreInfo('tacticalScore')"/>
        <el-table-column v-if="checkScoreItem('fightScore')" prop="fightScore"
                         :label="getScoreInfo('fightScore')"/>
        <el-table-column v-if="checkScoreItem('rescueScore')" prop="rescueScore"
                         :label="getScoreInfo('rescueScore')"/>
        <el-table-column v-if="checkScoreItem('nuclearScore')" prop="nuclearScore"
                         :label="getScoreInfo('nuclearScore')"/>
        <el-table-column v-if="checkScoreItem('runScore')" prop="runScore" :label="getScoreInfo('runScore')"/>
        <el-table-column prop="countScore" label="考勤扣除"/>
      </el-table-column>
      <el-table-column label="操作" min-width="150" fixed="right">
        <template #default="scope">
          <el-button plain type="primary" @click="updateScoreByIdAndRawBefore(scope.row.id,scope.row.name)">
            登记项目成绩
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  分页区-->
    <el-pagination background layout="total, ->, sizes, prev, pager, next, jumper"
                   @current-change="getScoreListDetail"
                   @size-change="getScoreListDetail"
                   :total="basicStudentList.totalRow"
                   v-model:current-page="currentPage"
                   v-model:page-size="currentSize"
                   :page-sizes="[5, 10, 15, 20]"
                   :page-count="basicStudentList.totalPage"/>
  </div>
  <!--配置考核科目对话框-->
  <el-dialog v-model="updateDialogVisible" width="50%">
    <template #title>
      <h1>配置考核科目</h1>
    </template>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-table :data="basicScoreList" width="300px">
          <el-table-column prop="description" label="考核项目"/>
          <el-table-column prop="parseValue" label="项目总分"/>
        </el-table>
      </el-col>
      <el-col :span="12">
        <el-row class="space">
          <el-col :span="24">
            <el-text>分数为0的项目不视作有效项目</el-text>
          </el-col>
        </el-row>
        <el-row class="space">
          <el-col :span="24">
            <el-select style="width: 100%" placeholder="考核项目" size="large"
                       v-model="updateScoreTotalData.rawValue">
              <el-option v-for="item in basicScoreList" :key="item.id" :label="item.description"
                         :value="item.rawValue"/>
            </el-select>
          </el-col>
        </el-row>
        <el-row class="space">
          <el-col :span="24">
            <el-input style="width: 100%" size="large" placeholder="分数"
                      v-model="updateScoreTotalData.parseValue"/>
          </el-col>
        </el-row>
        <el-row class="space">
          <el-col :span="24">
            <el-button style="width: 100%" type="success" size="large" plain @click="updateScoreTotal()">修改
            </el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-dialog>
  <!--登记项目成绩对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>登记项目成绩</h1>
    </template>
    <div class="space">
      <el-text>当前学生：{{ updateData.name }}</el-text>
    </div>
    <el-col style="text-align: center" class="space">
      <el-select style="width: 300px" placeholder="考核项目" size="large"
                 v-model="updateData.rawValue">
        <el-option v-for="item in usefulScoreList" :key="item.id" :label="item.description"
                   :value="item.rawValue"/>
      </el-select>
    </el-col>
    <el-col style="text-align: center" class="space">
      <el-input style="width: 300px" size="large" placeholder="分数"
                v-model="updateData.score"/>
    </el-col>
    <el-col style="text-align: center" class="space">
      <el-button style="width: 300px" type="success" size="large" plain @click="updateScoreByIdAndRaw()">修改
      </el-button>
    </el-col>
  </el-dialog>
  <!--核算军训成绩对话框-->
  <el-dialog v-model="caculateDialogVisible" v-loading="downLoadLoading">
    <template #title>
      <h1>核算军训成绩</h1>
    </template>
    <el-col style="text-align: center" class="space">
      <el-text>考勤成绩核算范围：</el-text>
      <el-date-picker
          v-model="caculateData.searchDate"
          size="large"
          type="daterange"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          style="width: 300px"/>
    </el-col>
    <el-col style="text-align: center" class="space">
      <el-button style="width: 300px" type="success" size="large" plain @click="getScoreFile">
        计算成绩
      </el-button>
    </el-col>
    <el-col class="space">
      <el-text type="info">成绩核算方式：有效科目成绩之和-考勤扣除=总分，其中，考勤请假扣5分、考勤缺勤扣20分。
      </el-text>
    </el-col>
    <div class="download" v-show="urlVisible">
      <a :href="downLoadURL">点击下载</a>
    </div>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}

.download {
  text-align: center;
  font-size: 20px;
}
</style>