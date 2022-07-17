<template>
  <div>
    <h3>查询审核包</h3>
    <!--    表单-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="题包id">
        <el-input v-model="formInline.markid" placeholder="题包id"></el-input>
      </el-form-item>
      <el-form-item label="公会">
        <el-select v-model="formInline.union" placeholder="公会">
          <el-option v-for="union in unions" v-if="unions != null" :key="union" :label='union' :value="union">
            {{ union }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="select">查询</el-button>
      </el-form-item>
    </el-form>
    <!--分割线-->
    <el-divider></el-divider>
    <!--表格-->
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        label="用户名"
        prop="user_name"
        width="280">
      </el-table-column>
      <el-table-column
        label="答题量"
        prop="page_count"
        width="280">
      </el-table-column>
      <el-table-column
        label="框"
        prop="rect"
        width="280">
      </el-table-column>
      <el-table-column
        label="图像矩形框"
        prop="imageRect"
        width="280">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

export default {
  name: 'MySelect',
  data() {
    return {
      formInline: {
        markid: '',
        union: ''
      },
      unions: '',
      tableData: []
    }
  },
  created() {
    this.$http({
      url: '/union/getAll',
      method: 'get'

    }).then(res => {
      this.unions = res.data.data
    })
  },
  methods: {
    select() {
      console.log('开始查询')
      this.$http({
        url: '/action/select',
        method: 'get',
        params: {
          'markid': this.formInline.markid,
          'unionname': this.formInline.union
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.tableData = res.data.data
          this.success()
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
