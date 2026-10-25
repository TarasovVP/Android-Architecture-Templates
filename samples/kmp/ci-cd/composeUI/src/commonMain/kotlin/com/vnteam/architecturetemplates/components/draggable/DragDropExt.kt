package com.vnteam.architecturetemplates.components.draggable

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.zIndex
import com.vnteam.architecturetemplates.UIConstants.FLOAT_PADDING

@Composable
fun rememberDragDropState(
    lazyListState: LazyListState,
    onSwap: (Int, Int) -> Unit,
): DragDropState {
    val scope = rememberCoroutineScope()
    val state =
        remember(lazyListState) {
            DragDropState(
                state = lazyListState,
                onSwap = onSwap,
                scope = scope,
            )
        }
    return state
}

fun LazyListState.getVisibleItemInfoFor(absoluteIndex: Int): LazyListItemInfo? =
    this
        .layoutInfo
        .visibleItemsInfo
        .getOrNull(
            absoluteIndex -
                this.layoutInfo.visibleItemsInfo
                    .first()
                    .index,
        )

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.DraggableItem(
    dragDropState: DragDropState,
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(isDragging: Boolean) -> Unit,
) {
    val current: Float by animateFloatAsState(dragDropState.draggingItemOffset * FLOAT_PADDING)
    val previous: Float by animateFloatAsState(dragDropState.previousItemOffset.value * FLOAT_PADDING)
    val dragging = index == dragDropState.currentIndexOfDraggedItem

    val draggingModifier =
        if (dragging) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationY = current
                }
        } else if (index == dragDropState.previousIndexOfDraggedItem) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationY = previous
                }
        } else {
            Modifier.animateItem(
                tween(easing = FastOutLinearInEasing),
            )
        }
    Column(modifier = modifier.then(draggingModifier)) {
        content(dragging)
    }
}

@Composable
fun UpdateViewConfiguration(
    longPressTimeoutMillis: Long? = null,
    content: @Composable () -> Unit,
) {
    fun ViewConfiguration.updateViewConfiguration() =
        object : ViewConfiguration {
            override val longPressTimeoutMillis
                get() =
                    longPressTimeoutMillis
                        ?: this@updateViewConfiguration.longPressTimeoutMillis

            override val doubleTapTimeoutMillis
                get() = this@updateViewConfiguration.doubleTapTimeoutMillis

            override val doubleTapMinTimeMillis
                get() = this@updateViewConfiguration.doubleTapMinTimeMillis

            override val touchSlop: Float
                get() = this@updateViewConfiguration.touchSlop
        }

    CompositionLocalProvider(
        LocalViewConfiguration provides LocalViewConfiguration.current.updateViewConfiguration(),
    ) {
        content()
    }
}
