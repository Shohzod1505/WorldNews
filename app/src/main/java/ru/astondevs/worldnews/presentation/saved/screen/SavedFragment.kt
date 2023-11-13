package ru.astondevs.worldnews.presentation.saved.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.data.datasource.remote.response.Article
import ru.astondevs.worldnews.databinding.FragmentSavedBinding
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.NewsActionListener
import ru.astondevs.worldnews.presentation.common.adapter.NewsAdapter
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle
import ru.astondevs.worldnews.presentation.common.detail.DetailFragmentNavigationListener
import ru.astondevs.worldnews.presentation.saved.viewmodel.SavedViewModel
import ru.astondevs.worldnews.utils.SharedPreferences.APP_PREFERENCES
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_END_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_LANGUAGE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_START_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_TYPE
import ru.astondevs.worldnews.utils.initRecycler
import ru.astondevs.worldnews.utils.toolbarSetting
import javax.inject.Inject

class SavedFragment : DaggerFragment(R.layout.fragment_saved) {

    private val binding by viewBinding(FragmentSavedBinding::bind)

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

    private val viewModel: SavedViewModel by viewModels {
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

        preferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        filter = preferences?.getString(FILTER_TYPE, null)
        language = preferences?.getString(FILTER_LANGUAGE, null)
        startDate = preferences?.getString(FILTER_START_DATE, null)
        endDate = preferences?.getString(FILTER_END_DATE, null)

        toolbarSetting(activity, "Saved")
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
            glide = Glide.with(this@SavedFragment),
            newsActionListener =  object : NewsActionListener {
                override fun onArticleAction(article: NewsItem.Article) {
                    detailFragmentNavigationListener?.goToDetailFragment(article.title)
                }
                override fun onSourcesAction(source: NewsItem.Source) {}
            })
        initRecycler(requireContext(), binding.list.recyclerView, newsAdapter)

        lifecycleScope.launch {
            articleList = async {
                if (filter != null || language != null || startDate != null || endDate != null) {
                    viewModel.getFilter("apple", "", 10, filter, language, startDate, endDate).articles
                } else {
                    viewModel.getArticle("apple", 10).articles
                }
            }.await()?.toListAdapterArticle() as MutableList<NewsItem.Article>
            newsAdapter?.articleSubmitList(articleList.toMutableList())
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
                            viewModel.loadFilterNews("apple", "", 10, filter, language, startDate, endDate)
                            articleList = newList
                        } else {
                            viewModel.loadNews("apple", 10)
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
                        viewModel.getFilter("apple", "", 10, filter, language, startDate, endDate).articles
                    } else {
                        viewModel.getArticle("apple", 10).articles
                    }
                }.await()
                newsAdapter?.articleSubmitList(articleList?.toListAdapterArticle()?.toMutableList())
                swipeRefreshLayout?.isRefreshing = false
            }
        }
    }

    companion object {
        const val FRAGMENT_SAVED_TAG = "FRAGMENT_SAVED_TAG"

        fun newInstance(): SavedFragment = SavedFragment()
    }

}
