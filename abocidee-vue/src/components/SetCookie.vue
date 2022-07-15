<template>
  <el-form label-width="80px">
    <el-form-item label="">
      <el-input v-model="cookie" placeholder="cookie" style="width: auto"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="setCookie">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'SetCookie',
  data() {
    return {
      cookie: ''
    }
  },
  methods: {
    setCookie() {
      console.log('开始重置cookie')
      this.$http({
        url: 'setcookie',
        method: 'get',
        params: {
          'cookie': this.cookie
        }

      }).then(res => {
        console.log('重置完成')
      })
    }
  }
}
</script>

<style scoped>

</style>
