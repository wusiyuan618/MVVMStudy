package com.example.mvvmstudy.data

import com.btpj.lib_base.data.bean.ApiResponse
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import com.example.mvvmstudy.data.bean.*
import com.example.mvvmstudy.data.http.Api

/**
 *
 */
object DataRepository: BaseRepository(), Api {
    //lazy是kotlin标准库实现的，它是属性懒加载的一种方式。在对属性使用时才对属性进行初始化。同时初始化操作时加锁，保证多线程环境下线程安全
    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return apiCall { service.login(username, pwd) }
    }
    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return apiCall { service.getBanner() }
    }
    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getArticlePageList(pageNo, pageSize) }
    }
    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return apiCall { service.getArticleTopList() }
    }

    override suspend fun getUserIntegral(): ApiResponse<CoinInfo> {
        return apiCall { service.getUserIntegral() }
    }

    override suspend fun getIntegralRankPageList(pageNo: Int): ApiResponse<PageResponse<CoinInfo>> {
        return apiCall { service.getIntegralRankPageList(pageNo) }
    }

    override suspend fun collectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.collectArticle(id) }
    }

    override suspend fun unCollectArticle(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectArticle(id) }
    }


    override suspend fun collectUrl(name: String, link: String): ApiResponse<CollectUrl?> {
        return apiCall { service.collectUrl(name, link) }
    }

    override suspend fun unCollectUrl(id: Int): ApiResponse<Any?> {
        return apiCall { service.unCollectUrl(id) }
    }

    override suspend fun getCollectArticlePageList(pageNo: Int): ApiResponse<PageResponse<CollectArticle>> {
        return apiCall { service.getCollectArticlePageList(pageNo) }
    }

    override suspend fun getCollectUrlList(): ApiResponse<List<CollectUrl>> {
        return apiCall { service.getCollectUrlList() }
    }

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return apiCall { service.getProjectTitleList() }
    }

    override suspend fun getNewProjectPageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getNewProjectPageList(pageNo,pageSize) }    }

    override suspend fun getProjectPageList(
        pageNo: Int,
        pageSize: Int,
        categoryId: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getProjectPageList(pageNo,pageSize,categoryId) }    }

    override suspend fun getAuthorArticlePageList(
        authorId: Int,
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getAuthorArticlePageList(authorId, pageNo, pageSize) }
    }

    override suspend fun getAuthorTitleList(): ApiResponse<List<Classify>> {
        return apiCall { service.getAuthorTitleList() }
    }
}