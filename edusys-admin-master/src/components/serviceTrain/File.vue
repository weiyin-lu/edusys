<script setup>
import {onMounted, ref} from "vue";
import http from "../../axios/axios.js";
import session from "../../store/session.js";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";

// 全局函数
const store = useStore()

// 数据列表：指导文件列表
const fileList = ref([
  {
    tag: 'JXDG',
    name: '教学大纲',
    url: http.defaults.baseURL + '/sources/document/JXDG.pdf'
  },
  {
    tag: 'PBBF',
    name: '评比办法',
    url: http.defaults.baseURL + '/sources/document/PBBF.pdf'
  },
  {
    tag: 'SSFA',
    name: '实施方案',
    url: http.defaults.baseURL + '/sources/document/SSFA.pdf'
  },
  {
    tag: 'YJYA',
    name: '应急预案',
    url: http.defaults.baseURL + '/sources/document/YJYA.pdf'
  }
])
// 请求数据：待上传文件的标签
const fileName = ref('')
// 标识：tab页选项卡
const tabsVisible = ref('JXDG')
// 标识：文件上传对话框
const uploadDialogVisible = ref(false)
// 标识：文件上传区
const uploadBoxVisible = ref(false)
// 函数：文件上传前变更
const uploadBefore = () => {
  uploadBoxVisible.value = true
}
// 函数：文件上传后
const uploadAfter = (response) => {
  ElMessage.info("上传中...")
  // 响应
  if (response.code == 200) {
    ElMessage.success(response.msg);
  } else {
    ElMessage.error(response.msg + ":" + response.cause)
  }
  uploadDialogVisible.value = false
  uploadBoxVisible.value = false
  // 每次上传后，为iframe组件传入一个无意义的参数，使iframe强制刷新
  var dom = document.getElementById("frame")
  dom.src = dom.src + "?param=" + new Date().getTime()
}
onMounted(() => {
  // 每次更新页面，为iframe组件传入一个无意义的参数，使iframe强制刷新
  var dom = document.getElementById("frame")
  dom.src = dom.src + "?param=" + new Date().getTime()
})
</script>

<template>
  <h1>指导文件公示</h1>
  <div class="space">
    <el-button plain type="success" @click="uploadDialogVisible = true">更新文件</el-button>
  </div>
  <el-card>
    <el-tabs v-model="tabsVisible" type="card" stretch>
      <el-tab-pane v-for="item in fileList" :label="item.name" :name="item.tag">
        <iframe id="frame" class="file" :src="item.url"></iframe>
      </el-tab-pane>
    </el-tabs>
  </el-card>

  <el-dialog v-model="uploadDialogVisible" width="50%">
    <template #title>
      <h1>更新文件</h1>
    </template>
    <el-space>
      <el-select style="width: 300px" placeholder="选择文件..." clearable v-model="fileName"
                 @change="uploadBefore">
        <el-option
            v-for="item in fileList"
            :key="item.tag"
            :label="item.name"
            :value="item.tag"/>
      </el-select>
    </el-space>
    <div v-show="uploadBoxVisible" style="padding: 10px 0px 10px">
      <el-upload drag :action="http.defaults.baseURL + '/api/file/fileLoad/' + fileName"
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
            pdf格式，不限制大小
          </div>
        </template>
      </el-upload>
    </div>
  </el-dialog>
</template>

<style scoped>
.file {
  width: 100%;
  height: 630px;
}
.space {
  padding: 0 0 10px;
}
</style>