package com.vnteam.architecturetemplates.components.draggable

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.vnteam.architecturetemplates.UIConstants.LONG_PRESS_TIME_OUT
import com.vnteam.architecturetemplates.UIConstants.THIRD_PART
import com.vnteam.architecturetemplates.UIConstants.ZERO_FLOAT
import com.vnteam.architecturetemplates.resources.LocalSmallPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> DragDropColumn(
    modifier: Modifier,
    items: List<T>,
    onSwap: (Int, Int) -> Unit,
    onDragEnd: () -> Unit,
    itemContent: @Composable LazyItemScope.(item: T, isDragging: Boolean) -> Unit,
) {
    val overscrollJob = remember { mutableStateOf<Job?>(null) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val dragDropState =
        rememberDragDropState(listState) { fromIndex, toIndex ->
            onSwap(fromIndex, toIndex)
        }

    UpdateViewConfiguration(
        longPressTimeoutMillis = LONG_PRESS_TIME_OUT,
    ) {
        LazyColumn(
            modifier =
                modifier
                    .pointerInput(dragDropState) {
                        detectDragGesturesAfterLongPress(
                            onDrag = { change, offset ->
                                change.consume()
                                dragDropState.onDrag(offset = offset)

                                if (overscrollJob.value?.isActive == true) {
                                    return@detectDragGesturesAfterLongPress
                                }

                                dragDropState
                                    .checkForOverScroll()
                                    .takeIf { it != ZERO_FLOAT }
                                    ?.let {
                                        overscrollJob.value =
                                            scope.launch {
                                                dragDropState.state.animateScrollBy(
                                                    it * THIRD_PART,
                                                    tween(easing = FastOutLinearInEasing),
                                                )
                                            }
                                    }
                                    ?: run { overscrollJob.value?.cancel() }
                            },
                            onDragStart = { offset -> dragDropState.onDragStart(offset) },
                            onDragEnd = {
                                dragDropState.onDragInterrupted()
                                overscrollJob.value?.cancel()
                                onDragEnd.invoke()
                            },
                            onDragCancel = {
                                dragDropState.onDragInterrupted()
                                overscrollJob.value?.cancel()
                                onDragEnd.invoke()
                            },
                        )
                    },
            state = listState,
            contentPadding = PaddingValues(LocalSmallPadding.current.size),
            verticalArrangement = Arrangement.spacedBy(LocalSmallPadding.current.size),
        ) {
            itemsIndexed(items = items) { index, item ->
                DraggableItem(
                    dragDropState = dragDropState,
                    index = index,
                    Modifier,
                ) { isDragging ->
                    itemContent(item, isDragging)
                }
            }
        }
    }
}
