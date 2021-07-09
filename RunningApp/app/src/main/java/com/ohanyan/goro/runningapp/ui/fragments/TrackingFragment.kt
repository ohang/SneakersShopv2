package com.ohanyan.goro.runningapp.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ohanyan.goro.runningapp.R
import com.ohanyan.goro.runningapp.db.Run
import com.ohanyan.goro.runningapp.other.Constants.ACTION_PAUSE_SERVICE
import com.ohanyan.goro.runningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.ohanyan.goro.runningapp.other.Constants.ACTION_STOP_SERVICE
import com.ohanyan.goro.runningapp.other.Constants.MAP_ZOOM
import com.ohanyan.goro.runningapp.other.Constants.POLYLINE_COLOR
import com.ohanyan.goro.runningapp.other.Constants.POLYLINE_WIDTH
import com.ohanyan.goro.runningapp.other.TrackingUtility
import com.ohanyan.goro.runningapp.services.Polyline
import com.ohanyan.goro.runningapp.services.TracingService
import com.ohanyan.goro.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()
    private var curTimeInMills=0L
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var map: GoogleMap? = null

    private var menu: Menu?=null

    private var weight=80f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        btnFinishRun.setOnClickListener{
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()
        }
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
            addAllPolylines()

        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TracingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TracingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
     TracingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
         curTimeInMills=it
         val formattedTime =TrackingUtility.getFormattedStopWatchTime(curTimeInMills,true)
         tvTimer.text =formattedTime

     })

    }

    private fun toggleRun() {
        if (isTracking) {
            menu?.getItem(0)?.isVisible =true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu,menu)
        this.menu=menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (curTimeInMills>0L){
            this.menu?.getItem(0)?.isVisible=true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miCancelTracking ->{
                 showCancelTrackingDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showCancelTrackingDialog(){
        val dialog= MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
                .setTitle("cancel the run")
                .setMessage("Are you sure to cancel?")
                .setIcon(R.drawable.ic_settings)
                .setPositiveButton("yes"){_,_->
                    stoprun()

                }

                .setNegativeButton("No"){dialoginterface, _ ->
                    dialoginterface.cancel()
                }
                .create()
        dialog.show()
    }

    private fun stoprun(){
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = "Stop"
            menu?.getItem(0)?.isVisible=true
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            pathPoints.last().last(),
                            MAP_ZOOM
                    )
            )
        }
    }

    private fun zoomToSeeWholeTrack(){
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints){
            for (pos in polyline){
                bounds.include(pos)
            }
        }

        map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        mapView.width,
                        mapView.height,
                        (mapView.height*0.005f).toInt()

                )
        )

    }


    private fun endRunAndSaveToDb(){
        map?.snapshot { bmp->
            var distanceInMeters = 0
            for (polyline in pathPoints){
                distanceInMeters +=TrackingUtility.calculatePolylineLength(polyline).toInt()

            }
             val avgSpeed = round((distanceInMeters/1000f)/(curTimeInMills/1000f/60/60)*10)/10f

            val dateTimeStamp=Calendar.getInstance().timeInMillis
            val caloriesBurned=((distanceInMeters/1000f)*weight).toInt()
            val run = Run(bmp,dateTimeStamp,avgSpeed,distanceInMeters,curTimeInMills,caloriesBurned)

            viewModel.insertRun(run)
            Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Run Saved successfully",
                    Snackbar.LENGTH_LONG
            ).show()
             stoprun()

        }
    }
    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .add(preLastLatLng)
                    .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
            Intent(requireContext(), TracingService::class.java).also {
                it.action = action
                requireContext().startService(it)
            }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}