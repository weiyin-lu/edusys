<script setup>
import {onMounted, ref} from "vue";
import store from "../../store/store.js";
import router from "../../route/router.js";

onMounted(() => {
  store.commit("commitStep", 100)
  formData.value.id = store.state.parseInfo.id
  formData.value.name = store.state.parseInfo.name
  formData.value.height = store.state.parseInfo.height
  formData.value.weight = store.state.parseInfo.weight
  formData.value.shoeSize = store.state.parseInfo.shoeSize
  formData.value.parseShoes = store.state.parseInfo.parseShoes
  formData.value.parseShirt = store.state.parseInfo.parseShirt
  formData.value.parseCloth = store.state.parseInfo.parseCloth
})

const formData = ref({
  name: null,
  id: null,
  height: null,
  weight: null,
  shoeSize: null,
  parseShoes: null,
  parseShirt: null,
  parseCloth: null
})
</script>

<template>
  <div class="card">
    <hr>
    <p class="text">登记完成</p>
    <p class="text2">
      {{ formData.name }}：你的服装信息已经登记完成。如需修改，可以
      <a href="#" class="url" @click="router.push('/user/form')">点击此处重新登记</a>
    </p>
    <hr>
    <p class="text3">
      衬衫参考尺码：{{ formData.parseShirt }}
    </p>
    <p class="text3">
      外装参考尺码：{{ formData.parseCloth }}
    </p>
    <p class="text3">
      鞋码转换：{{ formData.parseShoes }}
    </p>
    <hr>
    <p class="text3">
      登记身高：{{ formData.height }}cm
    </p>
    <p class="text3">
      登记体重：{{ formData.weight }}kg
    </p>
    <p class="text3">
      登记鞋码：{{ formData.shoeSize }}
    </p>
    <hr>
  </div>
</template>

<style scoped>
.url {
  color: blue;
}

.card {
  padding: 60px 10px 10px 10px;
  height: 380px;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

.text {
  color: forestgreen;
  text-align: center;
  letter-spacing: 0.2rem;
  font-size: 1.5rem;
  font-weight: bold;
  padding-bottom: 10px;
}

.text2 {
  text-align: center;
  letter-spacing: 0.2rem;
  font-size: 1rem;
  font-weight: bold;
}

.text3 {
  text-align: center;
  letter-spacing: 0.2rem;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>