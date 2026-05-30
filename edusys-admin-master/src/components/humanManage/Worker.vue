<script setup>
import {inject, onMounted, ref} from "vue";
import {ElLoading, ElMessage} from "element-plus";
import http from "../../axios/axios.js";
import session from "../../store/session.js";
// 全局函数
const api = inject("$api")
// 请求数据：查询用户信息
const searchData = ref({
  pageNumber: 1,
  pageSize: 10,
  userId: null,
  name: null,
  phone: null,
  department: null,
  post: null,
  sexual: null
})
// 请求数据：注册单个用户
const regData = ref({
  userId: null,
  name: null,
  phone: null,
  sexual: null,
  post: null
})
// 数据列表：部门信息（树形嵌套列表）
const treeDeptList = ref([])
// 数据列表：所有用户数据
const basicUserInfoList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：被选择用户信息
const selectUserInfo = ref({})
// 数据列表：所有角色信息
const basicRoleList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：被选择用户的角色列表
const currentRoleList = ref([])
// 标识：当前页码
const currentPage = ref(1)
// 标识：级联选择器配置
const cascaderConfig = ref({
  checkStrictly: true,
  value: "deptCode",
  label: "deptName",
  children: "childList"
})
// 标识：级联选择器选择的路径数组
const cascaderPath = ref([])
// 标识：批量注册对话框
const uploadDialogVisible = ref(false)
// 标识：角色配置对话框
const infoDialogVisible = ref(false)
// 标识：角色变更对话框中的当前页码
const dialogCurrentPage = ref(1)
// 标识：角色信息变更对话框
const roleDialogVisible = ref(false)
// 标识：创建单个用户对话框
const regDialogVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 标识：角色变更区域加载动画
const roleLoading = ref(false)
// 函数：获取当前页的数据
const getNowPage = () => {
  // 打开加载动画
  mainLoading.value = true
  //如果输入框不为空，调用含条件的查询；如果为空，直接进行查询
  let regax = /^.+$/ // 匹配所有内容
  // 判断条件
  let result = regax.test(searchData.value.userId) ||
      regax.test(searchData.value.name) ||
      regax.test(searchData.value.phone) ||
      regax.test(searchData.value.department) ||
      regax.test(searchData.value.sexual) ||
      regax.test(searchData.value.post);
  if (result) {
    searchUserInfoListByCondition()
  } else {
    searchUserInfoByPage(currentPage.value)
  }
  // 关闭加载动画
  mainLoading.value = false
}
// 函数：查询所有用户列表
const searchUserInfoByPage = value => {
  api.getUserInfoByPage(value)
      .then(r => {
        // 传入数据
        basicUserInfoList.value = r.data.data
      })
}
// 函数：按条件查询用户列表
const searchUserInfoListByCondition = () => {
  // 传入当前页码
  searchData.value.pageNumber = currentPage.value
  // 将级联选择器选择的最末级路径装填到请求数据中
  searchData.value.department = cascaderPath.value[cascaderPath.value.length - 1]
  // 执行
  api.searchUserInfoListByCondition(searchData.value)
      .then(r => {
        if (r.data.code == 200) {
          // 传入数据
          basicUserInfoList.value = r.data.data
        }
      })
}
// 函数：获取当前用户的角色列表
const searchRoleList = value => {
  api.getRoleListByUserId(value)
      .then(r => {
        if (r.data.code == 200) {
          currentRoleList.value = r.data.data;
        }
      })
}
// 函数：清空查询条件
const clearCondition = () => {
  // 清空查询使用的对象
  searchData.value.pageNumber = 1
  searchData.value.pageSize = 10
  searchData.value.name = null
  searchData.value.sexual = null
  searchData.value.userId = null
  searchData.value.post = null
  searchData.value.phone = null
  searchData.value.department = null
  cascaderPath.value = [] //这里要清空级联选择器传入的值
  // 重新查询一次完整列表
  getNowPage()
  ElMessage.warning("已重置查询")
}
// 函数：修改用户信息功能-数据装填
const updateInfoBefore = dto => {
  selectUserInfo.value = dto
  infoDialogVisible.value = true
}
// 函数：修改用户信息
const updateUserInfo = () => {
  // 更新
  api.updateUserInfoByUserId(selectUserInfo.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          infoDialogVisible.value = false
          getNowPage()
        }
      })

}
// 函数：角色变更功能-数据装填
const updateRoleBefore = dto => {
  // 打开加载动画
  roleLoading.value = true
  // 装填当前用户角色信息
  currentRoleList.value = dto.roleList
  // 打开对话框
  roleDialogVisible.value = true
  // 装填被选用户信息
  selectUserInfo.value = dto
  //装填所有角色列表
  api.getRoleListByPage(dialogCurrentPage.value)
      .then(r => {
        basicRoleList.value = r.data.data
        // 关闭加载动画
        roleLoading.value = false
      })
}
// 函数：获取角色当前页的数据
const dialogGetNowPage = () => {
  api.getRoleListByPage(dialogCurrentPage.value)
      .then(r => {
        basicRoleList.value = r.data.data
      })
}
// 函数：添加角色
const addRole = dto => {
  // 构建一个传输用的对象
  let requestParam = ref({
    userId: null,
    roleId: null
  })
  requestParam.value.userId = selectUserInfo.value.userId
  requestParam.value.roleId = dto.roleId
  api.addRoleForUser(requestParam.value)
      .then(r => {
        if (r.data.code == 200) {
          // 重新获取一次当前用户的角色列表
          searchRoleList(requestParam.value.userId)
          ElMessage.success(r.data.msg)
        }
      })

}
// 函数：移除角色
const removeRole = value => {
  let requestParam = ref({
    userId: null,
    roleId: null
  })
  requestParam.value.userId = selectUserInfo.value.userId
  requestParam.value.roleId = value
  api.removeRoleForUser(requestParam.value)
      .then(r => {
        if (r.data.code == 200) {
          // 重新获取一次当前用户的角色列表
          searchRoleList(requestParam.value.userId)
          ElMessage.success(r.data.msg)
        }
      })
}
// 函数：删除用户
const removeUser = value => {
  api.removeUser(value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          getNowPage()
        }
      })
}
// 函数：注册单个账号
const addUser = () => {
  api.registerAlone(regData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          regDialogVisible.value = false
          getNowPage()
        }
      })
}
// 函数：文件上传前操作
const uploadBefore = () => {
  uploadDialogVisible.value = true
}
// 函数：文件上传后操作
const uploadAfter = (response) => {
  // loading条
  const loading = ElLoading.service({
    lock: true,
    text: '解析中...',
  })
  if (response.code == 200) {
    ElMessage.success(response.msg);
  } else {
    ElMessage.error(response.msg + ":" + response.cause)
  }
  // 关闭对话框
  uploadDialogVisible.value = false
  // 重新执行一次数据列表的查询
  searchUserInfoByPage(1)
  loading.close()
}
// 初始化
onMounted(() => {
  // 打开加载动画
  mainLoading.value = true
  // 获取所有用户信息
  api.getUserInfoByPage(currentPage.value)
      .then(r => {
        // 传入数据
        basicUserInfoList.value = r.data.data
        // 关闭加载动画
        mainLoading.value = false
      })
  // 装填所有部门信息嵌套数据列表
  api.getDeptList('MAIN')
      .then(r => {
        treeDeptList.value = r.data.data
      })
})
</script>

<template>
  <h1>工作人员信息管理</h1>
  <!--  操作区-->
  <div class="space">
    <el-tag>检索/操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-input style="width: 100%" placeholder="账号..." v-model="searchData.userId" clearable/>
    </el-col>
    <el-col :span="5">
      <el-input style="width: 100%" placeholder="姓名..." v-model="searchData.name" clearable/>
    </el-col>
    <el-col :span="5">
      <el-input style="width: 100%" placeholder="联系电话..." v-model="searchData.phone" clearable/>
    </el-col>
  </el-row>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-cascader placeholder="所属组织..." style="width: 100%" v-model="cascaderPath"
                   :options="treeDeptList"
                   :props="cascaderConfig"
                   clearable/>
    </el-col>
    <el-col :span="5">
      <el-input style="width: 100%" placeholder="岗位及其他..." v-model="searchData.post" clearable/>
    </el-col>
    <el-col :span="5">
      <el-select style="width: 100%" placeholder="性别..." clearable v-model="searchData.sexual">
        <el-option key="1" label="男" value="男"/>
        <el-option key="1" label="女" value="女"/>
      </el-select>
    </el-col>
  </el-row>
  <el-row class="space" :gutter="10">
    <el-col :span="4">
      <el-space size="10">
        <el-button type="primary" plain @click="searchUserInfoListByCondition">查询</el-button>
        <el-button type="warning" plain @click="clearCondition">清空查询</el-button>
      </el-space>
    </el-col>
  </el-row>
  <!--  数据展示区-->
  <div class="space">
    <el-tag>数据列表</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="4">
      <el-space size="10">
        <el-button type="success" plain @click="uploadBefore">批量注册账号</el-button>
        <el-button type="primary" plain @click="regDialogVisible = true">注册单个账号</el-button>
      </el-space>
    </el-col>
  </el-row>
  <el-table v-loading="mainLoading" :data="basicUserInfoList.records" stripe height="460">
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="userId" label="账号" min-width="150"/>
    <el-table-column prop="name" label="姓名" min-width="100"/>
    <el-table-column prop="phone" label="联系电话" min-width="100"/>
    <el-table-column prop="sexual" label="性别" min-width="60"/>
    <el-table-column prop="deptName" label="所属组织" min-width="100"/>
    <el-table-column prop="post" label="岗位及其他" min-width="150"/>
    <el-table-column label="当前角色" min-width="400">
      <template #default="scope">
        <el-space v-if="scope.row.roleList.length > 0"
                  v-for="item in scope.row.roleList">
          <el-tag>{{ item.roleName }}</el-tag>
        </el-space>
        <el-tag type="danger" v-else>无角色</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="操作" min-width="180" fixed="right">
      <template #default="scope">
        <el-button type="primary" plain @click="updateRoleBefore(scope.row)">角色变更
        </el-button>
        <el-popover placement="left">
          <template #reference>
            <el-button plain circle>
              <el-icon color="#222222">
                <More/>
              </el-icon>
            </el-button>
          </template>
          <el-text>操作：</el-text>
          <el-button type="warning" plain circle @click="updateInfoBefore(scope.row)">
            <el-icon color="#222222">
              <Edit/>
            </el-icon>
          </el-button>
          <el-popconfirm title="删除账号后将永久无法恢复，确定删除？（真的很久！）"
                         @confirm="removeUser(scope.row.userId)"
                         hide-after="100">
            <template #reference>
              <el-button type="danger" plain circle>
                <el-icon color="#222222">
                  <Delete/>
                </el-icon>
              </el-button>
            </template>
          </el-popconfirm>
        </el-popover>
      </template>
    </el-table-column>
  </el-table>
  <!--  分页区-->
  <el-pagination background layout="total, ->, prev, pager, next, jumper" @current-change="getNowPage()"
                 :total="basicUserInfoList.totalRow"
                 v-model:current-page="currentPage"
                 :page-count="basicUserInfoList.totalPage"/>
  <!--  批量注册账号对话框-->
  <el-dialog v-model="uploadDialogVisible">
    <template #title>
      <h1>批量注册账号</h1>
    </template>
    <el-upload drag :action="http.defaults.baseURL + '/api/manages/users/registerBatch'" method="PUT"
               :headers="{edusys:session.get('token').tokenValue}"
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
          xls格式，小于10M。
          <a :href="http.defaults.baseURL + '/sources/template/工作人员账号注册示例文件.xlsx'">点击下载工作人员账号注册示例文件</a>
        </div>
      </template>
    </el-upload>
  </el-dialog>
  <!--  创建用户对话框-->
  <el-dialog v-model="regDialogVisible" width="500px">
    <template #title>
      <h1>修改用户信息</h1>
    </template>
    <el-text class="space">默认密码与账号相同</el-text>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="学号（账号）" v-model="regData.userId"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="姓名" v-model="regData.name"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-select style="width: 300px" size="large" placeholder="性别" clearable v-model="regData.sexual">
        <el-option key="1" label="男" value="男"/>
        <el-option key="1" label="女" value="女"/>
      </el-select>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="岗位及其他" v-model="regData.post"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="联系电话" v-model="regData.phone"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-button style="width: 300px" type="success" size="large" plain @click="addUser">注册</el-button>
    </el-col>
  </el-dialog>
  <!--  修改用户信息对话框-->
  <el-dialog v-model="infoDialogVisible" width="500px">
    <template #title>
      <h1>修改用户信息</h1>
    </template>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="姓名" v-model="selectUserInfo.name"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="联系电话" v-model="selectUserInfo.phone"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="性别" v-model="selectUserInfo.sexual"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-input style="width: 300px" size="large" placeholder="岗位及其他" v-model="selectUserInfo.post"/>
    </el-col>
    <el-col class="space" style="text-align: center">
      <el-button style="width: 300px" type="success" size="large" plain @click="updateUserInfo">确认
      </el-button>
    </el-col>
  </el-dialog>
  <!--  角色变更对话框-->
  <el-dialog v-model="roleDialogVisible" @close="getNowPage">
    <template #title>
      <h1>角色变更</h1>
    </template>
    <el-card shadow="hover">
      <h1>用户信息</h1>
      <table>
        <tr>
          <td>
            <el-text>当前用户：</el-text>
          </td>
          <td>
            <el-text>{{ selectUserInfo.name }}({{ selectUserInfo.userId }})</el-text>
          </td>
        </tr>
        <tr>
          <td>
            <el-text>当前持有权限：</el-text>
          </td>
          <td>
            <el-space v-if="currentRoleList.length > 0">
              <el-tag v-for="item in currentRoleList" closable @close="removeRole(item.roleId)">
                {{ item.roleName }}
              </el-tag>
            </el-space>
            <el-tag type="danger" v-else>无角色</el-tag>
          </td>
        </tr>
      </table>
    </el-card>
    <el-card shadow="hover">
      <h1>有效角色</h1>
      <el-table :data="basicRoleList.records" stripe height="550">
        <el-table-column type="index" label="序号" width="100"/>
        <el-table-column prop="roleId" label="角色编码"/>
        <el-table-column prop="roleName" label="角色含义"/>
        <el-table-column label="操作" width="300px">
          <template #default="scope">
            <el-button type="success" plain circle @click="addRole(scope.row)">
              <el-icon color="#222222">
                <Plus/>
              </el-icon>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, ->, prev, pager, next, jumper"
                     @current-change="dialogGetNowPage()"
                     :total="basicRoleList.totalRow"
                     v-model:current-page="dialogCurrentPage"
                     :page-count="basicRoleList.totalPage"/>
    </el-card>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>