/*
 * @Author: cnblogs.com/vipzhou
 * @Date: 2018-02-22 21:30:19 
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2018-02-22 22:02:47
 */

import * as http from './base'

/**
 * 获取设备列表
 * @param {object} params 
 */
const getCameraList = params => {
  return http.fetch('/camera/pageList', params)
}
/**
 * 删除设备
 * @param  {object} params
 */
const deleteCameraById = id => {
  return http.del(`/camera/del/${id}`)
}
/**
 * 获取设备详情
 * @param  {id} id
 */
const getCameraDetail = id => {
  return http.fetch(`/camera/detail/${id}`, {})
}

/**
 * 保存设备信息
 * @param {object} camera 
 */
const updateCameraInfo = camera => {
  if (!camera.id) {
    return Promise.reject(new Error(`arg id can't be null`))
  }
  return http.put(`/camera/update/${camera.id}`, camera)
}

/**
 * 添加设备
 * @param {camera对象} camera 
 */
const addCamera = camera => {
  return http.post('/camera/add', camera)
}

/**
 * 获取camera所表示的所有灯号
 * @param {camera对象} camera 
 */
const getMarkLamps = params => {
  return http.fetch(`/camera/getMarkLamps`, params)
}


const getMarkImage = params => {
  return http.fetch(`/camera/getMarkImage`, params)
}



/**
 * 添加三色灯标示
 * @param {camera对象} camera 
 */
const addCameraMarkLamp = params => {
  return http.post(`/camera/addMarkLamp`, params)
}

/**
 * 获取camera所表示的所有灯号
 * @param {camera对象} camera 
 */
const updateCameraMarkLamp = params => {
  return http.post(`/camera/updateMarkLamp`, params)
}

/** 
 * 删除三色灯标示
 * @param {camera对象} camera 
 */
const delCameraMarkLamp = id => {
  return http.del(`/camera/delMarkLamp/${id}`, {})
}

export { delCameraMarkLamp,getCameraList, deleteCameraById, 
  getCameraDetail, updateCameraInfo, addCamera,addCameraMarkLamp,updateCameraMarkLamp,getMarkLamps,getMarkImage}