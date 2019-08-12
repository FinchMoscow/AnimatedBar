package fm.finch.animatedbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat

/** Loops through all the child views and invokes [action] for each of them.
 *
 * @param depthLevel Determines how deep though the view hierarchy should the loop go:
 * if 1 - only the direct children are looped through,
 * if 2 - direct children and children of each child, etc.
 * if less then 1, than the entire view hierarchy is traversed.
 * Default value is 1.
 */
internal fun ViewGroup.onChildren(depthLevel: Int = 1, action: (View) -> Unit) {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        action(child)

        when {
            depthLevel < 1 -> (child as? ViewGroup)?.onChildren(0, action)
            depthLevel > 1 -> (child as? ViewGroup)?.onChildren(depthLevel - 1, action)
        }
    }
}

internal fun View.show(isShow: Boolean, goneIfNotShow: Boolean = true) {
    visibility = when {
        isShow -> View.VISIBLE
        goneIfNotShow -> View.GONE
        else -> View.INVISIBLE
    }
}

internal fun View.onAttrs(
    attrSet: AttributeSet?,
    attrs: IntArray,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    action: TypedArray.() -> Unit
) {
    context.obtainStyledAttributes(attrSet, attrs, defStyleAttr, defStyleRes)
        .run {
            action()
            recycle()
        }
}

internal fun Context.dimen(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)

internal fun Context.int(@IntegerRes resId: Int): Int = resources.getInteger(resId)

internal fun Context.drawable(@DrawableRes resId: Int): Drawable? =
    ContextCompat.getDrawable(this, resId)

internal fun inflateMenu(context: Context, @MenuRes menuRes: Int): Menu {
    val popup = PopupMenu(context, null)
    val menu = popup.menu
    popup.menuInflater.inflate(menuRes, menu)
    return menu
}
