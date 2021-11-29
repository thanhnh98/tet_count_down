package com.thanh_nguyen.test_count_down.app.presentation.ui.main.calendar

import android.os.Bundle
import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.thanh_nguyen.test_count_down.R
import com.thanh_nguyen.test_count_down.common.base.mvvm.fragment.BaseFragmentMVVM
import com.thanh_nguyen.test_count_down.databinding.FragmentCalendarBinding
import kodeinViewModel

class CalendarFragment: BaseFragmentMVVM<FragmentCalendarBinding, CalendarViewModel>() {
    override val viewModel: CalendarViewModel by kodeinViewModel()

    override fun inflateLayout(): Int = R.layout.fragment_calendar

    override fun onViewCreatedX(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedX(view, savedInstanceState)

    }
}