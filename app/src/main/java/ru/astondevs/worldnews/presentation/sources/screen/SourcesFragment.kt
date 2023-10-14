package ru.astondevs.worldnews.presentation.sources.screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.FragmentSourcesBinding
import ru.astondevs.worldnews.presentation.common.adapter.NewsActionListener
import ru.astondevs.worldnews.presentation.common.adapter.NewsAdapter
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterSource
import ru.astondevs.worldnews.presentation.sources.SourcesFragmentNavigationListener
import ru.astondevs.worldnews.presentation.sources.viewmodel.SourcesViewModel
import ru.astondevs.worldnews.utils.initRecycler
import ru.astondevs.worldnews.utils.toolbarSetting
import javax.inject.Inject

class SourcesFragment : DaggerFragment(R.layout.fragment_sources) {
    private val binding by viewBinding(FragmentSourcesBinding::bind)

    private var sourcesFragmentNavigationListener: SourcesFragmentNavigationListener? = null
    private var newsAdapter: NewsAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: SourcesViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SourcesFragmentNavigationListener) {
            sourcesFragmentNavigationListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetting(activity, "Sources")
        initAdapter()
        swiperRefresh()

        with(binding) {

        }
    }

    private fun initAdapter() {

        newsAdapter = NewsAdapter(
            glide = Glide.with(this@SourcesFragment),
            newsActionListener =  object : NewsActionListener {
                override fun onArticleAction(article: NewsItem.Article) {}
                override fun onSourcesAction(source: NewsItem.Source) {
                    sourcesFragmentNavigationListener?.goToSourcesNewsFragment(source.id)
                }
            })
        initRecycler(requireContext(), binding.list.recyclerView, newsAdapter)

        lifecycleScope.launch {
            val sourcesList = async {
                viewModel.getSources().sources
            }.await()
            newsAdapter?.sourceSubmitList(sourcesList?.toListAdapterSource()?.toMutableList())
        }
    }

    private fun swiperRefresh() {
        swipeRefreshLayout = binding.list.swipeView

        swipeRefreshLayout?.setOnRefreshListener {
            newsAdapter?.articleSubmitList(emptyList<NewsItem.Article>().toMutableList())
            lifecycleScope.launch {
                val sourcesList = async {
                    viewModel.getSources().sources
                }.await()
                newsAdapter?.sourceSubmitList(sourcesList?.toListAdapterSource()?.toMutableList())
                swipeRefreshLayout?.isRefreshing = false
            }
        }
    }

    companion object {
        const val FRAGMENT_SOURCES_TAG = "FRAGMENT_SOURCES_TAG"

        fun newInstance(): SourcesFragment = SourcesFragment()
    }

}
