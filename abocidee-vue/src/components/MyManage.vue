<template>
  <div :inlist="true">

    <h2>新增公会</h2>
    <el-form :inline="true" label-width="80px">
      <el-form-item label="cookie">
        <el-input v-model="cookie" placeholder="cookie" style="width: auto"></el-input>
      </el-form-item>
      <el-form-item label="公会id">
        <el-input v-model="unionid" placeholder="公会id" style="width: auto"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="add">提交</el-button>
      </el-form-item>
    </el-form>
    <br><br><br><br><br><br><br><br>
    <h2>重置公会cookie</h2>
    <el-form :inline="true" label-width="80px">
      <el-form-item label="cookie">
        <el-input v-model="newcookie" placeholder="cookie" style="width: auto"></el-input>
      </el-form-item>
      <el-form-item label="公会">
        <el-select v-model="unionname" placeholder="公会">
          <el-option v-for="union in unions" v-if="unions != null" :key="union" :label='union' :value="union">
            {{ union }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="set">提交</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  name: 'MyManage',
  data() {
    return {
      cookie: '',
      unionname: '',
      unionid: '',
      newcookie: '',
      unions: ''
    }
  },
  methods: {
    add() {
      this.$http({
        url: '/union/add',
        method: 'get',
        params: {
          'cookie': this.cookie,
          'unionid': this.unionid
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.success()
        } else {
          this.error(res.data.msg)
        }
      })
    },
    set() {
      this.$http({
        url: '/union/set',
        method: 'get',
        params: {
          'cookie': this.newcookie,
          'unionname': this.unionname
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.success()
        } else {
          this.error(res.data.msg)
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
