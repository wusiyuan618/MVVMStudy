package com.example.mvvmstudy.ui.collect.article

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.CollectArticle
import com.example.mvvmstudy.databinding.ListItemCollectArticleBinding
import com.example.mvvmstudy.ui.web.WebActivity

class CollectArticleAdapter:BaseQuickAdapter<CollectArticle,BaseDataBindingHolder<ListItemCollectArticleBinding>>(
    layoutResId = R.layout.list_item_collect_article
) ,LoadMoreModule{
    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<ListItemCollectArticleBinding>,
        item: CollectArticle
    ) {
        holder.dataBinding?.apply {
            collectArticle=item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context,item) }
        }
    }
}