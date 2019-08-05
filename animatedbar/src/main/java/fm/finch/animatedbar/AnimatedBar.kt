package fm.finch.animatedbar

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.MenuRes

/** Animated bar that expects [AnimatedBarItem] as its item. */
class AnimatedBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseAnimatedBar<AnimatedBarItem>(context, attrs) {

    /** Sets items from menu. */
    fun setFromMenu(@MenuRes menuRes: Int) {
        val menu = inflateMenu(context, menuRes)
        items = (0 until menu.size()).map { index ->
            val menuItem = menu.getItem(index)
            AnimatedBarItem.Basic(
                id = menuItem.itemId.toString(),
                title = menuItem.title.toString(),
                icon = menuItem.icon
            )
        }
    }
}
