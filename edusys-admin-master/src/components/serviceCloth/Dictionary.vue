<script setup>
import {inject, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")

// 数据列表：字典项列表
const dicIdList = ref([
  {dicId: 'cloth', description: '外装转换表'}
  , {dicId: 'shoes', description: '鞋码转换表'}
  , {dicId: 'shirt', description: '衬衫转换表'}
])
// 数据列表：字典内容列表
const basicDicList = ref({
  cloth: null,
  shoes: null,
  shirt: null
})
const changeData = ref({})
// 标识：添加/修改对话框
const updateDialogVisible = ref(false)
// 标识：添加/修改按钮
const buttonVisible = ref(false)
// 标识：主要区域加载动画
const mainLoading = ref(false)
// 函数：删除一条转换记录
const removeDic = dto => {
  api.removeDicById(dto.id)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          api.searchDicList(dto.dicId)
              .then(r => {
                basicDicList.value[dto.dicId] = r.data.data
              })
        }
      })
}
// 函数：添加一条转换记录
const addDic = () => {
  api.addDic(changeData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          api.searchDicList(changeData.value.dicId)
              .then(r => {
                basicDicList.value[changeData.value.dicId] = r.data.data
              })
          updateDialogVisible.value = false
        }
      })
}
// 函数：修改一条转换记录
const updateDic = () => {
  api.updateDicById(changeData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          api.searchDicList(changeData.value.dicId)
              .then(r => {
                basicDicList.value[changeData.value.dicId] = r.data.data
              })
          updateDialogVisible.value = false
        }
      })
}
// 函数：开始添加流程
const updateBefore = dto => {
  updateDialogVisible.value = true
  buttonVisible.value = false
  changeData.value.id = dto.id
  changeData.value.dicId = dto.dicId
  changeData.value.description = dto.description
  changeData.value.rawValue = dto.rawValue
  changeData.value.parseValue = dto.parseValue
}
// 函数：开始修改流程
const addBefore = dto => {
  updateDialogVisible.value = true
  buttonVisible.value = true
  changeData.value.dicId = dto.dicId
  changeData.value.description = dto.description
}
// 初始化
onMounted(() => {
  // 打开加载动画
  mainLoading.value = true
  api.searchDicList("cloth")
      .then(r => {
        basicDicList.value.cloth = r.data.data
        // 关闭加载动画
        mainLoading.value = false
      })
  api.searchDicList("shoes")
      .then(r => {
        basicDicList.value.shoes = r.data.data
        // 关闭加载动画
        mainLoading.value = false
      })
  api.searchDicList("shirt")
      .then(r => {
        basicDicList.value.shirt = r.data.data
        // 关闭加载动画
        mainLoading.value = false
      })
})
</script>

<template>
  <h1>字典管理</h1>
  <el-row v-loading="mainLoading" :gutter="20">
    <el-col :span="8" v-for="dic in dicIdList">
      <el-card shadow="hover">
        <template #header>
          <div>
            <p class="title">{{ dic.description }}
              <el-tag>{{ dic.dicId }}</el-tag>
            </p>
          </div>
        </template>
        <el-table :data="basicDicList[dic.dicId]" stripe height="550">
          <el-table-column prop="rawValue" label="原始值" sortable table-layout="fixed"/>
          <el-table-column prop="parseValue" label="转换值" table-layout="fixed"/>
          <el-table-column label="操作" width="100px">
            <template #header="scope">
              <el-button type="success" plain circle @click="addBefore(dic)">
                <el-icon color="#222222">
                  <Plus/>
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-button type="primary" plain circle @click="updateBefore(scope.row)">
                <el-icon color="#222222">
                  <Edit/>
                </el-icon>
              </el-button>
              <el-popconfirm title="确定删除？"
                             @confirm="removeDic(scope.row)"
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
      </el-card>
    </el-col>
  </el-row>
  <!--  添加对话框-->
  <el-dialog v-model="updateDialogVisible" width="400px">
    <template #title>
      <div v-if="buttonVisible">
        添加转换参数
      </div>
      <div v-else>
        修改转换参数
      </div>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="字典ID" v-model="changeData.dicId" disabled/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="描述" v-model="changeData.description"
                  disabled/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="原始值" v-model="changeData.rawValue"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="转换值" v-model="changeData.parseValue"/>
      </el-col>
      <el-col v-if="buttonVisible" style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addDic()">添加</el-button>
      </el-col>
      <el-col v-else style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="updateDic()">修改
        </el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped>
.title {
  font-size: 1.3rem;
}

.space {
  padding: 0 0 10px;
}
</style>