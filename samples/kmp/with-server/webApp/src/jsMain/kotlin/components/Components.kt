package components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.web.events.SyntheticMouseEvent
import com.vnteam.architecturetemplates.presentation.uimodels.OwnerUI
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cursor
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.flexShrink
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.lineHeight
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.textDecoration
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
fun VerticalLayout(content: @Composable () -> Unit) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.FlexStart)
            width(100.percent)
            marginTop(0.px)
            height(100.vh)
            position(Position.Relative)
        }
    }) {
        content()
    }
}

@Composable
fun BaseButton(buttonText: String, onClick: (SyntheticMouseEvent) -> Unit) {
    Button(attrs = {
        style {
            gap(8.px)
            width(200.px)
            display(DisplayStyle.Flex)
            padding(16.px)
            alignItems(AlignItems.Center)
            flexShrink(0)
            borderRadius(8.px)
            justifyContent(JustifyContent.Center)
            backgroundColor(rgb(52, 114, 159))
            color(Color.white)
            fontSize(24.px)
            textAlign("left")
            fontFamily("Roboto")
            fontWeight(400)
            lineHeight("normal")
            textDecoration("none")
            cursor("pointer")
        }
        onClick(onClick)
    }) {
        Text(buttonText)
    }
}

@Composable
fun DynamicVerticalList(items: List<String>, onItemClick: (String) -> Unit) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.FlexStart)
            width(100.percent)
            marginTop(0.px)
            height(100.vh)
            position(Position.Relative)
        }
    }) {
        items.forEach { item ->
            Div({
                style {
                    backgroundColor(Color.lightgray)
                    padding(8.px)
                    margin(8.px)
                    cursor("pointer")
                }
                onClick {
                    onItemClick(item)
                }
            }) {
                Text(item)
            }
        }
    }
}

@Composable
fun CircularProgress() {
    Div(attrs = {
        style {
            property("border", "4px solid rgba(0, 0, 0, 0.1)")
            property("border-top", "4px solid #3498db")
            position(Position.Fixed)
            bottom(50.percent)
            width(40.px)
            height(40.px)
            borderRadius(50.percent)
            property("animation", "spin 2s linear infinite")
        }
    }) {}
}

@Composable
fun Toast(message: String, onDismiss: () -> Unit) {
    Div({
        style {
            position(Position.Fixed)
            bottom(16.px)
            left(50.percent)
            padding(8.px)
            backgroundColor(rgba(0, 0, 0, 0.7))
            color(Color.white)
            borderRadius(4.px)
            maxWidth(80.percent)
            property("transform", "translateX(-50%)")
            property("z-index", "999")
            property("box-shadow", "0 4px 6px rgba(0, 0, 0, 0.1)")
            property("transition", "all 0.3s ease-in-out")
        }
    }) {
        Text(message)
        LaunchedEffect(true) {
            delay(1000)
            onDismiss()
        }
    }
}

@Composable
fun OwnerCard(owner: OwnerUI?) {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            gap(30.px)
            width(1110.px)
            padding(16.px)
            alignItems(AlignItems.Center)
            flexShrink(0)
            borderRadius(16.px)
            justifyContent(JustifyContent.Center)
            backgroundColor(Color("rgba(240, 240, 240, 1)"))
        }
    }) {
        Img(src = owner?.avatarUrl.orEmpty(),
            alt = "img210",
            attrs = { style {
                width(60.px)
                height(60.px)
            }
            }
        )
        Div(attrs = { classes(AppStyles.textStyle) }) { Text(owner?.login.orEmpty()) }
    }
}
