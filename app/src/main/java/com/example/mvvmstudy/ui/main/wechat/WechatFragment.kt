package com.example.mvvmstudy.ui.main.wechat

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.mvvmstudy.R
import com.example.mvvmstudy.base.BaseFragment
import com.example.mvvmstudy.data.bean.Classify
import com.example.mvvmstudy.databinding.FragmentViewpagerBinding
import com.example.mvvmstudy.ui.main.project.MoreItemAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayoutMediator

class WechatFragment :
    BaseFragment<WechatViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** TabLayout的标题集合 */
    private val mAuthorTitleList = ArrayList<Classify>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = WechatFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mAuthorTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return WechatChildFragment.newInstance(authorId = mAuthorTitleList[position].id)
            }
        }

        mBinding.apply {
            // 由于标题也需要请求（只有请求完标题后才会加载Fragment从而显示swipeRefreshLayout），
            // 所以在请求标题之前也需要一个loading
            showLoading = true

            viewPager2.apply {
                adapter = mFragmentStateAdapter
            }

            mTabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.apply {
                    // 处理长按出现toast的问题
                    view.setOnLongClickListener { true }
                    text = mAuthorTitleList[position].name
                }
            }.apply { attach() }

            ivMore.setOnClickListener {
                showMore()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            authorTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mAuthorTitleList.apply {
                    clear()
                    list?.let { addAll(it) }
                }

                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mAuthorTitleList.size
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
    }
    private fun _navigateToItem(position:Int){
        mBinding.tabLayout.post {
            mBinding.tabLayout.getTabAt(position)?.select()
        }
    }
    val adapter by lazy { MoreItemAdapter() }
    private fun showMore() {
        val mMoreDialog = MaterialDialog(requireContext())
            .cancelable(true)
            .cancelOnTouchOutside(true)
            .cornerRadius(6f)
            .customView(R.layout.pop_select_str)
            .lifecycleOwner(this)
        mMoreDialog.getCustomView().run {
            val rv = this.findViewById<RecyclerView>(R.id.recyclerView)
            rv.layoutManager = FlexboxLayoutManager(requireContext())
            adapter.apply {
               val titleList= mAuthorTitleList.map { it.name }
                setList(titleList)
                setOnItemClickListener { _, _, position ->
                    _navigateToItem(position)
                    mMoreDialog.dismiss()
                }
            }
            rv.adapter = adapter
        }
        mMoreDialog.show()
    }
}