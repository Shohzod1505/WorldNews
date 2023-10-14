package ru.astondevs.worldnews.presentation.common.filter.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import ru.astondevs.worldnews.R
import ru.astondevs.worldnews.databinding.FragmentFilterBinding
import ru.astondevs.worldnews.utils.toolbarSetting
import java.util.Calendar
import androidx.core.util.Pair
import androidx.core.view.MenuProvider
import com.google.android.material.datepicker.CalendarConstraints
import ru.astondevs.worldnews.utils.SharedPreferences.APP_PREFERENCES
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_END_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_LANGUAGE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_START_DATE
import ru.astondevs.worldnews.utils.SharedPreferences.FILTER_TYPE
import java.text.SimpleDateFormat
import java.util.Locale

class FilterFragment : Fragment(R.layout.fragment_filter), MenuProvider {
    private val binding by viewBinding(FragmentFilterBinding::bind)

    private var preferences: SharedPreferences? = null
    private var filter: String? = null
    private var language: String? = null
    private var fromDate: String? = null
    private var toDate: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        toolbarSetting(activity, "Filters", true)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        with(binding) {
            val chips = arrayOf(chipPopular, chipNew, chipRelevant)
            chips.forEachIndexed { index, chip ->
                chip.setOnClickListener {
                    chips.forEach { it.isChecked = false }
                    chip.isChecked = true
                }
                chip.setOnCheckedChangeListener { _, isChecked ->
                    when (index) {
                        0 -> {
                            stateChecked(chip, 24, isChecked)
                            filter = "popularity"
                        }
                        1 -> {
                            stateChecked(chip, 32, isChecked)
                            filter = "publishedAt"
                        }
                        2 -> {
                            stateChecked(chip, 12, isChecked)
                            filter = "relevancy"
                        }
                    }
                }
            }

            ivCalendar.setOnClickListener {
                showDatePicker(tvChooseDate, ivCalendar)
            }

            val languageChips = arrayOf(chipRussian, chipEnglish, chipDeutsch)
            languageChips.forEachIndexed { index, chip ->
                chip.setOnCheckedChangeListener { _, _ ->
                    when (index) {
                        0 -> {
                            language = "ru"
                        }
                        1 -> {
                            language = "en"
                        }
                        2 -> {
                            language = "de"
                        }
                    }
                }
            }
        }

    }

    private fun showDatePicker(date: TextView, ivCalendar: ImageView) {

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val startYear = currentYear - 7
        val endYear = currentYear + 7
        val start = Calendar.getInstance().apply { set(startYear, 0, 1) }.timeInMillis
        val end = Calendar.getInstance().apply { set(endYear, 11, 31) }.timeInMillis

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setStart(start)
                        .setEnd(end)
                        .build()
                )
                .setTheme(R.style.DatePicker)
                .setSelection(
                    Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = SimpleDateFormat("MMM d", Locale.getDefault()).format(selection.first)
            val endDate = SimpleDateFormat("MMM d", Locale.getDefault()).format(selection.second)

            date.text = if (startDate == endDate) {
                "$startDate, $currentYear"
            } else {
                "$startDate-$endDate, $currentYear"
            }

            fromDate = startDate
            toDate = endDate

            ivCalendar.setBackgroundResource(R.drawable.rounded_shape_selected)
            ivCalendar.setImageResource(R.drawable.icon_calendar_selected)
            date.setTextColor(requireActivity().getColor(R.color.M3_sys_light_primary))
        }

        dateRangePicker.addOnNegativeButtonClickListener {
            ivCalendar.setBackgroundResource(R.drawable.date_selector)
            ivCalendar.setImageResource(R.drawable.icon_calendar_normal)
            date.text = "Choose date"
            date.setTextColor(requireActivity().getColor(R.color.M3_sys_light_outline))
        }

        dateRangePicker.show(parentFragmentManager, "Datepicker")
    }

    private fun stateChecked(chip: Chip, size: Int, isChecked: Boolean) {
        if (isChecked) {
            chip.chipStartPadding = size * resources.displayMetrics.density
            chip.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        } else {
            chip.chipStartPadding = 10 * resources.displayMetrics.density
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.toolbar_filter_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.item_confirm -> {
                preferences?.edit {
                    putString(FILTER_TYPE, filter)
                    putString(FILTER_LANGUAGE, language)
                    putString(FILTER_START_DATE, fromDate)
                    putString(FILTER_END_DATE, toDate)
                    commit()
                }
                activity?.onBackPressed()
            }
        }
        return true
    }

    companion object {
        const val FRAGMENT_FILTER_TAG = "FRAGMENT_FILTER_TAG"

        fun newInstance(): FilterFragment = FilterFragment()
    }

}
