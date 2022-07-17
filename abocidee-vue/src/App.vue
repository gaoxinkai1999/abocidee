<template>
  <div id="app">
    <!--    <img src="./assets/logo.png">-->
    <!--    <router-view/>-->
    <h1 style="font-size: 50px">旅途助手</h1>
    <div v-if="username!=''">
      <caidan></caidan>
      <keep-alive>
        <router-view :username="username"></router-view>
      </keep-alive>
    </div>
    <div v-if="username==''">
      <br><br><br><br><br>
      <MyLogin v-if="value1==true" @username="getusername"></MyLogin>
      <MyRegister v-if="value1==false" @username="getusername"></MyRegister>
      <br><br><br><br><br>
      <el-switch
        v-model="value1"
        active-text="登录"
        inactive-text="注册"
        style="width: 500px">
      </el-switch>
    </div>
  </div>
</template>

<script>
import caidan from './components/HelloWorld.vue'
import MyMove from './components/MyMove'
import MySelect from './components/MySelect'
import MyHome from './components/MyHome'
import MyLogin from './components/MyLogin'
import MyRegister from './components/MyRegister'

export default {
  name: 'App',
  data() {
    return {
      name: 'Hello World',
      value1: true,
      username: ''
    }
  },
  created() {
    this.$http({
      url: '/user/check',
      method: 'get'

    }).then(res => {
      if (res.data.code === 0) {
        this.username = res.data.data.username
      }
    })
  },
  methods: {
    getusername(val) {
      this.username = val
    },
    success(message) {
      this.$notify({
        title: '成功',
        message: message,
        type: 'success',
        duration: 10000
      })
    },
    error(message) {
      this.$notify.error({
        title: '错误',
        message: message,
        duration: 10000
      })
    }
  },
  components: {
    caidan,
    MyMove,
    MySelect,
    MyHome,
    MyLogin,
    MyRegister

  }
}
</script>

<style>
#app {
  /*font-family: 'Avenir', Helvetica, Arial, sans-serif;*/
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
</style>
