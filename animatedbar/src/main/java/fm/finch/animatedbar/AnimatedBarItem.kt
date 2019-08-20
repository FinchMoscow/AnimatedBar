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
