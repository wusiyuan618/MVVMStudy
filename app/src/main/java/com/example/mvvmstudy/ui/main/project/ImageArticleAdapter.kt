package com.example.mvvmstudy.ui.main.project

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.Article
import com.example.mvvmstudy.databinding.ListItemArticleImageBinding
import com.example.mvvmstudy.ui.web.WebActivity

class ImageArticleAdapter:
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleImageBinding>>(layoutResId = R.layout.list_item_article_image),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<ListItemArticleImageBinding>,
        item: Article
    ) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()

            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}