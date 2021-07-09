package com.ohanyan.goro.runningapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohanyan.goro.runningapp.db.Run
import com.ohanyan.goro.runningapp.other.SortType
import com.ohanyan.goro.runningapp.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(val mainRepository: MainRepository):ViewModel() {

  private  val runsSortedByDate=mainRepository.getAllRunsSortedByDate()
  private  val runSortedByDistance=mainRepository.getAllRunsSortedByDistance()
  private  val runSortedByCalories=mainRepository.getAllRunsSortedByCaloriesBurned()
  private  val runSortedByTimeInMillis=mainRepository.getAllRunsSortedByTimeInMills()
  private  val runSortedByAvgSpeed=mainRepository.getAllRunsSortedByAvgSpeed()

    var runs =MediatorLiveData<List<Run>>()

    var sortType =SortType.DATE

  init {
      runs.addSource(runsSortedByDate){
        result-> if (sortType==SortType.DATE){
         result?.let { runs.value=it }
      }
      }

    runs.addSource(runSortedByDistance){
        result-> if (sortType==SortType.DISTANCE){
      result?.let { runs.value=it }
    }
    }

    runs.addSource(runSortedByCalories){
        result-> if (sortType==SortType.CALORIES_BURNED){
      result?.let { runs.value=it }
    }
    }

    runs.addSource(runSortedByTimeInMillis){
        result-> if (sortType==SortType.RUNNING_TIME){
      result?.let { runs.value=it }
    }
    }

    runs.addSource(runSortedByAvgSpeed){
        result-> if (sortType==SortType.AVG_SPEED){
      result?.let { runs.value=it }
    }
    }



  }


   fun sortRun(sortType: SortType)=when(sortType){
     SortType.DATE->runsSortedByDate.value?.let{ runs.value=it}
     SortType.AVG_SPEED->runSortedByAvgSpeed.value?.let{ runs.value=it}
     SortType.RUNNING_TIME->runSortedByTimeInMillis.value?.let{ runs.value=it}
     SortType.CALORIES_BURNED->runSortedByCalories.value?.let{ runs.value=it}
     SortType.DISTANCE->runSortedByDistance.value?.let{ runs.value=it}


   }.also {

     this.sortType=sortType
   }

    fun insertRun(run: Run)=viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}