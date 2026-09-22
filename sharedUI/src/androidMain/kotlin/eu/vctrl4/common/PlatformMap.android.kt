package eu.vctrl4.common

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.*
import androidx.core.content.res.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.LocalLifecycleOwner
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.R
import eu.vctrl4.business.datasource.storage.entities.VehiclePosition
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*
import eu.vctrl4.theme.*
import org.osmdroid.config.*
import org.osmdroid.tileprovider.tilesource.*
import org.osmdroid.util.*
import org.osmdroid.views.*
import org.osmdroid.views.overlay.*
import java.io.*

@Composable
actual fun PlatformOsmMap(
	viewState: VehicleTrackViewState, modifier: Modifier
                         ) {
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		Configuration.getInstance().userAgentValue = context.packageName
		val cacheDir = File(context.cacheDir?.absolutePath, "osmdroid")
		Configuration.getInstance().osmdroidTileCache = cacheDir
	}

	val mapView = remember {
		MapView(context).apply {
			setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
			setBuiltInZoomControls(false)
			setMultiTouchControls(true)
			controller.setZoom(5.0)
			controller.setCenter(GeoPoint(49.843, 9.9021))

			addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
				this.invalidate()
			}

			setOnTouchListener { view, event ->
				view.parent?.requestDisallowInterceptTouchEvent(true)
				false
			}
		}
	}

	val lifecycleOwner = LocalLifecycleOwner.current
	DisposableEffect(lifecycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			when (event) {
				Lifecycle.Event.ON_RESUME -> mapView.onResume()
				Lifecycle.Event.ON_PAUSE -> mapView.onPause()
				else -> {}
			}
		}
		lifecycleOwner.lifecycle.addObserver(observer)
		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
			mapView.onPause()
			mapView.onDetach()
		}
	}

	fun updateMapLayers(
		view: MapView, points: List<GeoPoint>, position: VehiclePosition?, isRentFinished: Boolean
	                   ) {
		view.overlayManager.clear()

		if (points.isNotEmpty()) {
			val line = Polyline().apply {
				setPoints(points)
				outlinePaint.color = Colors.cl_e02020.toArgb()
				setOnClickListener { _, _, _ ->/*Do something on track click*/
					false
				}
			}
			view.overlayManager.add(line)

			val startMarker = Marker(view).apply {
				this.position = points.first()
				setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
				setTextIcon("Start")
				title = "Start"
			}
			view.overlayManager.add(startMarker)

			if (isRentFinished) {
				val endMarker = Marker(view).apply {
					this.position = points.last()
					setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
					setTextIcon("Finish")
					title = "Finish"
				}
				view.overlayManager.add(endMarker)

				if (points.isNotEmpty()) { // size > 3
					var minLat = Double.MAX_VALUE
					var maxLat = Double.MIN_VALUE
					var minLong = Double.MAX_VALUE
					var maxLong = Double.MIN_VALUE

					for (point in points) {
						if (point.latitude < minLat) minLat = point.latitude
						if (point.latitude > maxLat) maxLat = point.latitude
						if (point.longitude < minLong) minLong = point.longitude
						if (point.longitude > maxLong) maxLong = point.longitude
					}

					val boundingBox = BoundingBox(maxLat, maxLong, minLat, minLong)
					view.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true)
				}
			}
		}

		if (position != null) {
			val vehiclePoint = GeoPoint(position.Y!!, position.X!!)
			view.controller.setZoom(15.0)
			view.controller.setCenter(vehiclePoint)

			val drawable = ResourcesCompat.getDrawable(
				view.resources, R.drawable.ic_car_blue, null
			                                          )

			val vehicleMarker = Marker(view).apply {
				this.position = vehiclePoint
				setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
				icon = drawable
				title = Res.string.vehicle_short.asState
				setOnMarkerClickListener { _, _ ->/*Do something on vehicle click*//**/
					true
				}
			}
			view.overlayManager.add(vehicleMarker)
		}

		view.invalidate()
	}

	AndroidView(factory = { mapView }, modifier = modifier, update = { view ->
		val geoPoints = viewState.geoPoints.map {
			GeoPoint(
				it.lat, it.lng
			        )
		}
		updateMapLayers(
			view = view,
			points = geoPoints,
			position = viewState.vehiclePosition,
			isRentFinished = viewState.isRentFinished,
		               )
	})
}

