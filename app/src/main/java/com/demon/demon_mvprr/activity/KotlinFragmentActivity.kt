package com.demon.demon_mvprr.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.demon.demon_mvprr.R
import com.demon.demon_mvprr.fragment.JavaJsonFragment
import com.demon.demon_mvprr.fragment.KtBeanFragment
import com.demon.mvprr.activity.BaseActivity
import com.demon.mvprr.model.BasePresenterInfc
import kotlinx.android.synthetic.main.activity_fragment.*

class KotlinFragmentActivity : BaseActivity<BasePresenterInfc>() {
    override fun bindLayout(): Int {
        return R.layout.activity_fragment
    }

    override fun initCreate() {
        val list = arrayListOf<Fragment>(JavaJsonFragment(), KtBeanFragment())
        val titles = arrayListOf<String>("Json", "Bean")
        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                return list[p0]
            }

            override fun getCount(): Int {
                return list.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }


}
