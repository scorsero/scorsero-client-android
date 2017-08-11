package io.github.dmi3coder.scorsero.navigation

import android.support.annotation.StringRes
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.reactivex.Flowable
import org.joda.time.Interval

/**
 * Created by dim3coder on 9:03 AM 8/5/17.
 */
data class NavigationItem(
    @StringRes val name: Int,
    val range: Interval,
    val repository: ScoreRepository = ScoreRepository.getInstance()
) {
  val itemCount: Flowable<Int> = repository.subscribeElementCountFor(range)

}