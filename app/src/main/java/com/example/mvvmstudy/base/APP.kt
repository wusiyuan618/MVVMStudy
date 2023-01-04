package com.example.mvvmstudy.base

import android.annotation.SuppressLint
import android.content.Context
import com.btpj.lib_base.BaseApp
import com.tencent.bugly.Bugly

/**
 * Application基类
 *
 * @author LTP  2022/3/21
 */
class APP:BaseApp() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var appViewModel: AppViewModel
    }
    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        context=applicationContext

        // bugly初始化
//        Bugly.init(applicationContext, "99ff7c64d9", false)
    }

}