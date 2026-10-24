package components

import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontStyle
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.lineHeight
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.width


object AppStyles : StyleSheet() {
    val textStyle by style {
        width(1110.px)
        color(Color("rgba(23, 23, 34, 1)"))
        fontSize(24.px)
        fontStyle("normal")
        textAlign("left")
        fontFamily("Roboto")
        fontWeight(400)
        lineHeight("normal")
        margin(16.px)
        property("font-stretch", "normal")
    }
}