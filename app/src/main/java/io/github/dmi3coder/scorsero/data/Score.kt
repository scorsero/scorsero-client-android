package io.github.dmi3coder.scorsero.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by dim3coder on 12:59 PM 7/2/17.
 */
@Entity
class Score() {

  @PrimaryKey(autoGenerate = true)
  var id: Int? = null

  var title: String? = null

  var priority: Int? = null

  var description: String? = null

  @ColumnInfo(name = "creation_date")
  var creationDate: Long? = null

  var completed: Boolean? = null

  @ColumnInfo(name = "completion_date")
  var completionDate: Long? = null

  constructor(id: Int?, title: String?, description: String?, creationDate: Long?,
      completed: Boolean?, completionDate: Long?) : this() {
    this.id = id
    this.title = title
    this.description = description
    this.creationDate = creationDate
    this.completed = completed
    this.completionDate = completionDate
  }
}