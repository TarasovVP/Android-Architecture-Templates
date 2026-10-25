package presentation

import androidx.compose.runtime.Composable
import com.vnteam.architecturetemplates.Res
import com.vnteam.architecturetemplates.ic_avatar_1
import com.vnteam.architecturetemplates.ic_avatar_10
import com.vnteam.architecturetemplates.ic_avatar_2
import com.vnteam.architecturetemplates.ic_avatar_3
import com.vnteam.architecturetemplates.ic_avatar_4
import com.vnteam.architecturetemplates.ic_avatar_5
import com.vnteam.architecturetemplates.ic_avatar_6
import com.vnteam.architecturetemplates.ic_avatar_7
import com.vnteam.architecturetemplates.ic_avatar_8
import com.vnteam.architecturetemplates.ic_avatar_9
import com.vnteam.architecturetemplates.ic_avatar_default
import com.vnteam.architecturetemplates.ic_empty_state
import com.vnteam.architecturetemplates.presentation.resources.DrawableResources
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun String?.textWithNoDataHandling() = this.takeIf { it.orEmpty().isNotEmpty() } ?: LocalStringResources.current.NO_DATA

fun DrawableResources.drawableRes(resId: String): DrawableResource {
    return when(resId) {
        EMPTY_STATE -> Res.drawable.ic_empty_state
        IC_AVATAR_1 -> Res.drawable.ic_avatar_1
        IC_AVATAR_2 -> Res.drawable.ic_avatar_2
        IC_AVATAR_3 -> Res.drawable.ic_avatar_3
        IC_AVATAR_4 -> Res.drawable.ic_avatar_4
        IC_AVATAR_5 -> Res.drawable.ic_avatar_5
        IC_AVATAR_6 -> Res.drawable.ic_avatar_6
        IC_AVATAR_7 -> Res.drawable.ic_avatar_7
        IC_AVATAR_8 -> Res.drawable.ic_avatar_8
        IC_AVATAR_9 -> Res.drawable.ic_avatar_9
        IC_AVATAR_10 -> Res.drawable.ic_avatar_10
        else -> Res.drawable.ic_avatar_default
    }
}