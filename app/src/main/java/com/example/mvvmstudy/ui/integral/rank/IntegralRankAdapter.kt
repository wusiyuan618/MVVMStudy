package com.example.mvvmstudy.ui.integral.rank

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.CoinInfo
import com.example.mvvmstudy.databinding.ListItemIntegralBinding

class IntegralRankAdapter  :
    BaseQuickAdapter<CoinInfo, BaseDataBindingHolder<ListItemIntegralBinding>>(layoutResId = R.layout.list_item_integral),
    LoadMoreModule {
    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }
    override fun convert(holder: BaseDataBindingHolder<ListItemIntegralBinding>, item: CoinInfo) {
        holder.dataBinding?.apply {
            integral=item
            executePendingBindings()
        }
    }

}