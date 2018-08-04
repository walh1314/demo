/*
 * @Author: cnblogs.com/vipzhou
 * @Date: 2018-07-02 11:30:19 
 * @Last Modified by: pa.liupingan
 * @Last Modified time: 2018-07-02 11:30:19 
 */

import * as http from './base'

/**
 * 获取灯列表
 * @param {object} params 
 */
const getLampList = params => {
  return http.fetch('/lamp/list', params)
}
/**
 * 删除灯
 * @param  {object} params
 */
const deleteLampById = id => {
  return http.del(`/lamp/del/${id}`)
}
/**
 * 获取灯详情
 * @param  {id} id
 */
const getLampDetail = id => {
  return http.fetch(`/lamp/detail/${id}`, {})
}

/**
 * 保存灯信息
 * @param {object} Lamp 
 */
const updateLampInfo = Lamp => {
  if (!Lamp.id) {
    return Promise.reject(new Error(`arg id can't be null`))
  }
  return http.put(`/lamp/update/${Lamp.id}`, Lamp)
}

/**
 * 添加灯
 * @param {Lamp对象} Lamp 
 */
const addLamp = Lamp => {
  return http.post('/lamp/add', Lamp)
}

/**
 * 添加灯
 * @param {Lamp对象} Lamp 
 */
const getLampTypeList = params => {
  return http.fetch('/lamp/type/list', params)
}

export { getLampList, deleteLampById, getLampDetail, updateLampInfo, addLamp,getLampTypeList }