# Animated bar
Android library for an bar of items that animates item's title when it gets selected.

Written entirely in Kotlin.

## Integration
Minimum SDK version: 15.

Gradle:

```
dependencies {
    // Your other dependencies

    implementation 'fm.finch.android:animatedbar:1.0.1'
}
```

## Usage
Simply add the AnimatedBar in your layout: 
```
<fm.finch.animatedbar.AnimatedBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

### Customization
You can customize the look of the AnimatedBar by using the following attributes in xml (and corresponding properties from code):

| Attribute     | Property        | Description   | Default value |
| ------------- | ------------- | -----------   | ------------- |
| `animatedBar_animationEnabled` | `isAnimationEnabled` | Enables/disables animation | true |
| `animatedBar_animationDuration` | `animationDuration` | Duration for item selection animation. In milliseconds. | `@integer/animated_bar_animation_duration` (200) |
| `animatedBar_itemTitleAppearance` | `itemTitleAppearance` | Style for item's title. | `@style/AnimatedBarTitleAppearance` |
| `animatedBar_itemIconSize` | `isAnimationEnabled` | Icon size for item.  | `@dimen/animated_bar_item_icon_size` (18dp) |
| `animatedBar_itemBackground` | `itemBackgroundRes` | Item background. | `@drawable/animated_bar_item_bg` |
| `animatedBar_itemTitleMargin` | `itemTitleMargin` | Margin between title and icon | `@dimen/animated_bar_item_title_margin` (8dp) |
| `animatedBar_itemPaddingVertical` | `itemPaddingVertical` | Top and bottom padding for item. | `@dimen/animated_bar_item_padding_vertical` (8dp) |
| `animatedBar_itemPaddingHorizontal` | `itemPaddingHorizontal` | Left and right padding for item. | `@dimen/animated_bar_item_padding_horizontal` (8dp) |
| `android:background` | - | Background for AnimatedBar | - |