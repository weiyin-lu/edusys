<script setup>
import {useStore} from "vuex";
import {inject, onMounted, ref} from "vue";
import http from "../axios/axios.js";
import {ElMessage} from "element-plus";
import {UploadFilled} from "@element-plus/icons-vue";
import session from "../store/session.js";
// 全局函数
const api = inject("$api")
const store = useStore()
// 数据列表：账户的角色信息
const currentRoleList = ref([])
// 请求数据：个人信息
const infoData = ref({
  name: null,
  phone: null,
  sexual: null
})
// 请求数据：修改密码
const rePwdData = ref({
  username: null,
  oldPwd: null,
  newPwd: null,
  repeat: null,
})
// 标识：维护个人信息对话框
const infoDialogVisible = ref(false)
// 标识：修改密码对话框
const rePwdDialogVisible = ref(false)
// 标识：头像上传对话框
const uploadDialogVisible = ref(false)
// 函数：头像上传成功后 回调操作
const uploadAfter = (response) => {
  if (response.code == 200) {
    store.dispatch('setInfoAction', response.data)
    ElMessage.success(response.msg);
    uploadDialogVisible.value = false;
  } else {
    ElMessage.error(response.msg + ":" + response.cause)
  }
}
// 函数：个人信息修改 数据装填
const setInfoBefore = () => {
  infoDialogVisible.value = true
  infoData.value.name = store.state.info.name
  infoData.value.phone = store.state.info.phone
  infoData.value.sexual = store.state.info.sexual
}
// 函数：修改个人信息
const setInfo = () => {
  api.setInfoByUserId(infoData.value)
      .then(r => {
        if (r.data.code == 200) {
          // 把修改后的信息更新到store中
          store.dispatch('setInfoAction', r.data.data)
          ElMessage.success(r.data.msg)
          infoDialogVisible.value = false
        }
      })
}
// 函数：修改密码 数据装填
const rePwdBefore = () => {
  rePwdDialogVisible.value = true
  // 装填账号
  rePwdData.value.username = store.state.token.loginId
}
// 函数：修改密码
const rePassword = () => {
  if (rePwdData.value.newPwd == rePwdData.value.repeat) {
    api.rePassword(rePwdData.value)
        .then(r => {
          if (r.data.code == 200) {
            ElMessage.success(r.data.msg)
            // 重置请求用对象
            rePwdData.value = {
              username: null,
              oldPwd: null,
              newPwd: null,
              repeat: null,
            }
            //关闭对话框
            rePwdDialogVisible.value = false
          } else {
            ElMessage.error(r.data.msg)
          }
        })
  } else {
    ElMessage.warning("两次密码不一致，请重新输入")
  }
}
// 函数：空值过滤格式化
const notNullFormat = value => {
  if (value == null || value == "") {
    return "无"
  } else {
    return value
  }
}
// 函数：角色信息格式化
const roleFormat = value => {
  for (let item of currentRoleList.value) {
    if (item.roleId == value) {
      return item.roleName
    }
  }
}
// 初始化
onMounted(() => {
  api.getRoleListByUserId(store.state.token.loginId)
      .then(r => {
        if (r.data.code == 200) {
          currentRoleList.value = r.data.data;

        }
      })
})
</script>

<template>
  <h1>个人信息</h1>
  <el-space direction="vertical">
    <el-avatar :size="100" :src="http.defaults.baseURL + store.state.info.image"/>
    <el-button plain @click="uploadDialogVisible = true">修改头像</el-button>
  </el-space>
  <el-descriptions border direction="vertical" :column="2">
    <template #extra>
      <el-space>
        <el-button plain @click="rePwdBefore">修改密码</el-button>
        <el-button plain @click="setInfoBefore()">维护个人信息</el-button>
      </el-space>
    </template>
    <el-descriptions-item label="账号">{{ store.state.info.userId }}</el-descriptions-item>
    <el-descriptions-item label="姓名">{{ store.state.info.name }}</el-descriptions-item>
    <el-descriptions-item label="联系方式">{{ notNullFormat(store.state.info.phone) }}</el-descriptions-item>
    <el-descriptions-item label="性别">{{ notNullFormat(store.state.info.sexual) }}</el-descriptions-item>
    <el-descriptions-item label="所属组织">{{
        notNullFormat(store.state.info.deptName)
      }}
    </el-descriptions-item>
    <el-descriptions-item label="岗位及其他信息">{{
        notNullFormat(store.state.info.post)
      }}
    </el-descriptions-item>
    <el-descriptions-item label="角色">
      <span v-for="item in store.state.role" style="padding: 0px 5px 0px">
        <el-tag>{{ roleFormat(item) }}</el-tag>
      </span>
    </el-descriptions-item>
  </el-descriptions>
  <!--  修改密码对话框-->
  <el-dialog v-model="rePwdDialogVisible" width="500">
    <template #title>
      <h1>修改密码</h1>
    </template>
    <el-row>
      <el-col class="space">
        <el-input type="password" style="width: 300px" size="large" placeholder="原密码"
                  v-model="rePwdData.oldPwd"/>
      </el-col>
      <el-col class="space">
        <el-input type="password" style="width: 300px" placeholder="新密码"
                  v-model="rePwdData.newPwd"/>
      </el-col>
      <el-col class="space">
        <el-input type="password" style="width: 300px" placeholder="重复新密码"
                  v-model="rePwdData.repeat"/>
      </el-col>
      <el-col class="space">
        <el-button type="success" style="width: 300px" plain @click="rePassword">修改</el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  维护个人信息对话框-->
  <el-dialog v-model="infoDialogVisible" width="500">
    <template #title>
      <h1>维护个人信息</h1>
    </template>
    <el-row>
      <el-col class="space">
        <el-input style="width: 300px" size="large" placeholder="姓名"
                  v-model="infoData.name"/>
      </el-col>
      <el-col class="space">
        <el-input style="width: 300px" size="large" placeholder="联系电话" v-model="infoData.phone"/>
      </el-col>
      <el-col class="space">
        <el-input style="width: 300px" size="large" placeholder="性别" v-model="infoData.sexual"/>
      </el-col>
      <el-col class="space">
        <el-button type="success" style="width: 300px" plain @click="setInfo">修改</el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  头像上传-->
  <el-dialog v-model="uploadDialogVisible">
    <template #title>
      <h1>修改头像</h1>
    </template>
    <el-upload drag :action="http.defaults.baseURL + '/api/manages/users/setHead'" method="PUT"
               :headers="{edusys:session.get('token').tokenValue}"
               :show-file-list="false" :on-success="uploadAfter">
      <el-icon class="el-icon--upload">
        <upload-filled/>
      </el-icon>
      <div class="el-upload__text">
        拖曳上传或<em>点击选择文件</em>
      </div>
      <template #tip>
        <div class="el-upload__tip">
          jpg/jpeg格式，小于10M
        </div>
      </template>
    </el-upload>
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
  text-align: center;
}
</style>