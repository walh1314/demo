/*
 * @Author: cnblogs.com/vipzhou
 * @Date: 2018-02-22 21:30:19 
 * @Last Modified by: vipzhou
 * @Last Modified time: 2018-02-23 21:13:44
 */

import * as http from './base'

/**
 * 获取设备列表
 * @param {object} params 
 */
const getCameraTypeList = params => {
  return http.fetch('/camera/type/list', params)
}
/**
 * 删除设备
 * @param  {object} params
 */
const deleteCameraTypeById = id => {
  return http.del(`/camera/type/del/${id}`)
}
/**
 * 获取设备详情
 * @param  {id} id
 */
const getCameraTypeDetail = id => {
  return http.fetch(`/camera/type/detail/${id}`, {})
}

/**
 * 保存设备信息
 * @param {object} CameraType 
 */
const updateCameraTypeInfo = CameraType => {
  if (!CameraType.id) {
    return Promise.reject(new Error(`arg id can't be null`))
  }
  return http.put(`//camera/type/update/${CameraType.id}`, CameraType)
}

/**
 * 添加设备
 * @param {CameraType对象} CameraType 
 */
const addCameraType = CameraType => {
  return http.post('/camera/type/add', CameraType)
}

export { getCameraTypeList, deleteCameraTypeById, getCameraTypeDetail, updateCameraTypeInfo, addCameraType }