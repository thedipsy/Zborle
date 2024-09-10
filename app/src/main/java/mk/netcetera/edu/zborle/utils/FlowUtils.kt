package mk.netcetera.edu.zborle.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Collects the latest values emitted by the [Flow] and invokes the provided [block] for each emission,
 * ensuring that collection is done only when the [LifecycleOwner] is in the [Lifecycle.State.CREATED] state.
 *
 * @param lifecycleOwner The lifecycle owner to which the [Flow] collection is tied.
 * @param block The lambda function to be invoked for each emitted value. It receives the emitted value as a parameter.
 */
fun <T> Flow<T>.collectLatest(
  lifecycleOwner: LifecycleOwner,
  block: (T) -> Unit
) = lifecycleOwner.run {
  lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.CREATED) {
      collectLatest { block(it) }
    }
  }
}
