<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {getLodop} from '../../clodop/CLodopfuncs'
// 全局函数
const api = inject("$api")

// 数据列表：学生数据源信息
const basicSourceList = ref({
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
// 数据列表：所有有效数据源
const sourceList = ref([]);
// 数据列表：转换后的服装信息列表
const parseList = ref({
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null,
  description: null,
  checkStatus: null,
  height: null,
  weight: null,
  phone: null,
  shoeSize: null,
  receiveStatus: null,
  parseShoes: null,
  parseShirt: null,
  parseCloth: null
})
// 请求数据：查询学生信息
const searchData = ref({
  pageNumber: 1,
  pageSize: 10,
  year: null,
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null,
  shoeSize: null,
  checkStatus: null,
  receiveStatus: null,
})
// 标识：学年
const year = ref("")
// 标识：登记状态映射对象
const checkList = ref([
  {value: 0, label: '未登记'},
  {value: 1, label: '已登记'}
])
// 标识：领取状态映射对象
const receiveList = ref([
  {value: 0, label: '未领取'},
  {value: 1, label: '已领取'}
])
// 标识：当前页码
const currentPage = ref(1)
// 标识：页面功能区
const componentVisible = ref(false)
// 标识：服装单详情抽屉
const drawerVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 函数：获取当前页的数据
const getNowPage = () => {
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  // 判断条件
  let result = regax.test(searchData.value.academy) ||
      regax.test(searchData.value.clazz) ||
      regax.test(searchData.value.name) ||
      regax.test(searchData.value.sexual) ||
      regax.test(searchData.value.id) ||
      regax.test(searchData.value.receiveStatus) ||
      regax.text(searchData.value.checkStatus);
  if (result) {
    searchStudentByCondition()
  } else {
    api.searchFullListByPage(currentPage.value, year.value)
        .then(r => {
          basicSourceList.value = r.data.data
        })
  }
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
  searchData.value.checkStatus = null
  searchData.value.receiveStatus = null
  // 重新查询一次完整列表
  getNowPage()
  ElMessage.warning("已重置查询")
}
// 函数：搜索当前学院的所有班级
const searchClassByAcademy = () => {
  api.searchClassByAcademy(year.value, searchData.value.academy)
      .then(r => {
        clazz.value = r.data.data
      })
}
// 函数：根据条件查询学生信息
const searchStudentByCondition = () => {
  searchData.value.pageNumber = currentPage.value
  api.searchByCondition(searchData.value)
      .then(r => {
        basicSourceList.value = r.data.data
      })
}
// 函数：手动数据登记
const getClothStep1 = value => {
  // 把登记标识设置为1
  api.updateCheckStatusById(year.value, value, "1")
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(value + " 手动服装登记:" + r.data.msg)
          // 操作一次重新查询用于更新按钮的状态
          getNowPage()
        }
      })

}
//函数：服装领取登记
const getClothStep2 = value => {
  // 把登记标识和领取标识都设置为1
  api.updateCheckStatusById(year.value, value, "1") //忽略此操作的回调
  api.updateReceiveStatusById(year.value, value, "1")
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(value + " 领取登记:" + r.data.msg)
          // 操作一次重新查询用于更新按钮的状态
          getNowPage()
        }
      })

}
// 函数：取消服装领取登记
const getClothCancel = value => {
  // 把领取标识设置为0
  api.updateReceiveStatusById(year.value, value, "0")
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(value + " 取消登记:" + r.data.msg)
          // 操作一次重新查询用于更新按钮的状态
          getNowPage()
        }
      })

}
// 函数：获得一个学生的服装转换列表
const getParseList = value => {
  api.searchSourceInfoBeforeParse(value, year.value)
      .then(r => {
        parseList.value = r.data.data
      })
  drawerVisible.value = true
}
// 函数：打印小票
const printHandle = () => {
  let LODOP = getLodop()
  LODOP.SET_LICENSES("", "13F0BE65846276CB60111433C6280000", "", "");
  LODOP.PRINT_INIT("服装分发工单");
  LODOP.ADD_PRINT_HTM(5, 5, '100%', '100%', document.getElementById('print').innerHTML);
  // LODOP.PREVIEW();
  LODOP.PRINT();
}
onMounted(() => {
  // 查询所有可用的数据源列表
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
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
    // 获得数据源数据列表第一页
    api.searchFullListByPage(1, year.value)
        .then(r => {
          if (r.data.code == 200) {
            ElMessage.success("数据源获取成功")
            basicSourceList.value = r.data.data
            // 关闭加载动画
            mainLoading.value = false
          }
        })
    // 获取当前数据源所有有效学院
    api.getAcademyList(year.value)
        .then(r => {
          academy.value = r.data.data
        })
    // 把学年信息放到查询对象里
    searchData.value.year = year.value
  }
}
</script>

<template>
  <h1>服装分发管理</h1>
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
  <div v-show="componentVisible">
    <div class="space">
      <el-tag>检索/操作</el-tag>
    </div>
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
        <el-input style="width: 100%" placeholder="姓名..." clearable v-model="searchData.name"/>
      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="性别..." clearable v-model="searchData.sexual">
          <el-option key="1" label="男" value="男"/>
          <el-option key="1" label="女" value="女"/>
        </el-select>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-input style="width: 100%" placeholder="学号..." clearable v-model="searchData.id"/>
      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="服装登记状态..." clearable
                   v-model="searchData.checkStatus">
          <el-option
              v-for="item in checkList"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-select style="width: 100%" placeholder="服装领取状态..." clearable
                   v-model="searchData.receiveStatus">
          <el-option
              v-for="item in receiveList"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="4">
        <el-space size="10">
          <el-button type="primary" plain @click="searchStudentByCondition">查询</el-button>
          <el-button type="warning" plain @click="clearCondition">清空查询</el-button>
        </el-space>
      </el-col>
    </el-row>
    <!--  数据展示区-->
    <div class="space">
      <el-tag>数据列表</el-tag>
    </div>
    <el-table v-loading="mainLoading" :data="basicSourceList.records" stripe height="430">
      <el-table-column type="index" label="序号" min-width="60"/>
      <el-table-column prop="academy" label="学院" min-width="150"/>
      <el-table-column prop="clazz" label="班级" min-width="100"/>
      <el-table-column prop="id" label="学号" min-width="150"/>
      <el-table-column prop="name" label="姓名" min-width="100"/>
      <el-table-column prop="sexual" label="性别" min-width="60"/>
      <el-table-column label="服装登记状态" min-width="60">
        <template #default="scope">
          <el-tag type="success" v-show="scope.row.checkStatus == 1">已登记</el-tag>
          <el-tag type="danger" v-show="scope.row.checkStatus != 1">未登记</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="receiveStatus" label="服装领取状态" min-width="60">
        <template #default="scope">
          <el-tag type="success" v-show="scope.row.receiveStatus == 1">已领取</el-tag>
          <el-tag type="warning" v-show="scope.row.receiveStatus != 1">未领取</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="300" fixed="right">
        <template #default="scope">
          <el-button type="primary" plain @click="getParseList(scope.row.id)">查看服装单
          </el-button>
          <el-button type="danger" plain
                     @click="getClothStep1(scope.row.id)"
                     v-show="scope.row.checkStatus == 0 && scope.row.receiveStatus == 0">手动服装登记
          </el-button>
          <el-button type="warning" plain
                     @click="getClothStep2(scope.row.id)"
                     v-show="scope.row.checkStatus == 1 && scope.row.receiveStatus == 0">领取登记
          </el-button>
          <el-button plain
                     @click="getClothCancel(scope.row.id)"
                     v-show=" scope.row.receiveStatus == 1">取消领取登记
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  分页区-->
    <el-pagination background layout="total, ->, prev, pager, next, jumper" @current-change="getNowPage()"
                   :total="basicSourceList.totalRow"
                   v-model:current-page="currentPage"
                   :page-count="basicSourceList.totalPage"/>
  </div>
  <!--  服装单抽屉-->
  <el-drawer
      v-model="drawerVisible"
      direction="ltr"
      size="30%">
    <div id="print">
      <h2 style="text-align: center">服装工单</h2>
      <el-descriptions border :column="1">
        <el-descriptions-item label="学号">
          <h4 style="text-align: center">{{ parseList.id }}</h4>
        </el-descriptions-item>
        <el-descriptions-item label="姓名">
          <h4 style="text-align: center">{{ parseList.name }}</h4>
        </el-descriptions-item>
        <el-descriptions-item label="建议鞋码">
          <h4 style="text-align: center">{{ parseList.parseShoes }}</h4></el-descriptions-item>
        <el-descriptions-item label="建议衣码/衬衫">
          <h4 style="text-align: center">{{ parseList.parseShirt }}</h4>
        </el-descriptions-item>
        <el-descriptions-item label="建议衣码/外装">
          <h4 style="text-align: center">{{ parseList.parseCloth }}</h4>
        </el-descriptions-item>
        <el-descriptions-item label="身高">
          <h4 style="text-align: center">
            {{ parseList.height == null ? "未填写" : parseList.height + 'cm' }}
          </h4>
        </el-descriptions-item>
        <el-descriptions-item label="体重">
          <h4 style="text-align: center">
            {{ parseList.weight == null ? "未填写" : parseList.weight + 'kg' }}
          </h4>
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <div style="text-align: center">
      <el-divider/>
      <el-button style="width:100%" size="large" type="primary" plain @click="printHandle()">打印服装工单
      </el-button>
    </div>
  </el-drawer>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>