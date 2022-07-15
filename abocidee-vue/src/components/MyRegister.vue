<template>
  <div>
    <!--    注册表单-->
    <el-form label-width="80px">
      <el-form-item label="">
        <el-input v-model="cookie" placeholder="cookie" style="width: auto"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>

      </el-form-item>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="text" @click="dialogVisible = true">注册指南</el-button>
    </el-form>
    <!--    注册指南弹出框-->
    <el-dialog
      :visible.sync="dialogVisible"
      title="提示"
      width="50%"
    >
      <el-image
        :fit="fits[0]"
        :src="url"
        style="width: 70%; height: 70%"></el-image>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </el-dialog>
  </div>
</template>
<script>
import imgurl from '../assets/text.png'

export default {
  name: 'MyRegister',
  data() {
    return {
      cookie: '',
      // 控制弹窗
      dialogVisible: false,
      url: imgurl,
      fits: ['fill', 'contain', 'cover', 'none', 'scale-down']
    }
  },
  methods: {
    register() {
      console.log('注册中')
      this.$http({
        url: '注册',
        method: 'get',
        params: {
          'cookie': this.cookie
        }
      }).then(res => {
        console.log(res.data)
        if (res.data.msg === 'fail') {
          this.error(res.data.text)
        } else {
          this.$emit('username', res.data.username)
        }
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    }
  }
}
</script>

<style scoped>

</style>
