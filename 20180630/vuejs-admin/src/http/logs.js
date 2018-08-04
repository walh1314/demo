/*
 * @Author: cnblogs.com/vipzhou
 * @Date: 2018-02-22 21:30:19 
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2018-02-22 22:02:47
 */

import * as http from './base'

/**
 * 添加设备
 * @param {logs} logs 
 */
const getLogByUser = logs => {
  return http.post('/logs/getLogByUser', logs)
}

export {getLogByUser}