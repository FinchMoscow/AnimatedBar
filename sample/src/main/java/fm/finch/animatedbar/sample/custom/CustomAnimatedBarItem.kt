package fm.finch.animatedbar.sample.custom

import android.graphics.drawable.Drawable
import fm.finch.animatedbar.AnimatedBarItem

data class CustomAnimatedBarItem(
    override val id: String,
    override val title: String,
    override val icon: Drawable?,
    val someExtraData: String
) : AnimatedBarItem
