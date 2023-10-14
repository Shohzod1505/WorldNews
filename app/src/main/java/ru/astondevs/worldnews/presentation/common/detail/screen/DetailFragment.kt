package ru.astondevs.worldnews.presentation.common.detail.screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.FragmentDetailBinding
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.adapter.toListAdapterArticle
import ru.astondevs.worldnews.presentation.common.detail.viewmodel.DetailViewModel
import ru.astondevs.worldnews.presentation.saved.viewmodel.SavedViewModel
import ru.astondevs.worldnews.utils.toolbarHide
import ru.astondevs.worldnews.utils.toolbarSetting
import javax.inject.Inject

class DetailFragment : DaggerFragment(R.layout.fragment_detail), MenuProvider {
    private val binding by viewBinding(FragmentDetailBinding::bind)

    private var isSaved = false

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(DETAIL_FRAGMENT_ARTICLE_KEY)

        toolbarHide(activity, R.id.toolbar)
        toolbarSetting(activity, query.toString(), true, R.id.collapsing_toolbar)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        activity?.window?.statusBarColor = Color.TRANSPARENT

        with(binding) {
            lifecycleScope.launch {
                val article = getArticle(query)

                Glide.with(this@DetailFragment)
                    .load(article?.urlToImage)
                    .into(ivPhoto)
                tvTitle.text = article?.title
                tvDate.text = article?.publishedAt
                tvSource.text = article?.source?.name
                tvNews.text = article?.content
            }

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.toolbar_save_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.item_save -> {
                if (!isSaved) {
                    isSaved = true
                    menuItem.setIcon(R.drawable.icon_saved)
                } else {
                    isSaved = false
                    menuItem.setIcon(R.drawable.icon_unsaved)
                }
            }
        }
        return true
    }

    private suspend fun getArticle(query: String?): NewsItem.Article? {
        return withContext(Dispatchers.IO) {
            val articleList = viewModel.getNews(query).articles
            articleList?.toListAdapterArticle()?.firstOrNull()
        }
    }

    companion object {
        const val DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG"
        private const val DETAIL_FRAGMENT_ARTICLE_KEY = "DETAIL_FRAGMENT_ARTICLE_KEY"

        fun newInstance(article: String?): DetailFragment = DetailFragment().apply {
            arguments = bundleOf(DETAIL_FRAGMENT_ARTICLE_KEY to article)
        }
    }

}
