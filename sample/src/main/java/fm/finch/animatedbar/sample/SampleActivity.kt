package fm.finch.animatedbar.sample

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import fm.finch.animatedbar.AnimatedBarItem
import fm.finch.animatedbar.BaseAnimatedBar
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
        setUpAnimatedBar(vDefaultBar, 5) {
            AnimatedBarItem.Basic(
                id = it.toString(),
                title = "Item $it",
                icon = drawable(R.drawable.ic_default_bar_item)
            )
        }
    }

    private fun setUpMenuBar() {
        vMenuBar.setFromMenu(R.menu.animated_bar)
        setUpAnimatedBar(vMenuBar, vMenuBar.items)
    }

    private fun setUpCustomBar() {
        setUpAnimatedBar(vCustomBar, 5) {
            CustomAnimatedBarItem(
                id = it.toString(),
                title = "Item ${it.toString().repeat(it)}",
                icon = drawable(R.drawable.ic_custom_bar_item),
                someExtraData = "Some extra data for item $it"
            )
        }
    }

    private fun drawable(@DrawableRes resId: Int): Drawable? =
        ContextCompat.getDrawable(this, resId)

    private fun <T : AnimatedBarItem> setUpAnimatedBar(
        bar: BaseAnimatedBar<T>,
        itemCount: Int,
        createItem: (Int) -> T
    ) {
        setUpAnimatedBar(bar, (1..itemCount).map(createItem))
    }

    private fun <T : AnimatedBarItem> setUpAnimatedBar(
        bar: BaseAnimatedBar<T>,
        barItems: List<T>
    ) {
        bar.apply {
            isAnimationEnabled = false
            items = barItems
            selectedItemId = barItems.firstOrNull()?.id
            isAnimationEnabled = true
            onItemClicked = { selectedItemId = it }
        }
    }
}
