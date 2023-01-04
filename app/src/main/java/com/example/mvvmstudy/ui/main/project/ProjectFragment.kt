package com.example.mvvmstudy.ui.main.project

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.btpj.lib_base.ext.toHtml
import com.btpj.lib_base.utils.ToastUtil
import com.example.mvvmstudy.R
import com.example.mvvmstudy.base.BaseFragment
import com.example.mvvmstudy.data.bean.ProjectTitle
import com.example.mvvmstudy.databinding.FragmentViewpagerBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayoutMediator

class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    /** TabLayout的标题集合 */
    private val mProjectTitleList = ArrayList<ProjectTitle>()
    private val mTitleList = ArrayList<String>()

    private lateinit var mTabLayoutMediator: TabLayoutMediator

    private lateinit var mFragmentStateAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initView() {
        mFragmentStateAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return mTitleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return if (position == 0) ProjectChildFragment.newInstance(true)
                else ProjectChildFragment.newInstance(categoryId = mProjectTitleList[position - 1].id)
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
                    text = mTitleList[position]
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
            projectTitleListLiveData.observe(viewLifecycleOwner) { list ->
                mBinding.showLoading = false
                mProjectTitleList.apply {
                    clear()
                    addAll(list ?: ArrayList())
                }

                mTitleList.apply {
                    clear()
                    mTitleList.add("最新项目")
                }
                list?.forEach { projectTitle ->
                    mTitleList.add(projectTitle.name.toHtml().toString())
                }
                mFragmentStateAdapter.notifyDataSetChanged()
                // 这里的方案是直接缓存所有加载过的子Fragment然后让子Fragment懒加载数据体验更好
                mBinding.viewPager2.offscreenPageLimit = mTitleList.size
            }
        }
    }

    private fun navigateToItem(position:Int){
        mBinding.tabLayout.post {
            mBinding.tabLayout.getTabAt(position)?.select()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabLayoutMediator.detach()
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
                setList(mTitleList)
                setOnItemClickListener { _, _, position ->
                    navigateToItem(position)
                    mMoreDialog.dismiss()
                }
            }
            rv.adapter = adapter
        }
        mMoreDialog.show()
    }

}