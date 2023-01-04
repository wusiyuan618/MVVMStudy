package com.example.mvvmstudy.ui.main.me

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.example.mvvmstudy.data.DataRepository
import com.example.mvvmstudy.data.bean.CoinInfo
import com.example.mvvmstudy.data.bean.User
import com.example.mvvmstudy.data.local.UserManager

class MineViewModel: BaseViewModel() {
    /**
     * 1、ObservableField只有在数据发生改变时UI才会收到通知，而LiveData不同，只要你postValue或者setValue，UI都会收到通知，不管数据有无变化
     * 2、 LiveData能感知Activity的生命周期，在Activity不活动的时候不会触发，例如一个Activity不在任务栈顶部
     */
    val user=ObservableField<User?>()
    val integral= MutableLiveData<CoinInfo?>()
    val userName = object : ObservableField<String>(user) {
        override fun get(): String {
            return if (UserManager.isLogin()) user.get()!!.nickname else "请登录"
        }
    }

    override fun start() {
        if(UserManager.isLogin()){
            user.set(UserManager.getUser())
        }
    }

    fun fetchPoints(){
        launch({
            val response=DataRepository.getUserIntegral()
            handleRequest(response,{
                    integral.value=response.data
                }
            )
        })
    }

}