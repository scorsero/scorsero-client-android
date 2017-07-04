package io.github.dmi3coder.scorsero.utils

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import com.bluelinelabs.conductor.Controller

/**
 * Created by dim3coder on 6:48 PM 7/3/17.
 */

abstract class LifecycleController: Controller(), LifecycleRegistryOwner{
  var lifecycleRegistry = LifecycleRegistry(this);

  override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry;

}
