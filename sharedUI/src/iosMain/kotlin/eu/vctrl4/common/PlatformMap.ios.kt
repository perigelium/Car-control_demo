package eu.vctrl4.common

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.viewinterop.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*
import kotlinx.cinterop.*
import platform.CoreLocation.*
import platform.MapKit.*
import platform.UIKit.*
import platform.darwin.*

private class OsmTileOverlay(uRLTemplate: String) : MKTileOverlay(uRLTemplate = uRLTemplate)

private class MapKitDelegate : NSObject(), MKMapViewDelegateProtocol {
	override fun mapView(
		mapView: MKMapView,
		rendererForOverlay: MKOverlayProtocol
	                    ): MKOverlayRenderer {
		return when (rendererForOverlay) {
			is MKTileOverlay -> {
				MKTileOverlayRenderer(overlay = rendererForOverlay)
			}
			is MKPolyline -> {
				MKPolylineRenderer(overlay = rendererForOverlay).apply {
					strokeColor = UIColor.colorWithRed(0.88, green = 0.13, blue = 0.13, alpha = 1.0)
					lineWidth = 4.0
				}
			}
			else -> MKOverlayRenderer(overlay = rendererForOverlay)
		}
	}

	override fun mapView(mapView: MKMapView, viewForAnnotation: MKAnnotationProtocol): MKAnnotationView? {
		if (viewForAnnotation is platform.MapKit.MKUserLocation) return null

		val title = viewForAnnotation.title()
		val reuseId = when (title) {
			"Vehicle" -> "VehicleAnnotationView"
			else -> "TextMarkerAnnotationView"
		}

		if (title == "Vehicle") {
			var annotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(reuseId)
			if (annotationView == null) {
				annotationView = MKAnnotationView(annotation = viewForAnnotation, reuseIdentifier = reuseId)
				annotationView.canShowCallout = true
			} else {
				annotationView.annotation = viewForAnnotation
			}

			val carImage = UIImage.imageNamed("ic_car_blue")
			if (carImage != null) {
				annotationView.image = carImage
			} else {
				val fallbackMarker = MKMarkerAnnotationView(annotation = viewForAnnotation, reuseIdentifier = reuseId)
				fallbackMarker.markerTintColor = UIColor.colorWithRed(0.0, green = 0.33, blue = 0.62, alpha = 1.0)
				fallbackMarker.glyphText = "🚗"
				return fallbackMarker
			}
			return annotationView
		} else {
			var markerView = mapView.dequeueReusableAnnotationViewWithIdentifier(reuseId) as? MKMarkerAnnotationView
			if (markerView == null) {
				markerView = MKMarkerAnnotationView(annotation = viewForAnnotation, reuseIdentifier = reuseId)
				markerView.canShowCallout = true
			} else {
				markerView.annotation = viewForAnnotation
			}

			when (title) {
				"Start" -> {
					markerView.markerTintColor = UIColor.systemGreenColor
					markerView.glyphText = "S"
				}
				"Finish" -> {
					markerView.markerTintColor = UIColor.systemGrayColor
					markerView.glyphText = "F"
				}
			}
			return markerView
		}
	}
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformOsmMap(
	viewState: VehicleTrackViewState, modifier: Modifier
                         ) {
	val mapDelegate = remember { MapKitDelegate() }

	val nativeMapView = remember {
		MKMapView().apply {
			setZoomEnabled(true)
			setScrollEnabled(true)
			setRotateEnabled(true)
			setDelegate(mapDelegate)

			val osmTemplate = "https://tile.openstreetmap.org/{z}/{x}/{y}.png"

			val tileOverlay = OsmTileOverlay(uRLTemplate = osmTemplate).apply {
				minimumZ = 0
				maximumZ = 19 // Limit requests inside OSM's technical map density caps
				setCanReplaceMapContent(canReplaceMapContent = true)
			}

			//addOverlay(tileOverlay, level = MKOverlayLevelAboveLabels)
            addOverlay(tileOverlay, level = MKOverlayLevelAboveRoads)
		}
	}

	DisposableEffect(Unit) {
		onDispose {
			nativeMapView.setDelegate(null)
			val allOverlays = nativeMapView.overlays
			if (allOverlays.isNotEmpty()) nativeMapView.removeOverlays(allOverlays)
			nativeMapView.removeAnnotations(nativeMapView.annotations)
		}
	}

	UIKitView(factory = { nativeMapView }, modifier = modifier, update = { mapView ->

		println("DEBUG: geoPoints size = ${viewState.geoPoints.size}")
		println("DEBUG: isRentFinished = ${viewState.isRentFinished}")
		println("DEBUG: vehiclePosition = ${viewState.vehiclePosition}")
		if (viewState.vehiclePosition != null) {
			println("DEBUG: vehicle X = ${viewState.vehiclePosition.X}, Y = ${viewState.vehiclePosition.Y}")
		}

		// Clear previous overlays (except tile engine) and marker nodes before processing up-states
		val nonTileOverlays = mapView.overlays.filter { it !is MKTileOverlay }
		if (nonTileOverlays.isNotEmpty()) mapView.removeOverlays(nonTileOverlays)
		mapView.removeAnnotations(mapView.annotations)

		var polylineBoundingBox: CValue<MKMapRect>? = null

		if (viewState.geoPoints.isNotEmpty()) {
			memScoped {
				val coordinateArray =
					allocArray<CLLocationCoordinate2D>(viewState.geoPoints.size) { index ->
						val currentPoint = viewState.geoPoints[index]
						this.latitude = currentPoint.lat
						this.longitude = currentPoint.lng
					}

				val polyline = MKPolyline.polylineWithCoordinates(
					coords = coordinateArray, count = viewState.geoPoints.size.toULong()
				                                                 )
				mapView.addOverlay(polyline)
				polylineBoundingBox = polyline.boundingMapRect
			}

			val startPoint = viewState.geoPoints.first()
			val startAnnotation = MKPointAnnotation().apply {
				setCoordinate(CLLocationCoordinate2DMake(startPoint.lat, startPoint.lng))
				setTitle("Start")
			}
			mapView.addAnnotation(startAnnotation)

			if (viewState.isRentFinished) {
				val endPoint = viewState.geoPoints.last()
				val endAnnotation = MKPointAnnotation().apply {
					setCoordinate(CLLocationCoordinate2DMake(endPoint.lat, endPoint.lng))
					setTitle("Finish")
				}
				mapView.addAnnotation(endAnnotation)
			}
		}

		// Camera Management & Bounds Framing
		if (viewState.vehiclePosition != null && viewState.vehiclePosition.Y != null && viewState.vehiclePosition.X != null) {
			val vehicleCoordinate = CLLocationCoordinate2DMake(
				viewState.vehiclePosition.Y,
				viewState.vehiclePosition.X
			                                                  )

			val vehicleAnnotation = MKPointAnnotation().apply {
				setCoordinate(vehicleCoordinate)
				setTitle("Vehicle")
			}
			mapView.addAnnotation(vehicleAnnotation)

			val region = MKCoordinateRegionMakeWithDistance(vehicleCoordinate, 1500.0, 1500.0)
			mapView.setRegion(region, animated = true)

		} else if (polylineBoundingBox != null && viewState.geoPoints.size > 2) {
			mapView.setVisibleMapRect(
				mapRect = polylineBoundingBox,
				edgePadding = UIEdgeInsetsMake(80.0, 80.0, 80.0, 80.0),
				animated = true
			                         )
		}
	})
}
