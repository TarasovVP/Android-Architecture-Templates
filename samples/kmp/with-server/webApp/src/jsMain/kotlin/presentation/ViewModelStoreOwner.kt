package presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.vnteam.architecturetemplates.presentation.viewmodels.CreateViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.DetailsViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import org.koin.compose.koinInject

import kotlin.reflect.KClass

@Composable
fun <VM : ViewModel> viewModel(
    modelClass: KClass<VM>
): VM {
    return androidx.lifecycle.viewmodel.compose.viewModel(modelClass, factory = appViewModelFactory(modelClass.simpleName.orEmpty()))
}

@Composable
private fun appViewModelFactory(
    modelClassName: String
): ViewModelProvider.Factory {
    return when (modelClassName) {
        DetailsViewModel::class.simpleName -> {
            val detailsViewModel: DetailsViewModel = koinInject()
            viewModelFactory {
                initializer { detailsViewModel }
            }
        }
        else -> {
            val listViewModel: ListViewModel = koinInject()
            viewModelFactory {
                initializer { listViewModel }
            }
        }
    }
}

class ComposeViewModelStoreOwner: ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
    fun dispose() { viewModelStore.clear() }
}

@Composable
private fun rememberComposeViewModelStoreOwner(): ViewModelStoreOwner {
    val viewModelStoreOwner = remember { ComposeViewModelStoreOwner() }
    DisposableEffect(viewModelStoreOwner) {
        onDispose { viewModelStoreOwner.dispose() }
    }
    return viewModelStoreOwner
}

@Composable
fun withViewModelStoreOwner(content: @Composable () -> Unit) {
    if (LocalViewModelStoreOwner.current != null) {
        content()
    } else {
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides rememberComposeViewModelStoreOwner(),
            content = content
        )
    }
}