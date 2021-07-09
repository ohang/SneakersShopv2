package com.ohanyan.goro.runningapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ohanyan.goro.runningapp.repositories.MainRepository

class StatisticViewModel @ViewModelInject constructor(val mainRepository: MainRepository):ViewModel() {

}