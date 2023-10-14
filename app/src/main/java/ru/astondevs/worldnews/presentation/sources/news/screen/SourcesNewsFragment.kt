package ru.astondevs.worldnews.presentation.sources.news.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.FragmentSourcesNewsBinding
import ru.astondevs.worldnews.presentation.common.adapter.NewsActionListener
import ru.astondevs.worldnews.presentation.common.adapter.NewsAdapter
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle
import ru.astondevs.worldnews.presentation.common.detail.DetailFragmentNavigationListener
import ru.astondevs.worldnews.presentation.sources.news.viewmodel.SourcesNewsViewModel
import ru.astondevs.worldnews.utils.initRecycler
import ru.astondevs.worldnews.utils.toolbarSetting
import javax.inject.Inject

class SourcesNewsFragment : DaggerFragment(R.layout.fragment_sources_news) {
    private val binding by viewBinding(FragmentSourcesNewsBinding::bind)

    private var newsAdapter: NewsAdapter? = null
    private var detailFragmentNavigationListener: DetailFragmentNavigationListener? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var articleList: MutableList<NewsItem.Article> = mutableListOf()
    private var newList: MutableList<NewsItem.Article> = mutableListOf()
    private var isLoading = false

    private var preferences: SharedPreferences? = null
    private var filter: String? = null
    private var language: String? = null
    private var startDate: String? = null
    private var endDate: String? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: SourcesNewsViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailFragmentNavigationListener) {
            detailFragmentNavigationListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences(ru.astondevs.worldnews.utils.SharedPreferences.APP_PREFERENCES, Context.MODE_PRIVATE)

        filter = preferences?.getString(ru.astondevs.worldnews.utils.SharedPreferences.FILTER_TYPE, null)
        language = preferences?.getString(ru.astondevs.worldnews.utils.SharedPreferences.FILTER_LANGUAGE, null)
        startDate = preferences?.getString(ru.astondevs.worldnews.utils.SharedPreferences.FILTER_START_DATE, null)
        endDate = preferences?.getString(ru.astondevs.worldnews.utils.SharedPreferences.FILTER_END_DATE, null)

        val title = requireArguments().getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY)

        toolbarSetting(activity, title.toString(), true)
        initAdapter()
        swiperRefresh()
        scroll()
        observeViewModel()

        with(binding) {
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                binding.list.progressbar.visibility = it
            }

            articleResponse.observe(viewLifecycleOwner) {
                newList = it.toMutableList()
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter(
            glide = Glide.with(this@SourcesNewsFragment),
            newsActionListener =  object : NewsActionListener {
                override fun onArticleAction(article: NewsItem.Article) {
                    detailFragmentNavigationListener?.goToDetailFragment(article.title)
                }
                override fun onSourcesAction(source: NewsItem.Source) {}
            })
        initRecycler(requireContext(), binding.list.recyclerView, newsAdapter)

        lifecycleScope.launch {
            val articleList = async {
                if (filter != null || language != null || startDate != null || endDate != null) {
                    viewModel.getFilter("a", arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10, filter, language, startDate, endDate).articles
                } else {
                    viewModel.getNewsSources(arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10).articles
                }
            }.await()
            newsAdapter?.articleSubmitList(articleList?.toListAdapterArticle()?.toMutableList())
        }
    }

    private fun scroll() {
        binding.list.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!isLoading) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && visibleItemCount + firstVisibleItem >= totalItemCount - 5) {
                        isLoading = true

                        if (filter != null || language != null || startDate != null || endDate != null) {
                            viewModel.loadFilterNews("a", arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10, filter, language, startDate, endDate)
                            articleList = newList
                        } else {
                            viewModel.loadNews(arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10)
                            articleList.addAll(newList)
                        }

                        newsAdapter?.articleSubmitList(articleList.toMutableList())
                        isLoading = false
                    }
                }
            }
        })
    }

    private fun swiperRefresh() {
        swipeRefreshLayout = binding.list.swipeView

        swipeRefreshLayout?.setOnRefreshListener {
            newsAdapter?.articleSubmitList(emptyList<NewsItem.Article>().toMutableList())
            lifecycleScope.launch {
                val articleList = async {
                    if (filter != null || language != null || startDate != null || endDate != null) {
                        viewModel.getFilter("a", arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10, filter, language, startDate, endDate).articles
                    } else {
                        viewModel.getNewsSources(arguments?.getString(SOURCES_NEWS_FRAGMENT_SOURCES_KEY), 10).articles
                    }
                }.await()
                newsAdapter?.articleSubmitList(articleList?.toListAdapterArticle()?.toMutableList())
                swipeRefreshLayout?.isRefreshing = false
            }
        }
    }

    companion object {
        const val SOURCES_NEWS_FRAGMENT_TAG = "SOURCES_NEWS_FRAGMENT_TAG"
        private const val SOURCES_NEWS_FRAGMENT_SOURCES_KEY = "SOURCES_NEWS_FRAGMENT_SOURCES_KEY"

        fun newInstance(sources: String?): SourcesNewsFragment = SourcesNewsFragment().apply {
            arguments = bundleOf(SOURCES_NEWS_FRAGMENT_SOURCES_KEY to sources)
        }
    }

}
