package com.example.mvvmstudy.ui.collect.url

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.CollectUrl
import com.example.mvvmstudy.databinding.ListItemCollectUrlBinding
import com.example.mvvmstudy.ui.web.WebActivity

class CollectUrlAdapter :
    BaseQuickAdapter<CollectUrl, BaseDataBindingHolder<ListItemCollectUrlBinding>>(layoutResId = R.layout.list_item_collect_url) {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemCollectUrlBinding>, item: CollectUrl) {
        holder.dataBinding?.apply {
            collectUrl = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}