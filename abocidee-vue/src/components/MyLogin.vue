<template>
  <el-form label-width="80px">
    <el-form-item label="">
      <el-input v-model="username" placeholder="用户名" style="width: auto"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

export default {
  name: 'MyLogin',
  data() {
    return {
      username: ''
    }
  },
  methods: {
    login() {
      console.log('登录中')
      this.$http({
        url: '/user/login',
        method: 'get',
        params: {
          'username': this.username
        }

      }).then(res => {
        console.log(res)
        if (res.data.code === 0) {
          this.$emit('username', this.username)
        } else {
          this.error(res.data.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
