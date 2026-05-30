<script setup>
import {onMounted, ref} from "vue";
import api from "../../axios/api.js";
import {layer} from "@layui/layui-vue";
import store from "../../store/store.js";
import {useRouter} from "vue-router";
// 全局函数
const router = useRouter()
// 初始化
onMounted(() => {
  // 组合学年
  formData.value.year = new Date().getFullYear().toString()
  // 变更进度条
  store.commit("commitStep", 33)
})
// 请求数据：校验表单
const formData = ref({
  year: "",
  name: "",
  cardId: ""
})
// 函数：提交校验
const submit = () => {
  if (/^\d{4}/.test(formData.value.year)) {
    //开始请求
    let promise = api.checkStudent(formData.value);
    promise.then(r => {
      if (r.data.code == 200) {
        store.commit("commitStep", 66)
        store.commit("commitStudentInfo", r.data.data)
        if (r.data.data.checkStatus == 0) {
          layer.msg("验证通过，请继续登记", {icon: 1, time: 1000})
          router.push("/user/form")
        } else {
          layer.msg("验证通过", {icon: 1, time: 1000})
          api.searchSourceInfoBeforeParse(r.data.data.id, formData.value.year)
              .then(r2 => {
                store.commit('commitParseInfo', r2.data.data)
                router.push("/user/complete")
              })
        }
      } else {
        layer.msg("信息无效，请检查学号和姓名是否正确", {icon: 2, time: 1000})
      }
    })
  } else {
    layer.msg("信息有误，请检查后重试", {icon: 2, time: 1000})
  }
}

</script>

<template>
  <lay-row>
    <lay-col>
      <lay-form pane :model="formData">
        <lay-form-item label="身份证号" prop="studentId">
          <lay-input v-model="formData.cardId"></lay-input>
        </lay-form-item>
        <lay-form-item label="姓名" prop="name">
          <lay-input v-model="formData.name"></lay-input>
        </lay-form-item>
        <lay-form-item style="text-align: center;">
          <lay-button style="width:100%" type="primary" @click="submit">提交</lay-button>
        </lay-form-item>
      </lay-form>
    </lay-col>
  </lay-row>
</template>

<style scoped>

</style>