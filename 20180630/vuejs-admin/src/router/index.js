import Vue from 'vue'
import Router from 'vue-router'

import store from '../store'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: resolve => require(['@/views/auth/Login'], resolve)
    },
    {
      path: '',   // 默认地址为登录页
      name: '',
      component: resolve => require(['@/views/auth/Login'], resolve)
    },
    {
      path: '/main',
      name: '',
      component: resolve => require(['@/views/Main'], resolve),
      meta: {
        requireAuth: true,    // 添加该字段，表示进入这个路由是需要登录的
        nav: '欢迎页'
      },
      children: [{
        path: 'user',
        component: resolve => require(['@/views/user/List'], resolve),
        name: 'UserList',
        meta: {
          requireAuth: true,
          nav: '用户管理',
          activeItem: '1-1'
        },
      }, {
        path: 'user/setting/:userId?',
        name: 'UserSetting',
        component: resolve => require(['@/views/user/Setting'], resolve),
        meta: {
          requireAuth: true,
          nav: '资料设置',
          activeItem: '1-2'
        },
      },{
        path: 'logs',
        component: resolve => require(['@/views/logs/List'], resolve),
        name: 'logs',
        meta: {
          requireAuth: true,
          nav: '日志管理',
          activeItem: '1-3'
        },
      }, {
        path: 'camera',
        component: resolve => require(['@/views/camera/List'], resolve),
        name: 'CameraList',
        meta: {
          requireAuth: true,
          nav: '设备列表',
          activeItem: '3-1'
        },
      },{
        path: 'camera/edit/:cameraId?',
        component: resolve => require(['@/views/camera/edit'], resolve),
        name: 'CameraEdit',
        meta: {
          requireAuth: true,
          nav: '设备设置',
          activeItem: '3-2'
        },
      },{
        path: 'camera/type',
        component: resolve => require(['@/views/camera/Type.List'], resolve),
        name: 'DevTypeList',
        meta: {
          requireAuth: true,
          nav: '设备类别',
          activeItem: '3-3'
        },
      },{
        path: 'camera/mark/:deviceId/:topic?',
        component: resolve => require(['@/views/camera/Mark'], resolve),
        name: 'CameraMark',
        meta: {
          requireAuth: true,
          nav: '灯号设定',
          activeItem: '3-4'
        },
      },  {
        path: '',   // 后台首页默认页
        component: resolve => require(['@/views/common/Welcome'], resolve),
        name: 'Welcome',
        meta: {
          requireAuth: true,
          nav: '欢迎页'
        },
      }]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/' && store.state.user.token) {
    return next('/main')
  }
  if (to.meta.requireAuth) {    // 如果需要拦截
    if (store != undefined && store.state != undefined &&  store.state.user != undefined &&  store.state.user.token) {
      next()
    } else {
      next({
        path: '/',
        query: {
          redirect: to.fullPath
        }
      })
    }
  } else {
    next()
  }
})

export default router