package com.example.mvvmstudy.ui.collect.url

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.example.mvvmstudy.data.DataRepository
import com.example.mvvmstudy.data.bean.CollectUrl

class CollectUrlViewModel: BaseViewModel() {

    /** 收藏网址列表LiveData */
    val collectUrlList = MutableLiveData<List<CollectUrl>?>()

    override fun start() {}

    /** 请求收藏网址列表 */
    fun fetchCollectUrlList() {
        launch({
            handleRequest(DataRepository.getCollectUrlList(), { collectUrlList.value = it.data })
        })
    }

    /** 取消收藏网址*/
    fun unCollectUrl(id: Int, successCallBack: () -> Any? = {}) {
        launch({
            handleRequest(DataRepository.unCollectUrl(id), {
                successCallBack.invoke()
            })
        })
    }
}