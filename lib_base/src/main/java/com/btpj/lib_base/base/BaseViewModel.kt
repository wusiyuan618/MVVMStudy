package com.btpj.lib_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.btpj.lib_base.data.bean.ApiResponse

/**
 * ViewModel基类 包装了用于服务器请求返回的LiveData
 * ViewModel 类旨在以注重生命周期的方式存储和管理界面相关的数据。ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。可用来管理LiveData
 * LiveData 是一种可观察的数据存储器类。具有感知生命周期的能力。可以确保LiveData仅更新处于活跃生命周期状态的应用组件观察者
 */
abstract class BaseViewModel : ViewModel() {

    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    abstract fun start()
}