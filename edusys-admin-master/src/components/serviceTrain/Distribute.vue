<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
// 请求数据：查询学生信息
const studentSearchData = ref({
  pageNumber: 1,
  pageSize: 10,
  year: null,
  academy: null,
  clazz: null,
  name: null,
  sexual: null,
  id: null,
  deptId: null,
  newDeptId: null,
})
// 请求数据：查询用户信息
const userSearchData = ref({
  pageNumber: 1,
  pageSize: 10,
  userId: null,
  name: null,
  phone: null,
  department: null,
  post: null,
  sexual: null
})
// 数据列表：所有有效数据源
const sourceList = ref([]);
// 数据列表：学生数据源信息
const basicSourceList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：所有用户数据
const basicUserInfoList = ref({
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
// 数据列表：部门信息（树形嵌套列表）
const treeDeptList = ref([])
// 数据列表：分配指标列表
const distributeAuditList = ref([])
// 标识：学生级联选择器选择的路径数组
const studentCascaderPath = ref([])
// 标识：更新组织使用的路径数组
const updateCascaderPath = ref([])
// 标识：级联选择器配置
const cascaderConfig = ref({
  checkStrictly: true,
  value: "deptCode",
  label: "deptName",
  children: "childList"
})
// 标识：学生当前页码
const studentCurrentPage = ref(1)
// 标识：教官当前页码
const userCurrentPage = ref(1)
// 标识：学年
const year = ref("")
// 标识：页面功能区
const componentVisible = ref(false)
// 标识：分配切换栏
const tabsVisible = ref('student')
// 标识：学生分配对话框
const distributeDialogVisible = ref(false)
// 标识：学生区域加载动画
const studentLoading = ref(false)
// 标识：教官区域加载动画
const userLoading = ref(false)
// 标识：分配情况区域加载动画
const auditLoading = ref(false)
// 函数：获取学生当前页的数据
const studentGetNowPage = () => {
  // 打开加载动画
  studentLoading.value = true
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  // 判断条件
  let result = regax.test(studentSearchData.value.academy) ||
      regax.test(studentSearchData.value.clazz) ||
      regax.test(studentSearchData.value.name) ||
      regax.test(studentSearchData.value.sexual) ||
      regax.test(studentSearchData.value.id) ||
      regax.test(studentSearchData.value.deptId);
  if (result) {
    searchStudentByCondition()
  } else {
    searchFullListByPage(studentCurrentPage.value)
  }
  // 关闭加载动画
  studentLoading.value = false
}
// 函数：清空学生查询条件
const studentClearCondition = () => {
  // 清空查询使用的对象
  studentSearchData.value.pageNumber = 1
  studentSearchData.value.pageSize = 10
  studentSearchData.value.academy = null
  studentSearchData.value.clazz = null
  studentSearchData.value.name = null
  studentSearchData.value.sexual = null
  studentSearchData.value.id = null
  studentSearchData.value.deptId = null
  studentCascaderPath.value = [] //这里要清空级联选择器传入的值
  // 重新查询一次完整列表
  studentGetNowPage()
  ElMessage.warning("已重置查询")
}
// 函数：查询当前学院下的所有班级
const searchClassByAcademy = () => {
  api.searchClassByAcademy(year.value, studentSearchData.value.academy)
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
  // 传入页码
  studentSearchData.value.pageNumber = studentCurrentPage.value
  // 将级联选择器选择的最末级路径装填到请求数据中
  studentSearchData.value.deptId = studentCascaderPath.value[studentCascaderPath.value.length - 1]
  // 查询
  api.searchByCondition(studentSearchData.value)
      .then(r => {
        if (r.data.code == 200) {
          basicSourceList.value = r.data.data
        }
      })
}
// 函数：批量学生分配前数据装填
const studentDistributeBefore = () => {
  // 打开对话框
  distributeDialogVisible.value = true
  // 把当前连队传递给更新对话框中
  updateCascaderPath.value = studentCascaderPath.value
  studentSearchData.value.newDeptId = studentSearchData.value.deptId
}
// 函数：批量为学生分配连队
const studentCommitDistributeBatch = () => {
  // 将级联选择器选择的最末级路径装填到更新数据中
  studentSearchData.value.newDeptId = updateCascaderPath.value[updateCascaderPath.value.length - 1]
  // 更新数据
  api.updateStudentDeptByCondition(studentSearchData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          distributeDialogVisible.value = false
          // 打开加载动画
          auditLoading.value = true
          // 重新获取当前分配情况统计列表
          api.getDistributeAudit(year.value)
              .then(r => {
                distributeAuditList.value = r.data.data
                // 关闭加载动画
                auditLoading.value = false
              })
          // 重新获取当前页
          studentGetNowPage()
        }
      })
}
// 函数：为单个学生分配连队
const studentCommitDistributeAlone = value => {
  // 请求使用的对象
  let distributeDTO = ref({
    year: year.value,
    id: value,
    newDeptId: null
  })
  // 拼装请求对象
  distributeDTO.value.newDeptId = updateCascaderPath.value[updateCascaderPath.value.length - 1]
  // 更新数据
  api.updateStudentDeptById(distributeDTO.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          distributeDialogVisible.value = false
          // 打开加载动画
          auditLoading.value = true
          // 重新获取当前分配情况统计列表
          api.getDistributeAudit(year.value)
              .then(r => {
                distributeAuditList.value = r.data.data
                // 关闭加载动画
                auditLoading.value = false
              })
          // 重新获取当前页
          studentGetNowPage()
          // 清空级联选择器数组
          updateCascaderPath.value = []
        }
      })
}
// 函数：获取教官当前页的数据
const userGetNowPage = () => {
  // 打开加载动画
  userLoading.value = true
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  // 判断条件
  let result = regax.test(userSearchData.value.name) ||
      regax.test(userSearchData.value.sexual) ||
      regax.test(userSearchData.value.post);
  if (result) {
    searchUserInfoListByCondition()
  } else {
    searchUserInfoByPage(userCurrentPage.value)
  }
  // 关闭加载动画
  userLoading.value = false
}
// 函数：清空教官查询条件
const userClearCondition = () => {
  // 清空查询使用的对象
  userSearchData.value.pageNumber = 1
  userSearchData.value.pageSize = 10
  userSearchData.value.name = null
  userSearchData.value.sexual = null
  userSearchData.value.post = null
  userSearchData.value.department = null
  // 重新查询一次完整列表
  userGetNowPage()
  ElMessage.warning("已重置查询")
}
// 函数：查询所有用户列表
const searchUserInfoByPage = value => {
  api.getUserInfoByPage(value)
      .then(r => {
        basicUserInfoList.value = r.data.data
      })
}
// 函数：按条件查询用户列表
const searchUserInfoListByCondition = () => {
  // 传入当前页码
  userSearchData.value.pageNumber = userCurrentPage.value
  // 执行
  api.searchUserInfoListByCondition(userSearchData.value)
      .then(r => {
        if (r.data.code == 200) {
          basicUserInfoList.value = r.data.data
        }
      })
}
// 函数：为用户分配连队
const userCommitDistribute = userId => {
  // 请求使用的对象
  let distributeDTO = ref({
    userId: null,
    newDeptId: null
  })
  // 拼装请求对象
  distributeDTO.value.userId = userId
  distributeDTO.value.newDeptId = updateCascaderPath.value[updateCascaderPath.value.length - 1]
  // 更新数据
  api.updateUserDeptByCondition(distributeDTO.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          distributeDialogVisible.value = false
          // 打开加载动画
          auditLoading.value = true
          // 重新获取当前分配情况统计列表
          api.getDistributeAudit(year.value)
              .then(r => {
                distributeAuditList.value = r.data.data
                // 关闭加载动画
                auditLoading.value = false
              })
          // 重新获取当前页
          userGetNowPage()
          // 清空级联选择器数组
          updateCascaderPath.value = []
        }
      })
}
// 函数：教官列表显示格式化
const userListFormat = value => {
  return value.replaceAll(",", "\n")
}
// 初始化
onMounted(() => {
  // 装填有效的学年信息
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
    ElMessage.error("必须选择学年数据源")
  } else {
    // 显示功能区（检索展示相关）
    componentVisible.value = true
    // 打开加载动画
    studentLoading.value = true
    userLoading.value = true
    auditLoading.value = true
    // 学生分配相关
    // 获得数据源列表第一页
    api.searchFullListByPage(1, year.value)
        .then(r => {
          if (r.data.code == 200) {
            ElMessage.success("数据源获取成功")
            basicSourceList.value = r.data.data
            // 关闭学生信息加载动画
            studentLoading.value = false
          }
        })
    // 获取当前数据源所有有效学院
    api.getAcademyList(year.value)
        .then(r => {
          academy.value = r.data.data
        })
    // 把学年信息放到查询对象里
    studentSearchData.value.year = year.value

    // 教官分配相关
    // 获取教官列表第一页
    api.getUserInfoByPage(1)
        .then(r => {
          basicUserInfoList.value = r.data.data
          // 关闭教官信息加载动画
          userLoading.value = false
        })

    // 指标相关
    // 装填当前指标
    api.getDistributeAudit(year.value)
        .then(r => {
          distributeAuditList.value = r.data.data
          // 关闭指标简报加载动画
          auditLoading.value = false
        })
  }
}
</script>

<template>
  <h1>人员分配管理</h1>
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
  <div v-show="componentVisible">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <el-tabs v-model="tabsVisible" type="card" stretch>
            <el-tab-pane label="学生分配" name="student">
              <div class="space">
                <el-tag>检索/操作</el-tag>
              </div>
              <el-row class="space" :gutter="5">
                <el-col :span="12">
                  <el-select style="width: 100%" placeholder="学院..." v-model="studentSearchData.academy"
                             @change="searchClassByAcademy">
                    <el-option v-for="item in academy" :key="item" :label="item" :value="item"/>
                  </el-select>
                </el-col>
                <el-col :span="12">
                  <el-select style="width: 100%" placeholder="班级..." clearable
                             v-model="studentSearchData.clazz">
                    <el-option v-for="item in clazz" :key="item" :label="item" :value="item"/>
                  </el-select>
                </el-col>
              </el-row>
              <el-row class="space" :gutter="5">
                <el-col :span="12">
                  <el-input style="width: 100%" placeholder="姓名..." clearable
                            v-model="studentSearchData.name"/>
                </el-col>
                <el-col :span="12">
                  <el-input style="width: 100%" placeholder="学号..." clearable
                            v-model="studentSearchData.id"/>
                </el-col>
              </el-row>
              <el-row class="space" :gutter="5">
                <el-col :span="12">
                  <el-select style="width: 100%" placeholder="性别..." clearable
                             v-model="studentSearchData.sexual">
                    <el-option key="1" label="男" value="男"/>
                    <el-option key="1" label="女" value="女"/>
                  </el-select>
                </el-col>
                <el-col :span="12">
                  <el-cascader placeholder="所在连队..." style="width: 100%" v-model="studentCascaderPath"
                               :options="treeDeptList"
                               :props="cascaderConfig"
                               clearable/>
                </el-col>
              </el-row>
              <el-row class="space" :gutter="5">
                <el-col :span="4">
                  <el-space size="5">
                    <el-button type="primary" plain @click="searchStudentByCondition">查询</el-button>
                    <el-button type="warning" plain @click="studentClearCondition">清空查询</el-button>
                  </el-space>
                </el-col>
              </el-row>
              <div class="space">
                <el-tag>数据列表</el-tag>
              </div>
              <el-table v-loading="studentLoading" :data="basicSourceList.records" height="420" stripe>
                <el-table-column prop="academy" label="学院" min-width="150"/>
                <el-table-column prop="clazz" label="班级" min-width="100"/>
                <el-table-column prop="id" label="学号" min-width="100"/>
                <el-table-column prop="name" label="姓名" min-width="100"/>
                <el-table-column prop="sexual" label="性别" min-width="60"/>
                <el-table-column prop="deptName" label="当前连队" min-width="150"/>
                <el-table-column min-width="100" fixed="right">
                  <template #header>
                    <el-button type="success" plain @click="studentDistributeBefore">批量分配</el-button>
                  </template>
                  <template #default="scope">
                    <el-popover placement="left" trigger="click">
                      <el-cascader placeholder="所在连队..." v-model="updateCascaderPath"
                                   :options="treeDeptList"
                                   :props="cascaderConfig"
                                   @change="studentCommitDistributeAlone(scope.row.id)"
                                   clearable/>
                      <template #reference>
                        <el-button plain type="primary">单独分配</el-button>
                      </template>
                    </el-popover>
                  </template>
                </el-table-column>
              </el-table>
              <!--  分页区-->
              <el-pagination background layout="total, ->, prev, pager, next, jumper"
                             @current-change="studentGetNowPage()"
                             :total="basicSourceList.totalRow"
                             v-model:current-page="studentCurrentPage"
                             :page-count="basicSourceList.totalPage"/>
            </el-tab-pane>

            <el-tab-pane label="教官分配" name="user">
              <div class="space">
                <el-tag>检索/操作</el-tag>
              </div>
              <el-row class="space" :gutter="5">
                <el-col :span="12">
                  <el-input style="width: 100%" placeholder="姓名..." clearable
                            v-model="userSearchData.name"/>
                </el-col>
                <el-col :span="12">
                  <el-input style="width: 100%" placeholder="岗位及其他..." clearable
                            v-model="userSearchData.post"/>
                </el-col>
              </el-row>
              <el-row class="space" :gutter="5">
                <el-col :span="12">
                  <el-select style="width: 100%" placeholder="性别..." clearable
                             v-model="userSearchData.sexual">
                    <el-option key="1" label="男" value="男"/>
                    <el-option key="1" label="女" value="女"/>
                  </el-select>
                </el-col>
              </el-row>
              <el-row class="space" :gutter="5">
                <el-col :span="4">
                  <el-space size="5">
                    <el-button type="primary" plain @click="searchUserInfoListByCondition">查询</el-button>
                    <el-button type="warning" plain @click="userClearCondition">清空查询</el-button>
                  </el-space>
                </el-col>
              </el-row>
              <div class="space">
                <el-tag>数据列表</el-tag>
              </div>
              <el-table v-loading="userLoading" :data="basicUserInfoList.records" stripe height="460">
                <el-table-column prop="name" label="姓名" min-width="150"/>
                <el-table-column prop="sexual" label="性别" min-width="60"/>
                <el-table-column prop="post" label="岗位及其他" min-width="150"/>
                <el-table-column prop="deptName" label="负责连队" min-width="100"/>
                <el-table-column label="变更连队" min-width="100" fixed="right">
                  <template #default="scope">
                    <el-popover placement="left" trigger="click">
                      <el-cascader placeholder="负责连队..." v-model="updateCascaderPath"
                                   :options="treeDeptList"
                                   :props="cascaderConfig"
                                   @change="userCommitDistribute(scope.row.userId)"
                                   clearable/>
                      <template #reference>
                        <el-button plain type="primary">变更连队</el-button>
                      </template>
                    </el-popover>
                  </template>
                </el-table-column>
              </el-table>
              <!--  分页区-->
              <el-pagination background layout="total, ->, prev, pager, next, jumper"
                             @current-change="userGetNowPage()"
                             :total="basicUserInfoList.totalRow"
                             v-model:current-page="userCurrentPage"
                             :page-count="basicUserInfoList.totalPage"/>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div style="padding: 10px 0px 10px">
            <el-tag>分配情况简报</el-tag>
          </div>
          <el-table v-loading="auditLoading" :data="distributeAuditList" stripe height="700" show-summary>
            <el-table-column sortable prop="deptName" label="连队名"/>
            <el-table-column sortable prop="studentTotal" label="学生总数"/>
            <el-table-column sortable prop="userList" label="教官">
              <template #default="scope">
                <span style="white-space: pre-wrap;">
                  {{ userListFormat(scope.row.userList) }}
                </span>
              </template>
            </el-table-column>
            <el-table-column sortable prop="userTotal" label="教官数量"/>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <!--学生分配对话框-->
  <el-dialog v-model="distributeDialogVisible" width="50%">
    <template #title>
      <h1>学生分配详情</h1>
    </template>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-table :data="basicSourceList.records" stripe>
          <el-table-column prop="clazz" label="班级"/>
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="deptName" label="当前连队"/>
        </el-table>
      </el-col>
      <el-col :span="12">
        <el-row class="space">
          <el-col :span="24">
            <el-text>当前条件下，共有</el-text>
            <el-text type="danger">{{ basicSourceList.totalRow }}</el-text>
            <el-text>名学生等待分配。请确认后再操作。</el-text>
          </el-col>
        </el-row>
        <el-row class="space">
          <el-col :span="24">
            <el-cascader placeholder="所在连队..." style="width: 100%" v-model="updateCascaderPath"
                         :options="treeDeptList" size="large"
                         :props="cascaderConfig"
                         clearable/>
          </el-col>
        </el-row>
        <el-row class="space">
          <el-col :span="24">
            <el-button style="width: 100%" plain type="success" size="large"
                       @click="studentCommitDistributeBatch">
              提交
            </el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>