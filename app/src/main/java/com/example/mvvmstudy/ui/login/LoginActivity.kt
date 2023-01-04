package com.example.mvvmstudy.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.btpj.lib_base.ext.hideLoading
import com.btpj.lib_base.ext.showLoading
import com.btpj.lib_base.utils.LogUtil
import com.example.mvvmstudy.R
import com.example.mvvmstudy.base.BaseActivity
import com.example.mvvmstudy.databinding.ActivityLoginBinding
import com.example.mvvmstudy.ext.launchNormalRun
import com.example.mvvmstudy.ext.launchRun
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity :BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login){
    companion object {
        /**
         * 页面启动
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            tvRegister.setOnClickListener {
                // 使用Activity Results API
//                registerForActivityResult.launch(RegisterActivity.newIntent(this@LoginActivity))
                LogUtil.i("点击了注册")
            }

            btnLogin.setOnClickListener {
                showLoading("登录中...")
                mViewModel.login(mViewModel.userName.get()!!, mViewModel.userPwd.get()!!) {
                    LogUtil.i("登录成功")
                    hideLoading()
                    onBackPressed()
                }
            }

            ivLogo.setOnClickListener {
//                launchChangeIpActivity()
            }
        }
    }
}