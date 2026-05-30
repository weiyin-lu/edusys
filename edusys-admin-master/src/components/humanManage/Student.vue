<script setup>
import {inject, onMounted, ref} from "vue";
import http from "../../axios/axios.js";
import {ElLoading, ElMessage} from "element-plus";
import session from "../../store/session.js";
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
// 请求数据：查询学生信息
const searchData = ref({
  pageNumber: 1,
  pageSize: 10,
  year: null,
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null
})
// 请求数据：添加学生信息
const addData = ref({
  year: "",
  academy: "",
  clazz: "",
  name: "",
  cardId: "",
  sexual: "",
  id: "",
  description: ""
})
// 请求数据：输入的数据源学年
const uploadYear = ref();
// 标识：当前页码
const currentPage = ref(1)
// 标识：学年
const year = ref("")
// 标识：页面功能区
const componentVisible = ref(false)
// 标识：页面功能区2（文件上传相关）
const componentVisible2 = ref(true)
// 标识：数据源上传对话框
const uploadDialogVisible = ref(false)
// 标识：学生添加对话框
const addDialogVisible = ref(false)
// 标识：上传区块
const uploadBoxVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 函数：获取当前页的数据
const getNowPage = () => {
  // 打开加载动画
  mainLoading.value = true
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  // 判断条件
  let result = regax.test(searchData.value.academy) ||
      regax.test(searchData.value.clazz) ||
      regax.test(searchData.value.name) ||
      regax.test(searchData.value.sexual) ||
      regax.test(searchData.value.id);
  if (result) {
    searchStudentByCondition()
  } else {
    searchFullListByPage(currentPage.value)
  }
  mainLoading.value = false
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
  // 重新查询一次完整列表
  getNowPage()
  ElMessage.warning("已重置查询")
}
// 函数：查询当前学院下的所有班级
const searchClassByAcademy = () => {
  api.searchClassByAcademy(year.value, searchData.value.academy)
      .then(r => {
        clazz.value = r.data.data
      })
}
// 函数：按页数查询数据源
const searchFullListByPage = value => {
  // 获取用户信息列表
  api.searchFullListByPage(value, year.value)
      .then(r => {
        if (r.data.code == 200) {
          basicSourceList.value = r.data.data
        }
      })
}
// 函数：根据混合条件查询数据源
const searchStudentByCondition = () => {
  searchData.value.pageNumber = currentPage.value
  api.searchByCondition(searchData.value)
      .then(r => {
        if (r.data.code == 200) {
          basicSourceList.value = r.data.data
        }
      })
}
// 函数：删除学生
const removeStudent = value => {
  api.removeStudent(value, year.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          getNowPage()
        }
      })
}
// 函数：添加学生
const addStudent = () => {
  api.addForYear(addData.value)
      .then(r => {
        ElMessage.success(r.data.msg)
        addDialogVisible.value = false
        // 清空添加用的对象
        addData.value = {
          year: year.value,
          academy: "",
          clazz: "",
          name: "",
          sexual: "",
          id: "",
          description: ""
        }
        // 重新查询一次数据
        getNowPage()
      })
}
// 函数：文件上传前操作
const uploadBefore = () => {
  uploadDialogVisible.value = true
}
// 函数：检查年份是否有效
const checkYear = () => {
  if (/^\d{4}$/.test(uploadYear.value)) {
    uploadBoxVisible.value = true
  } else {
    ElMessage.warning("请输入四位数字学年！")
  }
}
// 函数：文件上传后操作
const uploadAfter = (response) => {
  ElMessage.info("上传中...")
  // 响应
  if (response.code == 200) {
    ElMessage.success(response.msg);
  } else {
    ElMessage.error(response.msg + ":" + response.cause)
  }
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
  uploadDialogVisible.value = false
  uploadBoxVisible.value = false
}
// 初始化：获得系统中有效的学年信息
onMounted(() => {
  api.getOperateRecord()
      .then(r => {
        sourceList.value = r.data.data
      })
})
// 特殊的界面信息初始化方式：必须先输入学年才能进行初始化，否则没有操作意义
const setYear = () => {
  if (!(/^.+$/.test(year.value))) {
    ElMessage.error("必须选择学年数据源")
  } else {
    // 隐藏功能区2（文件上传相关）
    componentVisible2.value = false
    // 显示功能区1（检索展示相关）
    componentVisible.value = true
    // 打开表格加载动画
    mainLoading.value = true
    // 获得数据源列表第一页
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
    // 把学年信息放到各个请求对象里
    searchData.value.year = year.value
    addData.value.year = year.value
  }
}
</script>

<template>
  <h1>学生信息管理</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag type="danger">数据源操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-select style="width:100%" placeholder="数据源切换..." v-model="year"
                 @change="setYear">
        <el-option v-for="item in sourceList" :key="item" :label="item" :value="item"/>
      </el-select>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="setYear">确定</el-button>
    </el-col>
  </el-row>
  <div v-show="componentVisible2" class="space">
    <el-button size="large" type="success" plain @click="uploadBefore">上传数据源</el-button>
  </div>
  <div v-show="componentVisible">
    <div class="space">
      <el-tag>检索/操作</el-tag>
    </div>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-select style="width:100%" placeholder="学院..." v-model="searchData.academy"
                   @change="searchClassByAcademy">
          <el-option v-for="item in academy" :key="item" :label="item" :value="item"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-select style="width:100%" placeholder="班级..." v-model="searchData.clazz" clearable>
          <el-option v-for="item in clazz" :key="item" :label="item" :value="item"/>
        </el-select>
      </el-col>
      <el-col :span="5">
        <el-input style="width:100%" placeholder="姓名..." v-model="searchData.name" clearable/>
      </el-col>
    </el-row>
    <el-row class="space" :gutter="10">
      <el-col :span="5">
        <el-input style="width:100%" placeholder="学号..." v-model="searchData.id" clearable/>
      </el-col>
      <el-col :span="5">
        <el-select style="width:100%" placeholder="性别..." v-model="searchData.sexual">
          <el-option key="1" label="男" value="男"/>
          <el-option key="1" label="女" value="女"/>
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
    <el-table :data="basicSourceList.records" stripe height="435" v-loading="mainLoading">
      <el-table-column type="index" label="序号" min-width="60"/>
      <el-table-column prop="academy" label="学院" min-width="150"/>
      <el-table-column prop="clazz" label="班级" min-width="100"/>
      <el-table-column prop="id" label="学号" min-width="100"/>
      <el-table-column prop="name" label="姓名" min-width="100"/>
      <el-table-column prop="cardId" label="身份证号" min-width="150"/>
      <el-table-column prop="sexual" label="性别" min-width="60"/>
      <el-table-column prop="phone" label="联系电话" min-width="150"/>
      <el-table-column prop="description" label="备注" min-width="150"/>
      <el-table-column label="操作" min-width="100" fixed="right">
        <template #header>
          <el-button plain type="success" @click="addDialogVisible = true">添加学生</el-button>
        </template>
        <template #default="scope">
          <el-popconfirm title="删除学生信息后将永久无法恢复，确定删除？（真的很久！）"
                         @confirm="removeStudent(scope.row.id)"
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
                   :total="basicSourceList.totalRow"
                   v-model:current-page="currentPage"
                   :page-count="basicSourceList.totalPage"/>
  </div>
  <!--  导入数据源对话框-->
  <el-dialog v-model="uploadDialogVisible" width="50%">
    <template #title>
      <h1>导入数据源</h1>
    </template>
    <el-space>
      数据源学年:
      <el-input style="width: 300px" placeholder="输入数据源学年..." v-model="uploadYear"/>
      <el-button type="primary" plain @click="checkYear">确定</el-button>
    </el-space>
    <div v-show="uploadBoxVisible" style="padding: 10px 0px 10px">
      <el-upload drag :action="http.defaults.baseURL + '/api/manages/sources/sourceLoad/' + uploadYear"
                 method="PUT" :headers="{edusys:session.get('token').tokenValue}"
                 :multiple="false" :with-credentials="false" :show-file-list="false"
                 :on-success="uploadAfter">
        <el-icon class="el-icon--upload">
          <upload-filled/>
        </el-icon>
        <div class="el-upload__text">
          拖曳上传或<em>点击选择文件</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            xls格式，小于10M。同名数据源会<b>全量覆盖</b>过往数据。
            <a :href="http.defaults.baseURL + '/sources/template/学生数据源示例文件.xlsx'">点击下载数据源上传示例文件</a>
          </div>
        </template>
      </el-upload>
    </div>
  </el-dialog>

  <!--  添加对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>添加学生</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="学号"
                  v-model="addData.id"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="学院"
                  v-model="addData.academy"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="班级"
                  v-model="addData.clazz"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="姓名"
                  v-model="addData.name"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="身份证号"
                  v-model="addData.cardId"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-select style="width: 300px" placeholder="性别..." size="large" v-model="addData.sexual">
          <el-option key="1" label="男" value="男"/>
          <el-option key="1" label="女" value="女"/>
        </el-select>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="备注"
                  v-model="addData.description"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addStudent()">添加
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