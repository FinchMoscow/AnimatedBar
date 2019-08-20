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

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.view_animated_bar.view.vConstraintPanel

/**
 * Animated bar that animates visibility of item's title on selection.
 *
 * You can extends this class with your own subclass to work with custom [AnimatedBarItem],
 * or simply use [AnimatedBarItem] if you don't need such customization.
 *
 * @param TItem Displayed item that the bar works with.
 */
open class BaseAnimatedBar<TItem : AnimatedBarItem> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    /** Displayed items. */
    var items: List<TItem> = listOf()
        set(value) {
            field = value
            vConstraintPanel.removeAllViews()
            val viewIds = mutableListOf<Int>()
            value.forEach { item ->
                vConstraintPanel.addView(
                    AnimatedBarItemView(context).apply {
                        itemId = item.id
                        title = item.title
                        icon = item.icon
                        id = ViewIdGenerator.generate().also {
                            viewIds.add(it)
                        }
                        setOnClickListener {
                            if (onItemClicked(item)) {
                                selectedItemId = item.id
                            }
                        }
                        layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.MATCH_PARENT
                        )

                        isAnimationEnabled = this@BaseAnimatedBar.isAnimationEnabled
                        setAnimationDuration(animationDuration)
                        setTitleTextAppearance(itemTitleAppearance)
                        setIconSize(itemIconSize)
                        setItemBackground(itemBackgroundRes)
                        setTitleMargin(itemTitleMargin)
                        setItemPaddingHorizontal(itemPaddingHorizontal)
                        setItemPaddingVertical(itemPaddingVertical)
                    }
                )
            }

            ConstraintSet().apply {
                clone(vConstraintPanel)
                createHorizontalChain(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT,
                    viewIds.toIntArray(),
                    null,
                    ConstraintSet.CHAIN_SPREAD
                )
                viewIds.forEach {
                    centerVertically(it, ConstraintSet.PARENT_ID)
                }
                applyTo(vConstraintPanel)
            }
        }

    /**
     * Invoked when an item is clicked on by user.
     *
     * @return True if item should be automatically selected, false otherwise.
     * Note that if it returns false, you should set selected menu manually via [selectedItemId].
     */
    var onItemClicked: (TItem) -> Boolean = { true }

    /**
     * Item that is currently selected.
     * Note that setting this property will not trigger [onItemClicked].
     */
    var selectedItemId: String? = null
        set(value) {
            if (field == value) return
            field = value
            vConstraintPanel.onChildren { child ->
                if (child is AnimatedBarItemView) {
                    child.isSelected = value != null && child.itemId == value
                }
            }
        }

    /** Returns selected item. Null if there is none. */
    val selectedItem: TItem?
        get() = items.find { it.id == selectedItemId }

    // Attributes

    /**
     * Indicates if animation is enabled/disabled.
     * Can be used, for example, to set initial selected item without animation.
     */
    var isAnimationEnabled: Boolean = true
        set(value) {
            field = value
            onItemViews { it.isAnimationEnabled = value }
        }

    /**
     * Duration for item selection animation. In milliseconds.
     */
    var animationDuration: Int = context.int(R.integer.animated_bar_animation_duration)
        set(value) {
            field = value
            onItemViews { it.setAnimationDuration(value) }
        }

    /** Resource id for style for item title. */
    var itemTitleAppearance: Int = R.style.AnimatedBarTitleAppearance
        set(value) {
            field = value
            onItemViews { it.setTitleTextAppearance(value) }
        }

    /** Icon size for item, in pixels. */
    var itemIconSize: Int = context.dimen(R.dimen.animated_bar_item_icon_size)
        set(value) {
            field = value
            onItemViews { it.setIconSize(value) }
        }

    /** Resource id for item background. */
    @DrawableRes
    var itemBackgroundRes: Int = R.drawable.animated_bar_item_bg
        set(value) {
            field = value
            onItemViews { it.setItemBackground(value) }
        }

    /** Margin between title and icon, in pixels. */
    var itemTitleMargin: Int = context.dimen(R.dimen.animated_bar_item_title_margin)
        set(value) {
            field = value
            onItemViews { it.setTitleMargin(value) }
        }

    /** Left and right padding for item. */
    var itemPaddingHorizontal: Int = context.dimen(R.dimen.animated_bar_item_padding_horizontal)
        set(value) {
            field = value
            onItemViews { it.setItemPaddingHorizontal(value) }
        }

    /** Top and bottom padding for item. */
    var itemPaddingVertical: Int = context.dimen(R.dimen.animated_bar_item_padding_vertical)
        set(value) {
            field = value
            onItemViews { it.setItemPaddingVertical(value) }
        }

    init {
        inflate(context, R.layout.view_animated_bar, this)

        onAttrs(attrs, R.styleable.AnimatedBar, 0, R.style.AnimatedBar) {
            isAnimationEnabled = getBoolean(
                R.styleable.AnimatedBar_animatedBar_animationEnabled,
                isAnimationEnabled
            )
            animationDuration = getInt(
                R.styleable.AnimatedBar_animatedBar_animationDuration,
                animationDuration
            )
            itemTitleAppearance = getResourceId(
                R.styleable.AnimatedBar_animatedBar_itemTitleAppearance,
                itemTitleAppearance
            )
            itemIconSize = getDimensionPixelSize(
                R.styleable.AnimatedBar_animatedBar_itemIconSize,
                itemIconSize
            )
            itemBackgroundRes =
                getResourceId(
                    R.styleable.AnimatedBar_animatedBar_itemBackground,
                    itemBackgroundRes
                )
            itemTitleMargin = getDimensionPixelSize(
                R.styleable.AnimatedBar_animatedBar_itemTitleMargin,
                itemTitleMargin
            )
            itemPaddingHorizontal = getDimensionPixelSize(
                R.styleable.AnimatedBar_animatedBar_itemPaddingHorizontal,
                itemPaddingHorizontal
            )
            itemPaddingVertical = getDimensionPixelSize(
                R.styleable.AnimatedBar_animatedBar_itemPaddingVertical,
                itemPaddingVertical
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                resolveSize(
                    context.dimen(R.dimen.animated_bar_height),
                    heightMeasureSpec
                ),
                MeasureSpec.EXACTLY
            )
        )
    }

    private fun onItemViews(action: (AnimatedBarItemView) -> Unit) {
        vConstraintPanel.onChildren { child ->
            if (child is AnimatedBarItemView) {
                action(child)
            }
        }
    }
}
