package fm.finch.animatedbar

import android.os.Build
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

/** Generates unique ids for views. */
internal object ViewIdGenerator {

    private val sNextGeneratedId = AtomicInteger(1)

    fun generate(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId()
        } else {
            // Simply duplicate View.generateViewId() implementation
            while (true) {
                val result = sNextGeneratedId.get()
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        }
    }
}
