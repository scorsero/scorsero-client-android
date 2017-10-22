package io.github.dmi3coder.scorsero.data

import android.arch.persistence.room.Entity

/**
 * Created by dim3coder on 3:36 PM 10/22/17.
 */
@Entity
class GeoLocation() {
  var x: Double? = null

  var y: Double? = null


  constructor(x: Double?, y: Double?) : this() {
    this.x = x
    this.y = y
  }

  operator fun component1(): Double? = x
  operator fun component2(): Double? = y
}