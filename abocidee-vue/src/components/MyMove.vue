<template>
  <div>
    <h1>一键转会</h1>
    <br><br><br><br><br><br><br><br>
    <el-row>
      <el-button v-for="union in unions" :key="union" type="info" @click="move(union,false)">{{ union }}</el-button>
    </el-row>
    <br><br><br><br><br><br><br><br><br><br><br><br>
    <el-button type="" @click="move(null,true)">只退会</el-button>
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
    move(union, onlyexit) {
      this.$http({
        url: '/action/move',
        method: 'get',
        params: {
          'unionname': union,
          'onlyexit': onlyexit
        }
      }).then(res => {
        if (res.data.code === 1) {
          this.error(res.data.msg)
        } else {
          this.success()
        }
      })
    }
  },
  created() {
    this.$http({
      url: '/union/getAll',
      method: 'get'
    }).then(res => {
      this.unions = res.data.data
    })
  }
}
</script>

<style scoped>

</style>
