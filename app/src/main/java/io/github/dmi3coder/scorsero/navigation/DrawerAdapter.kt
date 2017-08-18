package io.github.dmi3coder.scorsero.navigation

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.navigation.NavigationContract.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.item_drawer_option.view.count
import kotlinx.android.synthetic.main.item_drawer_option.view.icon
import kotlinx.android.synthetic.main.item_drawer_option.view.title

/**
 * Created by dim3coder on 9:06 AM 8/2/17.
 */

class DrawerAdapter(var items: Array<NavigationItem>,
    val presenter: Presenter, val drawerLayout: DrawerLayout,
    startPosition: Int = 1) : RecyclerView.Adapter<DrawerItemHolder>() {

  val selectSubscription: BehaviorSubject<Int> = BehaviorSubject.createDefault(startPosition)

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DrawerItemHolder {
    return DrawerItemHolder(
        LayoutInflater.from(parent!!.context).inflate(R.layout.item_drawer_option, parent, false))
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: DrawerItemHolder?, position: Int) {
    val view = holder!!.itemView
    val currentItem = items[position]

    holder.selectionSubscription = selectSubscription.subscribe({
      val color = if (it == position) Color.YELLOW else Color.GRAY
      arrayOf(view.title, view.count).forEach {
        it.setTextColor(color)
      }
      view.icon.imageTintList = ColorStateList.valueOf(color)
    })

    view.title.text = holder.itemView.context.getString(currentItem.name)
    holder.itemView.setOnClickListener {
      presenter.navigationChosen(items[position])
      selectSubscription.onNext(position)
      drawerLayout.closeDrawers()
    }
    holder.countSubscription = currentItem.itemCount.observeOn(
        AndroidSchedulers.mainThread()).subscribe {
      view.count.text = it.toString()
    }
  }

  override fun onViewRecycled(holder: DrawerItemHolder?) {
    super.onViewRecycled(holder)
    holder?.apply {
      countSubscription?.dispose()
      selectionSubscription?.dispose()
    }
  }
}

class DrawerItemHolder(view: View) : RecyclerView.ViewHolder(view) {
  var countSubscription: Disposable? = null
  var selectionSubscription: Disposable? = null

}