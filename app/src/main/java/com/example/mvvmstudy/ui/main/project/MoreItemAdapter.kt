package com.example.mvvmstudy.ui.main.project

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.ProjectTitle
import com.example.mvvmstudy.databinding.ListItemTvBinding

/**
 * 下拉拓展的Adapter
 */
class MoreItemAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<ListItemTvBinding>>(layoutResId = R.layout.list_item_tv),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemTvBinding>, item: String) {
        holder.dataBinding?.apply {
            text = item
            executePendingBindings()
        }
    }
}