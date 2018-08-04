/*
 * @Author: cnblogs.com/vipzhou
 * @Date: 2018-02-22 21:29:32 
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2018-02-22 21:48:40
 */
import axios from 'axios'

import router from '../router'
import store from '../store'
import {Message} from 'element-ui'

// axios 配置
axios.defaults.timeout = 30000
//axios.defaults.baseURL = '/api/v1'
axios.defaults.baseURL = '/lamp/'

axios.defaults.withCredentials = true;

// 请求拦截器
axios.interceptors.request.use(config => {
  if (store.state.user.token) {   // TODO 判断token是否存在
    config.headers.Authorization = `Bearer ${store.state.user.token}`
  }
  return config
}, err => {
  return Promise.reject(err)
})

axios.interceptors.response.use(response => {
  return handleInterceptorResponse(response);
}, err => {
  if (err.response) {
    switch (err.response.status) {
      case 401:
        store.commit('LOGOUT')
        router.replace({ path: '/', query: { redirect: router.currentRoute.fullPath } })
        break
      case 403:
        store.commit('LOGOUT')
        router.replace({ path: '/', query: { redirect: router.currentRoute.fullPath } })
        break
    }
  }
  return Promise.reject(new Error(err.response.data.error || err.message))
})

/**
 * @param  {string} url
 * @param  {object} params={}
 */
const fetch = (url, params = {}) => {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params
    }).then(res => {
      handleResponse(res,resolve)
    }).catch(err => {
      reject(err)
    })
  })
}
/**
 * @param  {string} url
 * @param  {object} data={}
 */
const post = (url, data = {}) => {
  return new Promise((resolve, reject) => {
    axios.post(url, data).then(res => {
      handleResponse(res,resolve)
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * @param  {string} url
 * @param  {object} data={}
 */
const put = (url, data = {}) => {
  return new Promise((resolve, reject) => {
    axios.put(url, data).then(res => {
      handleResponse(res,resolve)
    }).catch(err => {
      reject(err)
    })
  })
}
/**
 * @param  {string} url
 * @param  {object} params={}
 */
const del = (url) => {
  return new Promise((resolve, reject) => {
    axios.delete(url, {}).then(res => {
      handleResponse(res,resolve)
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * 统一异常处理
 */
const handleResponse = function(res,resolve){
  if(res.data != undefined && res.data.code != undefined ){
    switch (res.data.code){
      case '1':
        resolve(res.data); 
        break;
      case 'login-1000-01':
        Message.error(res.data.msg || "系统异常，请联系管理员");
        //默认跳转到登录页
        router.push({name: ''});
        break;
      case 'login-0000-01':
          Message.error(res.data.msg || "系统异常，请联系管理员");
          store.state.userInfo = {}
          store.state.token = '';
          localStorage.removeItem('userInfo');
          localStorage.removeItem('token');
          //默认跳转到登录页
          router.push({name: ''});
          break;
      default:
        resolve(res.data);      
    }
  }
}
  /**
 * 统一异常处理
 */
const handleInterceptorResponse = function(res){
  if(res.data != undefined && res.data.code != undefined ){
    switch (res.data.code){
      case '1':
        return res;
      case 'login-1000-01':
        Message.error(res.data.msg || "系统异常，请联系管理员");
        //默认跳转到登录页
        router.push({name: ''});
        return res;
        break;
      case 'login-0000-01':
          Message.error(res.data.msg || "系统异常，请联系管理员");
          store.state.userInfo = undefined;
          store.state.token = undefined;
          localStorage.removeItem('userInfo');
          localStorage.removeItem('token');
          //默认跳转到登录页
          //router.push({path:"/"});
          router.push({name: ''});
          return res;
          break;
      default:
        return res;     
    }
  }
}
export { axios, fetch, post, put, del }