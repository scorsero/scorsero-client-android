package io.github.dmi3coder.scorsero.data

import android.arch.persistence.room.TypeConverter

/**
 * Created by dim3coder on 3:49 PM 10/22/17.
 */

class RoomConverters {

  @TypeConverter
  fun geopositionFromString(string: String): GeoLocation {
    var position = string.split(",").map { it.toDoubleOrNull() }
    return GeoLocation(position[0], position[1])
  }

  @TypeConverter
  fun stringToGeoposition(geoLocation: GeoLocation): String {
    val (x, y) = geoLocation
    return "$x,$y"
  }
}
