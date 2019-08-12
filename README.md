# AnimatedBar
Android library for an animated bar of items that animates item's title when it gets selected.

Written in Kotlin.

## Integration
Minimum SDK version: 15.

Add the following dependency in yout `build.gradle` file:

```
dependencies {
    // Other dependencies

    implementation 'fm.finch.android:animatedbar:1.0.1'
}
```

## Usage

Add AnimatedBar in your layout: 
```
<fm.finch.animatedbar.AnimatedBar
        android:id="@+id/animatedBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

Set items via the `items` property. For example, here we generate and set a list of 5 items:
```
val animatedBarItems: List<AnimatedBarItem> = (1..5).map {
            AnimatedBarItem.Basic(
                id = it.toString(),
                title = "Item $it",
                icon = ContextCompat.getDrawable(this, R.drawable.ic_animated_bar_item))
            )
    }
animatedBar.items = animatedBarItems
```

*`AnimatedBarItem` is an interface for items, so to create instances of items, use `AnimatedBarItem.Basic`.*

Set selected item by passing its id to `selectedItemId`. For example, here we select the first item:
```
animatedBar.selectedItemId = animatedBarItems.first().id
```

By default, when user clicks an item, it automatically gets selected. You can change that, or add some additional handling, by setting `onItemClicked`:
```
animatedBar.onItemClicked = { item -> 
    // Do something
    
    // Return true if you want the clicked item to get selected, false otherwise
    return false
}
```
*Note that `onItemClicked` is not invoked when selecting an item via `selectedItemId`.*

You can disable animations with the `isAnimationEnabled` property. This can be useful, for example, when you load and set items asynchronously and want to set initial item without animations.
```
animatedBar.isAnimationEnabled = false

animatedBar.items = animatedBarItems
animatedBar.selectedItemId = animatedBarItems.first().id

animatedBar.isAnimationEnabled = true
```

### Customization
You can customize the look of the AnimatedBar by using the following attributes in the layout file (and corresponding properties from code):

| Attribute (in layout)     | Property (in code)        | Description   | Default value |
| ------------- | ------------- | -----------   | ------------- |
| `animatedBar_animationEnabled` | `isAnimationEnabled` | Enables/disables animation | true |
| `animatedBar_animationDuration` | `animationDuration` | Duration for item selection animation. In milliseconds. | `@integer/animated_bar_animation_duration` (200) |
| `animatedBar_itemTitleAppearance` | `itemTitleAppearance` | Style for item's title. | `@style/AnimatedBarTitleAppearance` |
| `animatedBar_itemIconSize` | `isAnimationEnabled` | Icon size for item.  | `@dimen/animated_bar_item_icon_size` (18dp) |
| `animatedBar_itemBackground` | `itemBackgroundRes` | Item background. | `@drawable/animated_bar_item_bg` |
| `animatedBar_itemTitleMargin` | `itemTitleMargin` | Margin between title and icon | `@dimen/animated_bar_item_title_margin` (8dp) |
| `animatedBar_itemPaddingVertical` | `itemPaddingVertical` | Top and bottom padding for item. | `@dimen/animated_bar_item_padding_vertical` (8dp) |
| `animatedBar_itemPaddingHorizontal` | `itemPaddingHorizontal` | Left and right padding for item. | `@dimen/animated_bar_item_padding_horizontal` (8dp) |
| `android:background` | - | Background for AnimatedBar | transparent |