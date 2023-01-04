package com.example.mvvmstudy.ui.main.home

import com.example.mvvmstudy.ui.web.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.mvvmstudy.R
import com.example.mvvmstudy.data.bean.Article
import com.example.mvvmstudy.databinding.ListItemArticleBinding

/**
 * 文章列表的Adapter
 *
 * @author LTP  2022/3/23
 */
class ArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ListItemArticleBinding>>(R.layout.list_item_article),
    LoadMoreModule {

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(holder: BaseDataBindingHolder<ListItemArticleBinding>, item: Article) {
        holder.dataBinding?.apply {
            article = item
            //可以解决数据闪动的问题
            executePendingBindings()
            clItem.setOnClickListener { WebActivity.launch(context, item) }
        }
    }
}