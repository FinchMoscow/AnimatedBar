/**
 * Copyright 2019 FINCH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fm.finch.animatedbar

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.view_animated_bar_item.view.ivIcon
import kotlinx.android.synthetic.main.view_animated_bar_item.view.tvTitle
import kotlinx.android.synthetic.main.view_animated_bar_item.view.vPanel

/** Item view for animated bar that animates visibility of item's title. */
class AnimatedBarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val lytTransition = LayoutTransition().apply {
        setDuration(context.int(R.integer.animated_bar_animation_duration).toLong())
    }

    var itemId: String = ""

    var title: String
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.text = value
        }

    @DrawableRes
    var icon: Drawable? = null
        set(value) {
            field = value
            ivIcon.setImageDrawable(value)
        }

    var isAnimationEnabled: Boolean = false
        set(value) {
            field = value
            vPanel.layoutTransition = if (value) lytTransition else null
        }

    init {
        inflate(context, R.layout.view_animated_bar_item, this)
        isSelected = false
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        ivIcon.isSelected = selected
        tvTitle.isSelected = selected
        tvTitle.show(!isSelected) // Toggle visibility to force animation
        tvTitle.show(isSelected)
    }

    fun setTitleTextAppearance(resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvTitle.setTextAppearance(resId)
        } else {
            tvTitle.setTextAppearance(context, resId)
        }
    }

    fun setIconSize(iconSize: Int) {
        ivIcon.layoutParams = LinearLayout.LayoutParams(iconSize, iconSize)
    }

    fun setItemBackground(@DrawableRes backgroundResId: Int) {
        vPanel.setBackgroundResource(backgroundResId)
    }

    fun setTitleMargin(margin: Int) {
        (tvTitle.layoutParams as LinearLayout.LayoutParams).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                marginStart = margin
            } else {
                setMargins(margin, topMargin, rightMargin, bottomMargin)
            }
        }
    }

    fun setItemPaddingHorizontal(paddingHorizontal: Int) {
        vPanel.setPadding(
            paddingHorizontal, vPanel.paddingTop, paddingHorizontal, vPanel.paddingBottom
        )
    }

    fun setItemPaddingVertical(paddingVertical: Int) {
        vPanel.setPadding(
            vPanel.paddingLeft, paddingVertical, vPanel.paddingRight, paddingVertical
        )
    }

    fun setAnimationDuration(animationDurationMs: Int) {
        lytTransition.setDuration(animationDurationMs.toLong())
    }
}
