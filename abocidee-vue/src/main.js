// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI, {Loading} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import {error, success} from './until'

Vue.config.productionTip = false
Vue.use(ElementUI)
// axios.defaults.baseURL = 'http://124.223.48.47:8080'
axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true // 让ajax携带cookie
Vue.prototype.$http = axios
Vue.prototype.success = success
Vue.prototype.error = error
let loading
// 内存中正在请求的数量
let loadingNum = 0

function startLoading() {
  if (loadingNum === 0) {
    loading = Loading.service({
      lock: true,
      text: '拼命加载中...',
      background: 'rgba(255,255,255,0.5)'
    })
  }
  // 请求数量加1
  loadingNum++
}

function endLoading() {
  // 请求数量减1
  loadingNum--
  if (loadingNum <= 0) {
    loading.close()
  }
}

// 请求数据拦截器
axios.interceptors.request.use(request => {
  startLoading()
  return request
}, err => {
  return Promise.reject(err)
})

// 接收响应拦截器
axios.interceptors.response.use(response => {
  endLoading()
  return response
}, err => {
  endLoading()
  if (err && err.response) {
    switch (err.response.status) {
      case 400:
        err.message = '请求错误(400)'
        break
      case 401:
        this.$router.push('/login')
        break
      case 403:
        err.message = '拒绝访问(403)'
        break
      case 404:
        err.message = '请求出错(404)'
        break
      case 408:
        err.message = '请求超时(408)'
        break
      case 500:
        err.message = '服务器错误(500)'
        break
      case 501:
        err.message = '服务未实现(501)'
        break
      case 502:
        err.message = '网络错误(502)'
        break
      case 503:
        err.message = '服务不可用(503)'
        break
      case 504:
        err.message = '网络超时(504)'
        break
      case 505:
        err.message = 'HTTP版本不受支持(505)'
        break
      default:
        err.message = `连接出错(${err.response.status})!`
    }
  } else {
    err.message = '连接服务器失败!'
  }
  return Promise.reject(err)
})

// eslint-disable-next-line no-new
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
