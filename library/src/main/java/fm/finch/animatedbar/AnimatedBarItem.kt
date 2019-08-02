package fm.finch.animatedbar

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * Item for animated bar.
 *
 * @property id Item id.
 * @property title Item title.
 * @property icon Item icon.
 */
interface AnimatedBarItem {
    val id: String
    val title: String
    val icon: Drawable?

    data class Basic(
        override val id: String,
        override val title: String,
        @DrawableRes override val icon: Drawable?
    ) : AnimatedBarItem
}
