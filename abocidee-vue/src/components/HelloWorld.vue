<template>
  <el-menu
    :router="true"
    active-text-color="#ffd04b"
    background-color="#545c64"
    class="el-menu-demo"
    mode="horizontal"
    text-color="#fff" @select="handleSelect">
    <el-menu-item index="1" style="width: 200px">主页</el-menu-item>
    <el-menu-item index="2" style="width: 200px">一键转会</el-menu-item>
    <el-menu-item index="3" style="width: 200px">查询审核包</el-menu-item>
    <el-menu-item index="4" style="width: 200px">管理员</el-menu-item>
  </el-menu>

</template>
<script>

export default {
  name: 'caidan',
  data () {
    return {
      // 当前要渲染的组件名称
      comName: 'MyMove'
    }
  },
  methods: {
    handleSelect (key, keyPath) {
    }

  }
}
</script>
