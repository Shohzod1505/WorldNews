package ru.astondevs.worldnews.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.presentation.common.adapter.NewsAdapter

fun toolbarSetting(activity: FragmentActivity?, title: String, flag: Boolean = false, id: Int = R.id.toolbar) {
    val toolbar = activity?.findViewById<MaterialToolbar>(id)
    toolbar?.title = title
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(flag)
    activity.supportActionBar?.show()
    toolbar?.setNavigationIconTint(activity.getColor(R.color.M3_sys_light_background))
    if (flag) {
        toolbar?.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }
    if (id == R.id.toolbar) {
        activity.window?.statusBarColor = activity.getColor(R.color.M3_ref_primary_primary60)
    }
}

fun toolbarHide(activity: FragmentActivity?, id: Int) {
    val toolbar = activity?.findViewById<MaterialToolbar>(id)
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    activity.supportActionBar?.hide()
}

fun initRecycler(context: Context, recycler: RecyclerView, adapter: NewsAdapter?) {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    recycler.layoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )
    recycler.adapter = adapter
    recycler.addItemDecoration(divider)
}
