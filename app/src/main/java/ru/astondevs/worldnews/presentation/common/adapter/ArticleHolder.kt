package ru.astondevs.worldnews.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.ItemNewsBinding

class ArticleHolder(
    private val actionListener: NewsActionListener,
    private val glide: RequestManager,
    private val binding: ItemNewsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(article: NewsItem.Article) {
        with(binding) {

            tvSources.text = article.source?.name
            tvText.text = article.content

            val logo = when(article.source?.name) {
                "BBC News" -> R.drawable.logo_bbc
                "Fox News" -> R.drawable.logo_foxnews
                "The New York Times" -> R.drawable.logo_thenewyorktimes
                "CBC News" -> R.drawable.logo_cnbc
                "CNN" -> R.drawable.logo_cnn
                "Bloomberg" -> R.drawable.logo_bloomberg
                "Daily Mail" -> R.drawable.logo_dailymail
                else -> R.drawable.logo_placeholder
            }

            glide
                .load(logo)
                .error(R.drawable.photo_placeholder)
                .into(ivLogo)

            glide
                .load(article.urlToImage)
                .error(R.drawable.photo_placeholder)
                .placeholder(R.drawable.photo_placeholder)
                .into(ivPhoto)

            root.setOnClickListener {
                actionListener.onArticleAction(article)
            }

        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            actionListener: NewsActionListener,
        ): ArticleHolder = ArticleHolder(
            binding = ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            actionListener = actionListener,
        )
    }

}
