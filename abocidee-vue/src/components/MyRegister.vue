<template>
  <div>
    <el-button type="primary" @click="getimgurl">注册</el-button>

    <!--    注册指南弹出框-->
    <el-dialog
      :visible.sync="dialogVisible"
      title="请使用百度app扫码"
      width="50%"
    >
      <el-image
        :fit="fits[0]"
        :src="url"
        style="width: 15%; height: 15%"></el-image>
    </el-dialog>
  </div>
</template>
<script>

export default {
  name: 'MyRegister',
  data() {
    return {
      cookie: '',
      // 控制弹窗
      dialogVisible: false,
      url: '',
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      sign: '',
      time: ''
    }
  },
  methods: {
    register() {
      console.log('注册中')
      this.$http({
        url: '/user/add',
        method: 'get',
        params: {
          'cookie': this.cookie
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.$emit('username', res.data.data.username)
        } else {
          this.error(res.data.msg)
        }
      })
    },
    getimgurl() {
      this.dialogVisible = true
      this.$http({
        url: '/getimg',
        method: 'get'
      }).then(res => {
        if (res.data.code === 0) {
          this.url = res.data.data.imgurl
          this.sign = res.data.data.sign
          this.isshow = true
          this.checkstate(this.sign, res.data.data.time)
        } else {
          this.error(res.data.msg)
        }
      })
    },
    checkstate(sign, time) {
      this.$http({
        url: '/checkstate',
        method: 'get',
        params: {
          'sign': sign,
          'time': time
        },
        headers: {
          'showLoading': false
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.success(res.data.msg)
          this.cookie = res.data.data.cookie
          this.register()
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
