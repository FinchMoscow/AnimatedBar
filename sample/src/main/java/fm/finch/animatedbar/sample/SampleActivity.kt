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
package fm.finch.animatedbar.sample

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import fm.finch.animatedbar.AnimatedBarItem
import fm.finch.animatedbar.sample.custom.CustomAnimatedBarItem
import kotlinx.android.synthetic.main.activity_sample.vCustomBar
import kotlinx.android.synthetic.main.activity_sample.vDefaultBar
import kotlinx.android.synthetic.main.activity_sample.vMenuBar

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        setUpDefaultBar()
        setUpMenuBar()
        setUpCustomBar()
    }

    private fun setUpDefaultBar() {
        val defaultBarItems = (1..5).map {
            AnimatedBarItem.Basic(
                id = it.toString(),
                title = "Item $it",
                icon = drawable(R.drawable.ic_default_bar_item)
            )
        }
        vDefaultBar.items = defaultBarItems
        vDefaultBar.selectedItemId = defaultBarItems.first().id
    }

    private fun setUpMenuBar() {
        vMenuBar.setFromMenu(R.menu.animated_bar) { itemId, item ->
            // We can access title and icon of the item
            val title = item.title
            val icon = item.icon

            // itemId has been already cast to Int,
            // so we can check it the same way we usually do for menus.
            when (itemId) {
                R.id.menu_item_1 -> {
                    // Do something
                }
                R.id.menu_item_2 -> {
                    // Do something else
                }
            }

            // Return true to automatically select item
            true
        }
        vMenuBar.selectedItemId = R.id.menu_item_1.toString()
    }

    private fun setUpCustomBar() {
        val customBarItems = (1..5).map {
            CustomAnimatedBarItem(
                id = it.toString(),
                title = "Item ${it.toString().repeat(it)}",
                icon = drawable(R.drawable.ic_custom_bar_item),
                someExtraData = "Some extra data for item $it"
            )
        }
        vCustomBar.run {
            // We can disable animations until we properly set up the bar.
            // Useless here, but can be useful, for example, when loading bar items asynchronously
            // and then displaying them and selecting initial item without animations.
            isAnimationEnabled = false

            items = customBarItems
            selectedItemId = customBarItems.first().id

            // Turn animations back on
            isAnimationEnabled = true

            onItemClicked = { item ->
                // Note that the item that is passed here is of CustomAnimatedBarItem type,
                // as we set up our custom bar to work with this type of items.
                // So no need to cast anything.
                val someExtraData = item.someExtraData

                // You can add any custom handling here.
                // For example, lets make it so that clicking a selected item will deselect it.
                if (item.id == selectedItemId) {
                    selectedItemId = null
                } else {
                    selectedItemId = item.id
                }

                // Return false as we don't need the item to be automatically selected.
                false
            }
        }
    }

    private fun drawable(@DrawableRes resId: Int): Drawable? =
        ContextCompat.getDrawable(this, resId)
}
