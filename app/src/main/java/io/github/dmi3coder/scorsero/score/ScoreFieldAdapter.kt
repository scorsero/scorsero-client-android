package io.github.dmi3coder.scorsero.score

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R

/**
 * Created by dim3coder on 8:23 AM 7/19/17.
 */
class ScoreFieldAdapter(val fieldList: List<Triple<String,String,Any?>>?) : RecyclerView.Adapter<ScoreFieldHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreFieldHolder {
    val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_score_field,parent,false)
    return ScoreFieldHolder(view)
  }

  override fun onBindViewHolder(holder: ScoreFieldHolder?, position: Int) {
  }

  override fun getItemCount(): Int = 10
}

class ScoreFieldHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}