package ru.astondevs.worldnews.presentation.main.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerAppCompatActivity
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.ActivityMainBinding
import ru.astondevs.worldnews.presentation.common.adapter.NewsItem
import ru.astondevs.worldnews.presentation.common.detail.screen.DetailFragment
import ru.astondevs.worldnews.presentation.common.detail.DetailFragmentNavigationListener
import ru.astondevs.worldnews.presentation.common.error.ErrorFragmentNavigationListener
import ru.astondevs.worldnews.presentation.common.error.screen.ErrorFragment
import ru.astondevs.worldnews.presentation.common.filter.screen.FilterFragment
import ru.astondevs.worldnews.presentation.headlines.screen.HeadlinesFragment
import ru.astondevs.worldnews.presentation.saved.screen.SavedFragment
import ru.astondevs.worldnews.presentation.sources.screen.SourcesFragment
import ru.astondevs.worldnews.presentation.sources.SourcesFragmentNavigationListener
import ru.astondevs.worldnews.presentation.sources.news.screen.SourcesNewsFragment
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_END_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_LANGUAGE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_START_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_TYPE

class MainActivity : DaggerAppCompatActivity(),
    SourcesFragmentNavigationListener,
    DetailFragmentNavigationListener,
    ErrorFragmentNavigationListener {

    private var binding: ActivityMainBinding? = null
    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = this.getSharedPreferences(ru.astondevs.worldnews.utils.SharedPreferences.APP_PREFERENCES, Context.MODE_PRIVATE)

        preferences?.edit {
            putString(FILTER_TYPE, null)
            putString(FILTER_LANGUAGE, null)
            putString(FILTER_START_DATE, null)
            putString(FILTER_END_DATE, null)
            commit()
        }

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        var fragmentTag: String = HeadlinesFragment.FRAGMENT_HEADLINES_TAG
        var fragment: Fragment = HeadlinesFragment.newInstance()
        if (supportFragmentManager.findFragmentByTag(fragmentTag) == null) {
            supportFragmentManager.beginTransaction().run {
                add(R.id.fragment_container, fragment, fragmentTag)
                commit()
            }
        }

        binding?.run {
            bnv.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.item_headlines -> {
                        fragmentTag = HeadlinesFragment.FRAGMENT_HEADLINES_TAG
                        fragment = HeadlinesFragment.newInstance()
                    }
                    R.id.item_saved -> {
                        fragmentTag = SavedFragment.FRAGMENT_SAVED_TAG
                        fragment = SavedFragment.newInstance()
                    }
                    R.id.item_sources -> {
                        fragmentTag = SourcesFragment.FRAGMENT_SOURCES_TAG
                        fragment = SourcesFragment.newInstance()
                    }
                }
                replaceFragment(fragment, fragmentTag)
                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_search -> {
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.item_filter -> {
                openFilter(FilterFragment.newInstance(), FilterFragment.FRAGMENT_FILTER_TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction().run {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            commit()
        }
    }

    private fun openFilter(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction().run {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            addToBackStack(fragmentTag)
            commit()
        }
    }

    override fun goToSourcesNewsFragment(sources: String?) {
        val fragment = SourcesNewsFragment.newInstance(sources)
        val fragmentTag = SourcesNewsFragment.SOURCES_NEWS_FRAGMENT_TAG

        supportFragmentManager.beginTransaction().run {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            addToBackStack(fragmentTag)
            commit()
        }
    }

    override fun goToDetailFragment(article: String?) {
        val fragment = DetailFragment.newInstance(article)
        val fragmentTag = DetailFragment.DETAIL_FRAGMENT_TAG

        supportFragmentManager.beginTransaction().run {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            addToBackStack(fragmentTag)
            commit()
        }
    }

    override fun goToErrorFragment() {
        val fragment = ErrorFragment.newInstance()
        val fragmentTag = ErrorFragment.ERROR_FRAGMENT_TAG

        supportFragmentManager.beginTransaction().run {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            addToBackStack(fragmentTag)
            commit()
        }
    }

}
