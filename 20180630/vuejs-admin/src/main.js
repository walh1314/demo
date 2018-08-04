// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { axios } from './http/base'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import './assets/fonts/iconfont.css'

import ECharts from 'vue-echarts/components/ECharts'
// import ECharts modules manually to reduce bundle size
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'

// register component to use
Vue.component('chart', ECharts)

import store from './store'

import moment from 'moment'
import Stompjs from "stompjs"
import SockJS from "sockjs-client"
//import {Signal} from "await-signal"

//const sysconstant = require('../../config/sysconstant')
// const sysconstant = require('')

// const websocketClient  = Stompjs.over(new SockJS(sysconstant.WEBSOCKET_SERVICE));
Vue.prototype.$moment = moment
Vue.prototype.$Stompjs = Stompjs
Vue.prototype.$SockJS = SockJS
//Vue.prototype.$Signal = Signal

//Vue.use(Signal);
//var aa = new Signal("wait");
//new Signal("wait");

Vue.use(ElementUI)

// 引入mqtt
import './mq'

Vue.config.productionTip = false

// 挂载到prototype上面，确保组件中可以直接使用this.axios
// Vue.prototype.axios = axios

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>',
  filters: {
    formatDate: function (data) {
      var now = new Date(data);
      var year = now.getFullYear();
      var month = now.getMonth() + 1;
      var date = now.getDate();
      var hour = now.getHours();
      var minute = now.getMinutes();
      var second = now.getSeconds();
      return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":" + second;
    }
  },methods:{
     sleep: function(time) {
        var start = new Date().getTime();
        while (true) {
            if (new Date().getTime() - start > time) {
                break;
            }
        }
    }
  }
})
