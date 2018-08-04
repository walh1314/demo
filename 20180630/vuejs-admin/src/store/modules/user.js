import * as TYPES from '../mutation.types'

const state = {
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}'),
    token: localStorage.getItem('token') || ''
}

const actions = {
    
}
const mutations = {
    [TYPES.LOGIN]: (state, loginData) => {
        state.userInfo = loginData.data.userInfo
        state.token = loginData.data.token
        localStorage.setItem('userInfo', JSON.stringify(loginData.data.userInfo))
        localStorage.setItem('token', loginData.data.token)
    },
    [TYPES.LOGOUT]: state => {
        state.userInfo = {}
        state.token = ''
        localStorage.removeItem('userInfo')
        localStorage.removeItem('token')
    }
}

const getters = {

}

export default {
    state,
    actions,
    mutations,
    getters
}


