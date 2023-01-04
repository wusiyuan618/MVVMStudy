package com.example.mvvmstudy.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.example.mvvmstudy.base.APP
import com.example.mvvmstudy.data.DataRepository
import com.example.mvvmstudy.data.local.UserManager

class LoginViewModel :BaseViewModel(){
    //运用DataBinding绑定数据，如果是普通的基本数据类型是无法实现数据改变视图跟着改变的效果的。需要用ObservableField<T>替代
    val userName = ObservableField("")
    val userPwd = ObservableField("")

    /** 登录按键是否可点击(这样可避免在dataBinding中写较复杂的逻辑) */
    val loginBtnEnable = object : ObservableBoolean(userName, userPwd) {
        override fun get(): Boolean {
            return !userName.get()?.trim().isNullOrEmpty() && !userPwd.get().isNullOrEmpty()
        }
    }

    override fun start() {
        userName.set(UserManager.getLastUserName())
    }
    /**
     * 登录
     * @param userName 用户名
     * @param pwd 密码
     */
    fun login(userName: String, pwd: String, successCall: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.login(userName, pwd), successBlock = {
                UserManager.saveLastUserName(userName)
                UserManager.saveUser(it.data)
                APP.appViewModel.userEvent.value = it.data
                successCall.invoke()
            },errorBlock={false})
        })
    }
}