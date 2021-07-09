package com.ohanyan.goro.runningapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.nio.channels.FileLock


@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(run:Run)

    @Delete
    suspend fun delete(run: Run)

    @Query("SELECT * FROM running_table ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate():LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeed():LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistance():LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY timeInMills DESC")
    fun getAllRunsSortedByTimeInMills():LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY calories DESC")
    fun getAllRunsSortedByCaloriesBurned():LiveData<List<Run>>

    @Query("SELECT SUM(timeInMills) FROM running_table")
    fun getAllTotalTimeInMills():LiveData<Long>

    @Query("SELECT SUM(calories) FROM running_table")
    fun getAllTotalCaloriesBurned():LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    fun getAllTotalDistance():LiveData<Int>


    @Query("SELECT AVG(avgSpeedInKMH) FROM running_table")
    fun getTotalAvgSpeed():LiveData<Float>









}