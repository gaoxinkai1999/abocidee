<template>
  <div>
    <h1>一键转会</h1>
    <br><br><br><br><br><br><br><br>
    <el-row>
      <el-button v-for="union in unions" :key="union" type="info" @click="move(union)">{{ union }}</el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'MyMove',
  data() {
    return {
      unions: ''
    }
  },
  methods: {
    move(union) {
      this.$http({
        url: 'zhuanhui',
        method: 'get',
        params: {
          'unionname': union
        }
      }).then(res => {
        if (res.data.msg === 'fail') {
          this.error(res.data.text)
        } else {
          this.success()
        }
      })
    }
  },
  created() {
    this.$http({
      url: 'getunion',
      method: 'get'
    }).then(res => {
      this.unions = res.data.unions
    })
  }
}
</script>

<style scoped>

</style>
