package ru.astondevs.worldnews.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.ItemSourcesBinding

class SourceHolder(
    private val binding: ItemSourcesBinding,
    private val glide: RequestManager,
    private val actionListener: NewsActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(source: NewsItem.Source) {
        with(binding) {

            tvSources.text = source.name

            val category = "${source.category}".replaceFirstChar { it.uppercase() }
            val country = source.country?.uppercase()
            tvText.text = "$category | $country"

            val logo = when(source.name) {
                "BBC News" -> R.drawable.logo_bbc
                "Bloomberg" -> R.drawable.logo_bloomberg
                "CBC News" -> R.drawable.logo_cnbc
                "CNN" -> R.drawable.logo_cnn
                "Daily Mail" -> R.drawable.logo_dailymail
                "Fox News" -> R.drawable.logo_foxnews
                "The New York Times" -> R.drawable.logo_thenewyorktimes
                else -> R.drawable.logo_placeholder
            }

            glide
                .load(logo)
                .error(R.drawable.photo_placeholder)
                .into(ivLogo)

            root.setOnClickListener {
                actionListener.onSourcesAction(source)
            }

        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            newsActionListener: NewsActionListener,
        ): SourceHolder = SourceHolder(
            binding = ItemSourcesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            actionListener = newsActionListener,
        )
    }

}
