<template>
  <!--  显示当前用户名-->
  <!--  具有退出登录，重置cookie的功能-->
  <div>
    <h3>当前用户:{{ username }}</h3>
    <br><br><br>
    <h4>
      此版本重写前端框架,修复后端部分bug&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;---author
    </h4>
    <br><br>
    <SetCookie v-if="isshowcookie==true"></SetCookie>
    <br><br><br><br><br><br><br><br><br><br><br>
    <el-row>
      <el-button round type="primary" @click="isshowcookie=!isshowcookie">重置cookie</el-button>

      <el-button round type="primary" @click="exit">退出登录</el-button>
    </el-row>
  </div>
</template>

<script>

import SetCookie from './SetCookie'

export default {
  name: 'MyHome',
  data () {
    return {
      isshowcookie: false
    }
  },
  methods: {
    exit () {
      this.$http({
        url: '/user/logout',
        method: 'get'
      }).then(res => {
        location.reload()
      })
    }
  },
  created () {
  },
  props: ['username'],
  components: {
    SetCookie
  },
  // 在keepalive下保持数据更新
  activated () {
    console.log('渲染中')
  }
}
</script>

<style scoped>

</style>
