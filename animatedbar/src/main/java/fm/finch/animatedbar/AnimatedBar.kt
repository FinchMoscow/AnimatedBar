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
import androidx.annotation.MenuRes

/** Animated bar that expects [AnimatedBarItem] as its item. */
class AnimatedBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseAnimatedBar<AnimatedBarItem>(context, attrs) {

    /**
     * Sets items from menu.
     * You can that get them from [items].
     *
     * Note that as [selectedItemId] is String, you have to case it to/from Int when working with item id.
     *
     * @param menuRes Resource for menu.
     *
     * @param onMenuItemClicked Invoked when item is clicked.
     * Takes menu item's id (already cast to Int for convenience) and item itself.
     * Returns true if the item should be selected, false otherwise.
     *
     * Note that is will be set as new [onItemClicked] so it will override the previous one.
     * You can avoid this by passing null, in which case the previous [onItemClicked] is kept.
     */
    fun setFromMenu(
        @MenuRes menuRes: Int,
        onMenuItemClicked: ((Int, AnimatedBarItem) -> Boolean)? = { _, _ -> true }
    ) {
        val menu = inflateMenu(context, menuRes)
        items = (0 until menu.size()).map { index ->
            val menuItem = menu.getItem(index)
            AnimatedBarItem.Basic(
                id = menuItem.itemId.toString(),
                title = menuItem.title.toString(),
                icon = menuItem.icon
            )
        }
        onMenuItemClicked?.let {
            onItemClicked = { item -> onMenuItemClicked(item.id.toInt(), item) }
        }
    }
}
