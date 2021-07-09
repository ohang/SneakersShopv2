package com.ohanyan.goro.runningapp.repositories

import com.ohanyan.goro.runningapp.db.Run
import com.ohanyan.goro.runningapp.db.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(val runDao:RunDao) {

    suspend fun insertRun(run: Run) = runDao.insert(run)

    suspend fun deleteRun(run: Run) = runDao.delete(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMills() = runDao.getAllRunsSortedByTimeInMills()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    fun getTotalCaloriesBurned() = runDao.getAllTotalCaloriesBurned()

    fun getTotalTimeInMills() = runDao.getAllTotalTimeInMills()

    fun getTotalDistance() = runDao.getAllTotalDistance()
}

