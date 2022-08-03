<template>
  <div>
    <img v-if="imgurl!=null"
         :src="imgurl"/>
    <br>
    <el-button type="primary" @click="getimgurl">二维码登录</el-button>
  </div>
</template>

<script>
export default {
  name: 'MyQrcode',
  data () {
    return {
      imgurl: null,
      isshow: false,
      sign: ''
    }
  },
  methods: {
    getimgurl () {
      this.$http({
        url: '/getimg',
        method: 'get'
      }).then(res => {
        if (res.data.code === 0) {
          this.imgurl = res.data.data.imgurl
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
