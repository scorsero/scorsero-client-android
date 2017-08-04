package io.github.dmi3coder.scorsero.navigation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import kotlinx.android.synthetic.main.item_drawer_option.view.count
import kotlinx.android.synthetic.main.item_drawer_option.view.title

/**
 * Created by dim3coder on 9:06 AM 8/2/17.
 */

class DrawerAdapter : RecyclerView.Adapter<DrawerItemHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrawerItemHolder {
    return DrawerItemHolder(
        LayoutInflater.from(parent!!.context).inflate(R.layout.item_drawer_option, parent, false))
  }

  override fun getItemCount(): Int = 4

  override fun onBindViewHolder(holder: DrawerItemHolder?, position: Int) {
    val view = holder!!.itemView

    val options = view.context.resources.getStringArray(R.array.drawer_options)
    view.title.text = options[position]
    view.count.text = (Math.random() * 10).toInt().toString()
  }

}

class DrawerItemHolder(view: View) : RecyclerView.ViewHolder(view) {

}