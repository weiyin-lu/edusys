<script setup>
import {onMounted, ref} from "vue";
import store from "../../store/store.js";
import api from "../../axios/api.js";
import {layer} from "@layui/layui-vue";
import {useRouter} from "vue-router";

const router = useRouter()

onMounted(() => {
  store.commit("commitStep", 66)
  formData.value.year = new Date().getFullYear().toString()
  formData.value.id = store.state.studentInfo.id
  formData.value.name = store.state.studentInfo.name
})

const formData = ref({
  year: "",
  name: "",
  id: "",
  height: "",
  weight: "",
  shoeSize: "",
  phone: ""
})

const submit = () => {
  api.updateSourceInfoByIdAndName(formData.value)
      .then(r => {
        if (r.data.code == 200) {
          api.updateCheckStatusById(formData.value.year, formData.value.id, 1)
          layer.msg("登记成功", {icon: 1, time: 1000})
          api.searchSourceInfoBeforeParse(formData.value.id, formData.value.year)
              .then(r2 => {
                store.commit('commitParseInfo', r2.data.data)
                router.push("/user/complete")
              })
        } else {
          layer.msg("登记异常，请联系管理员", {icon: 2, time: 1000})
        }
      })
}

</script>

<template>
  <lay-form pane :model="formData">
    <lay-form-item label="身高" prop="year">
      <lay-input placeholder="单位：cm" v-model="formData.height"></lay-input>
    </lay-form-item>
    <lay-form-item label="体重" prop="studentId">
      <lay-input placeholder="单位：kg" v-model="formData.weight"></lay-input>
    </lay-form-item>
    <lay-form-item label="鞋码" prop="name">
      <lay-input v-model="formData.shoeSize"></lay-input>
    </lay-form-item>
    <lay-form-item label="手机号" prop="name">
      <lay-input v-model="formData.phone"></lay-input>
    </lay-form-item>
    <lay-form-item style="text-align: center;">
      <lay-button style="width:100%" type="primary" @click="submit">提交</lay-button>
    </lay-form-item>
  </lay-form>
</template>

<style scoped>

</style>