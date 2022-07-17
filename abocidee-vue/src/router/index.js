import Vue from 'vue'
import Router from 'vue-router'
import MyHome from '../components/MyHome'
import MyMove from '../components/MyMove'
import MySelect from '../components/MySelect'
import MyManage from '../components/MyManage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MyHome',
      component: MyHome
    },
    {
      path: '/1',
      name: 'MyHome',
      component: MyHome
    },
    {
      path: '/2',
      name: 'MyMove',
      component: MyMove
    },
    {
      path: '/3',
      name: 'MySelect',
      component: MySelect
    },
    {
      path: '/4',
      name: 'MyManage',
      component: MyManage
    }

  ]
})
