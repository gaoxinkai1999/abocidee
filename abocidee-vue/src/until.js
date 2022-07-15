export function success(message) {
  this.$notify({
    title: '成功',
    message: message,
    type: 'success',
    duration: 6000
  })
}

export function error(message) {
  this.$notify.error({
    title: '错误',
    message: message,
    duration: 6000
  })
}
