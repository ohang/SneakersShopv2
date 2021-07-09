package com.ohanyan.goro.runningapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ohanyan.goro.runningapp.R
import com.ohanyan.goro.runningapp.ui.viewmodels.MainViewModel
import com.ohanyan.goro.runningapp.ui.viewmodels.StatisticViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment:Fragment(R.layout.fragment_settings) {
    private val viewModel: StatisticViewModel by viewModels()


}