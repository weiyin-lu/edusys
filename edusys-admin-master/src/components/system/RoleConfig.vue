<script setup>
import {inject, onMounted, ref} from "vue";
import {Delete, Edit} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
// 全局函数
const api = inject("$api")
// 数据列表：角色信息
const basicRoleList = ref({
  pageNumber: null,
  pageSize: null,
  records: [],
  totalPage: null,
  totalRow: null
})
// 数据列表：所有权限信息
const basicPermissionList = ref([])
// 数据列表：所有菜单信息
const basicMenuList = ref([])
// 数据列表：某角色当前拥有的权限
const permissionRelevanceList = ref([])
// 数据列表：某角色当前拥有的菜单
const menuRelevanceList = ref([])
// 请求数据：查询角色信息
const searchData = ref({
  roleId: null,
  roleName: null,
  page: 1
})
// 请求数据：修改角色信息
const roleEditData = ref({
  roleId: null,
  roleName: null
})
// 请求数据：添加角色信息
const roleAddData = ref({
  roleId: null,
  roleName: null
})
// 请求数据：配置角色和权限/菜单关系
const relevanceData = ref({
  roleId: null,
  list: []
})
// 标识：当前页码
const currentPage = ref(1)
// 标识：修改对话框显示
const editDialogVisible = ref(false)
// 标识：添加对话框显示
const addDialogVisible = ref(false)
// 标识：配置权限组对话框显示
const permissionRelevanceDialogVisible = ref(false)
// 标识：配置菜单对话框显示
const menuRelevanceDialogVisible = ref(false)
// 函数：数据模糊匹配查询角色
const searchRoleList = () => {
  api.getRoleListByCondition(searchData.value)
      .then(r => {
        basicRoleList.value = r.data.data
      })
}
// 函数：配置权限组-数据装填
const permissionRelevanceEditBefore = value => {
  permissionRelevanceDialogVisible.value = true
  relevanceData.value.roleId = value;
  api.getPermissionListByRoleId(value)
      .then(r => {
        if (r.data.code == 200) {
          permissionRelevanceList.value = r.data.data
        }
      })
  api.getPermissionListAll()
      .then((r => {
        if (r.data.code == 200) {
          basicPermissionList.value = r.data.data
        }
      }))
}
// 函数：配置权限组-添加权限
const addPermissionRelevance = () => {
  api.addPermissionForRole(relevanceData.value)
      .then(r => {
        if (r) {
          api.getPermissionListByRoleId(relevanceData.value.roleId)
              .then(r => {
                if (r) {
                  permissionRelevanceList.value = r.data.data
                }
              })
          relevanceData.value.list = []
          ElMessage.success("添加成功")
          permissionRelevanceDialogVisible.value = false;
        }
      })
}
// 函数：配置权限组-移除权限
const removePermissionRelevance = (dto) => {
  api.removePermissionForRole(dto)
      .then(r => {
        if (r.data.code == 200) {
          api.getPermissionListByRoleId(relevanceData.value.roleId)
              .then(r => {
                if (r) {
                  permissionRelevanceList.value = r.data.data
                }
              })
          ElMessage.success("移除成功")
        }
      })
}
// 函数：配置菜单-数据装填
const menuRelevanceEditBefore = value => {
  menuRelevanceDialogVisible.value = true
  relevanceData.value.roleId = value;
  api.getMenuListByRoleId(value)
      .then(r => {
        if (r.data.code == 200) {
          menuRelevanceList.value = r.data.data
        }
      })
  api.getMenuListAll()
      .then((r => {
        if (r.data.code == 200) {
          basicMenuList.value = r.data.data
        }
      }))
}
// 函数: 配置菜单-添加菜单
const addMenuRelevance = () => {
  api.addMenuForRole(relevanceData.value)
      .then(r => {
        if (r.data.code == 200) {
          api.getMenuListByRoleId(relevanceData.value.roleId)
              .then(r => {
                if (r) {
                  permissionRelevanceList.value = r.data.data
                }
              })
          relevanceData.value.list = []
          ElMessage.success("添加成功")
          menuRelevanceDialogVisible.value = false;
        }
      })
}
// 函数：配置菜单-移除菜单
const removeMenuRelevance = (dto) => {
  api.removeMenuForRole(dto)
      .then(r => {
        if (r) {
          api.getMenuListByRoleId(relevanceData.value.roleId)
              .then(r => {
                if (r) {
                  permissionRelevanceList.value = r.data.data
                }
              })
          ElMessage.success("移除成功")
          menuRelevanceDialogVisible.value = false;
        }
      })
}
// 函数：修改角色信息 数据装填
const setRoleBefore = value => {
  editDialogVisible.value = true
  roleEditData.value.roleId = value.roleId
  roleEditData.value.roleName = value.roleName
}
// 函数：修改角色信息
const setRole = () => {
  api.setRoleByRoleId(roleEditData.value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          // 关闭菜单、清空请求数据、重新获取当前列表
          editDialogVisible.value = false
          Object.keys(roleEditData.value).forEach((i) => roleEditData.value[i] = null)
          getNowPage()
        }
      })
}
// 函数：添加角色
const addRole = () => {
  api.addRole(roleAddData.value)
      .then(r => {
        if (r) {
          ElMessage.success(r.data.msg)
          addDialogVisible.value = false
          getNowPage()
        }
      })
}
// 函数：删除角色信息
const removeRole = (value) => {
  api.removeRoleByRoleId(value)
      .then(r => {
        if (r.data.code == 200) {
          ElMessage.success(r.data.msg)
          getNowPage()
        }
      })
}
// 函数：获取当前页的数据
const getNowPage = () => {
  // 如果查询输入框有值，就通过模糊匹配函数获取数据；如果没有，就通过正常函数查询
  if ((searchData.value.roleId == null || searchData.value.roleId == "") &&
      (searchData.value.roleName == null || searchData.value.roleName == "")) {
    api.getRoleListByPage(currentPage.value)
        .then(r => {
          basicRoleList.value = r.data.data
        })
  } else {
    searchRoleList()
  }
}
// 初始化
onMounted(() => {
  // 获取角色信息数据列表第一页
  api.getRoleListByPage(1)
      .then(r => {
        basicRoleList.value = r.data.data
      })
})
</script>

<template>
  <h1>角色信息配置</h1>
  <!--  检索/操作区域-->
  <div class="space">
    <el-tag>检索/操作</el-tag>
  </div>
  <el-row class="space" :gutter="10">
    <el-col :span="5">
      <el-input placeholder="角色编码..." v-model="searchData.roleId"
                @input="searchRoleList()"/>
    </el-col>
    <el-col :span="5">
      <el-input placeholder="角色含义..." v-model="searchData.roleName"
                @input="searchRoleList()"/>
    </el-col>
    <el-col :span="2">
      <el-button type="primary" plain @click="searchRoleList()">查询</el-button>
    </el-col>
    <el-col :span="2">
      <el-button type="success" plain @click="addDialogVisible=true">添加角色</el-button>
    </el-col>
  </el-row>
  <!--  数据列表区域-->
  <div class="space">
    <el-tag>数据列表</el-tag>
  </div>
  <el-table :data="basicRoleList.records" stripe height="75%">
    <el-table-column type="index" label="序号" width="100"/>
    <el-table-column prop="roleId" sortable label="角色编码"/>
    <el-table-column prop="roleName" sortable label="角色含义"/>
    <el-table-column label="操作" width="300px">
      <template #default="scope">
        <el-button type="primary" plain @click="permissionRelevanceEditBefore(scope.row.roleId)">配置权限组
        </el-button>
        <el-button type="primary" plain @click="menuRelevanceEditBefore(scope.row.roleId)">配置菜单
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
          <el-button type="warning" plain circle @click="setRoleBefore(scope.row)">
            <el-icon color="#222222">
              <Edit/>
            </el-icon>
          </el-button>
          <el-popconfirm title="删除角色可能会导致持有该角色的用户失去权限，确定删除？"
                         @confirm="removeRole(scope.row.roleId)"
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
                 :total="basicRoleList.totalRow"
                 v-model:current-page="currentPage"
                 :page-count="basicRoleList.totalPage"/>
  <!--  修改对话框-->
  <el-dialog v-model="editDialogVisible" width="400px">
    <template #title>
      <h1>修改角色信息</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" v-model="roleEditData.roleId" disabled/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="角色含义（名称）"
                  v-model="roleEditData.roleName"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="setRole()">
          提交修改
        </el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  添加对话框-->
  <el-dialog v-model="addDialogVisible" width="400px">
    <template #title>
      <h1>添加角色</h1>
    </template>
    <el-row>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="角色ID" v-model="roleAddData.roleId"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-input style="width: 300px" size="large" placeholder="角色含义（名称）"
                  v-model="roleAddData.roleName"/>
      </el-col>
      <el-col style="text-align: center" class="space">
        <el-button style="width: 300px" type="success" size="large" plain @click="addRole()">添加</el-button>
      </el-col>
    </el-row>
  </el-dialog>
  <!--  菜单配置对话框-->
  <el-dialog v-model="menuRelevanceDialogVisible" width="50%">
    <template #title>
      <h1>菜单清单</h1>
    </template>
    <el-collapse>
      <el-collapse-item>
        <template #title>
          <el-text type="primary">点击展开添加选项</el-text>
        </template>
        <el-space>
          <el-select v-model="relevanceData.list" clearable multiple filterable style="width: 500px">
            <el-option v-for="item in basicMenuList" :key="item.menuId"
                       :label="item.menuId" :value="item.menuId">
              <el-text style="float: left">{{ item.description }}</el-text>
              <el-text type="info" style="float: right">{{ item.menuId }}</el-text>
            </el-option>
          </el-select>
          <el-button type="success" plain @click="addMenuRelevance()">添加</el-button>
        </el-space>
      </el-collapse-item>
    </el-collapse>
    <el-table :data="menuRelevanceList" height="400">
      <el-table-column label="菜单ID" prop="menuId"/>
      <el-table-column label="菜单路径" prop="url"/>
      <el-table-column label="菜单描述" prop="description"/>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="移除菜单后持有该角色的用户将不能操作该菜单，确定移除？"
                         @confirm="removeMenuRelevance(scope.row)"
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
  </el-dialog>
  <!--  权限配置对话框-->
  <el-dialog v-model="permissionRelevanceDialogVisible" width="50%">
    <template #title>
      <h1>权限清单</h1>
    </template>
    <el-collapse>
      <el-collapse-item>
        <template #title>
          <el-text type="primary">点击展开添加选项</el-text>
        </template>
        <el-space>
          <el-select v-model="relevanceData.list" clearable multiple filterable style="width: 500px">
            <el-option v-for="item in basicPermissionList" :key="item.permissionId"
                       :label="item.permissionId" :value="item.permissionId">
              <el-text style="float: left">{{ item.permissionName }}</el-text>
              <el-text type="info" style="float: right">{{ item.permissionId }}</el-text>
            </el-option>
          </el-select>
          <el-button type="success" plain @click="addPermissionRelevance()">添加</el-button>
        </el-space>
      </el-collapse-item>
    </el-collapse>
    <el-table :data="permissionRelevanceList" height="400">
      <el-table-column label="权限ID" prop="permissionId"/>
      <el-table-column label="权限含义" prop="permissionName"/>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="移除权限后持有该角色的用户将一同失去权限，确定移除？"
                         @confirm="removePermissionRelevance(scope.row)"
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
  </el-dialog>
</template>

<style scoped>
.space {
  padding: 0 0 10px;
}
</style>