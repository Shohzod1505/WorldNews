package ru.astondevs.worldnews.presentation.common.error.screen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.FragmentErrorBinding
import ru.astondevs.worldnews.presentation.common.detail.screen.DetailFragment

class ErrorFragment : Fragment(R.layout.fragment_error) {
    private val binding by viewBinding(FragmentErrorBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }

    companion object {
        const val ERROR_FRAGMENT_TAG = "ERROR_FRAGMENT_TAG"
        private const val ERROR_FRAGMENT_TYPE_KEY = "ERROR_FRAGMENT_TYPE_KEY"

        fun newInstance(): DetailFragment = DetailFragment()
    }

}
