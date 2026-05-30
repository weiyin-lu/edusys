<script setup>
import {ElMessage} from "element-plus";
import {inject, onMounted, ref} from "vue";
import * as echarts from 'echarts';

// 全局函数
const api = inject("$api")

// 数据列表：有效数据源
const sourceList = ref([])
// 数据列表：指标数据
const basicAuditList = ref([{
  academy: null,
  clazz: null,
  total: null,
  notCheck: null,
  checked: null,
  notReceive: null,
  received: null
}])
// 标识：学年
const year = ref("")
// 标识：主要区域加载动画
const mainLoading = ref(false)

// 数据列表：数值指标对象
const audit = ref({
  total: 0,
  notCheck: 0,
  checked: 0,
  notReceive: 0,
  received: 0
})

// 图表：图表初始化对象
const charts = ref()
// 图表：绘制图表的页面元素
const main = ref()
// 图表：图表1
var option1 = ref({
  title: [
    {
      text: '各学院服装分发详情'
    }
  ],
  dataset: {
    dimensions: [
      {name: 'academy', displayName: '学院'},
      {name: 'total', displayName: '总计'},
      {name: 'checked', displayName: '已登记'},
      {name: 'received', displayName: '已领取'},
      {name: 'notCheck', displayName: '未登记'},
      {name: 'notReceive', displayName: '未领取'},
    ],
    source: basicAuditList.value
  },
  legend: {},
  tooltip: {},
  xAxis: {},
  yAxis: {
    type: 'category',
  },
  series: [
    {
      name: '学院总人数',
      type: 'bar',
      z: "-3",
      barGap: '-100%',
      color: '#30A498'
    },
    {
      name: '已登记人数',
      type: 'bar',
      z: "-2",
      barGap: '-100%',
      color: '#5BC399'
    },
    {
      name: '已领取人数',
      type: 'bar',
      z: "-1",
      barGap: '-100%',
      color: '#93DF93'
    },
  ]
})

// 初始化：获得系统中有效的学年信息
onMounted(() => {
  // 获取数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
  // 绘制图表
  let mainEl = main.value
  charts.value = echarts.init(mainEl, "light");
  charts.value.setOption(option1.value);
})
//设置学年，装填图表数据，生成图表，计算数值指标
const setYear = () => {
  if (!(/^.+$/.test(year.value))) {
    ElMessage.error("必须选择学年数据源")
  } else {
    // 打开加载动画
    mainLoading.value = true
    // 查询当前数据源的指标
    api.getClothAuditAtAcademy(year.value)
        .then(r => {
          // 装填指标数据
          basicAuditList.value = r.data.data

          // 把指标数据装填到图表里
          option1.value.dataset.source = basicAuditList.value
          // 重绘图表
          charts.value.setOption(option1.value)
          // 提示消息
          ElMessage.success(r.data.msg)

          // 运算数值指标
          // 先把数据列表重置
          audit.value.total = 0
          audit.value.checked = 0
          audit.value.notCheck = 0
          audit.value.received = 0
          audit.value.notReceive = 0
          // 运算各个统计数值
          for (let index in basicAuditList.value) {
            audit.value.total += Number.parseInt(basicAuditList.value[index].total)
            audit.value.checked += Number.parseInt(basicAuditList.value[index].checked)
            audit.value.notCheck += Number.parseInt(basicAuditList.value[index].notCheck)
            audit.value.received += Number.parseInt(basicAuditList.value[index].received)
            audit.value.notReceive += Number.parseInt(basicAuditList.value[index].notReceive)
          }
          // 关闭加载动画
          mainLoading.value = false
        })
  }
}
</script>

<template>
  <h1>服装领取指标大屏</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag type="danger">选择数据源（必需）</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-select style="width: 300px" placeholder="数据源切换..." v-model="year"
                 @change="setYear">
        <el-option v-for="item in sourceList" :key="item" :label="item" :value="item"/>
      </el-select>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="setYear">确定</el-button>
    </el-col>
  </el-row>
  <!--  指标展示区-->
  <el-card v-loading="mainLoading">
    <el-row class="audit1">
      <el-col :span="24">
        <el-statistic class="audit2" title="应领服装总人数" :value="audit.total"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6" class="audit1">
        <el-statistic class="audit2" title="未登记" :value="audit.notCheck"/>
      </el-col>
      <el-col :span="6" class="audit1">
        <el-statistic class="audit2" title="已登记" :value="audit.checked"/>
      </el-col>
      <el-col :span="6" class="audit1">
        <el-statistic class="audit2" title="未领取" :value="audit.notReceive"/>
      </el-col>
      <el-col :span="6" class="audit1">
        <el-statistic class="audit2" title="已领取" :value="audit.received"/>
      </el-col>
    </el-row>
  </el-card>
  <div ref="main" style="width: 100%;height:80%;"/>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}

.audit1 {
  text-align: center;
}

.audit2 {
  --el-statistic-title-font-size: 15px;
  --el-statistic-content-font-size: 45px;
}
</style>