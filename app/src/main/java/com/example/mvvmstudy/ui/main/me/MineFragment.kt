package com.example.mvvmstudy.ui.main.me

import com.btpj.lib_base.ext.initColors
import com.example.mvvmstudy.R
import com.example.mvvmstudy.base.APP
import com.example.mvvmstudy.base.BaseFragment
import com.example.mvvmstudy.data.local.UserManager
import com.example.mvvmstudy.databinding.FragmentMineBinding
import com.example.mvvmstudy.ui.collect.CollectActivity
import com.example.mvvmstudy.ui.integral.rank.IntegralRankActivity
import com.example.mvvmstudy.ui.login.LoginActivity

class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {
    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView() {
        mBinding.apply {
            swipeRefreshLayout.apply {
                initColors()
                setOnRefreshListener { onRefresh() }
            }

            clUser.setOnClickListener {
                if (!UserManager.isLogin()) {
                    LoginActivity.launch(requireContext())
                }
            }


            // 我的积分
            tvPoints.setOnClickListener {
                IntegralRankActivity.launch(requireContext())
            }
            //我的收藏
            tvCollect.setOnClickListener {
                CollectActivity.launch(requireContext())
            }

        }
        onRefresh()
    }

    private fun onRefresh(){
        if(UserManager.isLogin()){
            mBinding.swipeRefreshLayout.apply {
                isEnabled=true
                isRefreshing=true
            }
            mViewModel.fetchPoints()
        }else{
            mBinding.swipeRefreshLayout.isEnabled=false
        }
    }

    override fun createObserve() {
        super.createObserve()
        APP.appViewModel.userEvent.observe(this){
            mViewModel.user.set(it)
            if(it==null){
                mViewModel.integral.value=null
            }
            onRefresh()
        }
        mViewModel.integral.observe(this){
            mBinding.apply {
                swipeRefreshLayout.isRefreshing=false
                tvInfo.text = "id：${it?.userId ?: '—'}　排名：${it?.rank ?: '—'}"
                tvPointsNum.text = "${it?.coinCount ?: '—'}"
            }
        }
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.swipeRefreshLayout.isRefreshing=false
    }

}
