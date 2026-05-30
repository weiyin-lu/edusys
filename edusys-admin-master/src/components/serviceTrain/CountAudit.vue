<script setup>
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {inject, onMounted, ref} from "vue";
import http from "../../axios/axios.js";

// 全局函数
const api = inject("$api")
const router = useRouter()
// 请求数据：指标查询
const searchData = ref({
  date: new Date(),
  year: null,
  amOrPm: "0",
  isNormal: true
})
// 数据列表：有效数据源
const sourceList = ref([])
// 数据列表：指标数据
const audit = ref({
  total: 0,
  countTotal: 0,
  leaveTotal: 0,
  notCountTotal: 0,
  classView: {},
  deptView: {},
  academyView: {}
})
// 数据列表：文件下载链接
const downLoadURL = ref('')
// 标识：学年
const year = ref()
// 标识：时间段列表
const dateStatusList = ref([
  {value: 0, label: '上午'},
  {value: 1, label: '下午'}
])
// 标识：tab页标签
const tabsVisible = ref("dept")
// 标识：下载对话框
const downLoadDialogVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 标识：文件下载加载动画
const downLoadLoading = ref(false)
// 函数：查询指标
const getAudit = () => {
  mainLoading.value = true
  api.getCountAuditAtDept(searchData.value)
      .then(r => {
        ElMessage.success(r.data.msg)
        audit.value = r.data.data
        mainLoading.value = false
      })
}
// 函数：下载文件
const getFile = () => {
  downLoadLoading.value = true
  api.getCountAuditFile(searchData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          downLoadDialogVisible.value = true
          downLoadURL.value = http.defaults.baseURL + r.data.data
          downLoadLoading.value = false
        } else {
          downLoadLoading.value = false
        }
      })
}
// 初始化：获得系统中有效的学年信息
onMounted(() => {
  // 获取数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
})
//设置学年，装填图表数据，生成图表，计算数值指标
const setYear = () => {
  if (!(/^.+$/.test(year.value))) {
    ElMessage.error("必须选择学年数据源")
  } else {
    mainLoading.value = true
    searchData.value.year = year.value
    api.getCountAuditAtDept(searchData.value)
        .then(r => {
          ElMessage.success(r.data.msg)
          audit.value = r.data.data
          mainLoading.value = false
        })
  }
}
</script>

<template>
  <h1>军训考勤指标大屏</h1>
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
  <div class="search" v-loading="downLoadLoading">
    <el-space>
      <el-date-picker size="large"
                      v-model="searchData.date" type="date"
                      @change="getAudit"
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      placeholder="指标统计日期"/>
      <el-select size="large" placeholder="时间段" @change="getAudit"
                 v-model="searchData.amOrPm">
        <el-option
            v-for="dateStatus in dateStatusList"
            :key="dateStatus.value"
            :label="dateStatus.label"
            :value="dateStatus.value"/>
      </el-select>
      <el-button plain type="primary" @click="getFile">下载当前指标明细</el-button>
      <el-switch
          v-model="searchData.isNormal"
          size="large"
          inline-prompt
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
          active-text="仅导出考勤异常学生"
          inactive-text="导出全部的军训学生"
      />
    </el-space>
  </div>
  <!--总指标-->
  <el-card v-loading="mainLoading">
    <el-row class="audit1">
      <el-col :span="24">
        <el-statistic class="audit2" title="军训总人数" :value="audit.total"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8" class="audit1">
        <el-statistic class="audit2" title="出勤" :value="audit.countTotal"/>
      </el-col>
      <el-col :span="8" class="audit1">
        <el-statistic class="audit2" title="请假" :value="audit.leaveTotal"/>
      </el-col>
      <el-col :span="8" class="audit1">
        <el-statistic class="audit2" title="缺勤" :value="audit.notCountTotal"/>
      </el-col>
    </el-row>
  </el-card>
  <!--签到情况详情-->
  <el-tabs
      v-loading="mainLoading"
      v-model="tabsVisible"
      stretch
      type="card">
    <el-tab-pane label="学院视图" name="academy">
      <el-scrollbar height="350">
        <el-row v-for="(item,index) in audit.academyView" class="space">
          <el-col :span="24">
            <el-card shadow="hover">
              <el-row>
                <el-col :span="4">
                  <el-statistic title="班级" :value="index"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="总人数" :value="item.total"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="出勤" :value="item.countTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="请假" :value="item.leaveTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="缺勤" :value="item.notCountTotal"/>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-tab-pane>
    <el-tab-pane label="连队视图" name="dept">
      <el-scrollbar height="350">
        <el-row v-for="(item,index) in audit.deptView" class="space">
          <el-col :span="24">
            <el-card shadow="hover">
              <el-row>
                <el-col :span="4">
                  <el-statistic title="连队" :value="index"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="总人数" :value="item.total"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="出勤" :value="item.countTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="请假" :value="item.leaveTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="缺勤" :value="item.notCountTotal"/>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-tab-pane>
    <el-tab-pane label="班级视图" name="clazz">
      <el-scrollbar height="350">
        <el-row v-for="(item,index) in audit.classView" class="space">
          <el-col :span="24">
            <el-card shadow="hover">
              <el-row>
                <el-col :span="4">
                  <el-statistic title="班级" :value="index"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="总人数" :value="item.total"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="出勤" :value="item.countTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="请假" :value="item.leaveTotal"/>
                </el-col>
                <el-col :span="5" class="audit1">
                  <el-statistic title="缺勤" :value="item.notCountTotal"/>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-tab-pane>
  </el-tabs>
  <el-dialog v-model="downLoadDialogVisible" width="400">
    <template #title>
      <h1>明细文件下载</h1>
    </template>
    <div class="download">
      <a :href="downLoadURL">点击下载</a>
    </div>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}

.search {
  text-align: center;
  padding: 0 0 10px;
}

.audit1 {
  text-align: center;
}

.audit2 {
  --el-statistic-title-font-size: 15px;
  --el-statistic-content-font-size: 45px;
}

.download {
  text-align: center;
  font-size: 20px;
}
</style>