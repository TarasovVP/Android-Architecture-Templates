package com.vnteam.architecturetemplates.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val demoObjectRepository: DBRepository,
    private val demoObjectUIMapper: DemoObjectUIMapper,
): ViewModel() {

    val progressVisibilityLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val demoObjectLiveData = MutableLiveData<DemoObjectUI>()

    fun getDemoObjectById(demoObjectId: Long?) {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            val demoObject = demoObjectRepository.getDemoObjectById(demoObjectId ?: 0)
            demoObjectLiveData.postValue(demoObjectUIMapper.mapToImplModel(demoObject))
            progressVisibilityLiveData.postValue(false)
        }
    }
}