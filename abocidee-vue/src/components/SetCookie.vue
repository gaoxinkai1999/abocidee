<template>
  <div>
    <el-button type="primary" @click="getimgurl">重置</el-button>
    <!--    注册指南弹出框-->
    <el-dialog
      :visible.sync="dialogVisible"
      title="请使用百度app扫码"
      width="50%"
    >
      <el-image
        :src="url"
        style="width: 15%; height: 15%"></el-image>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'SetCookie',
  data () {
    return {
      cookie: '',
      url: '',
      sign: '',
      time: '',
      dialogVisible: false
    }
  },
  methods: {
    setCookie () {
      console.log('开始重置cookie')
      this.$http({
        url: '/user/set',
        method: 'get',
        params: {
          'cookie': this.cookie
        }

      }).then(res => {
        if (res.data.code === 0) {
          this.success()
        } else {
          this.error(res.data.msg)
        }
      })
    },
    getimgurl () {
      this.dialogVisible = true
      this.$http({
        url: '/qrcode/getimg',
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
    checkstate (sign, time) {
      this.$http({
        url: '/qrcode/checkstate',
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
          this.setCookie()
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
