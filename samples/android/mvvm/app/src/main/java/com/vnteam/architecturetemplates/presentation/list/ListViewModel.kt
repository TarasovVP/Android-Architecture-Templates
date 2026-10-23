package com.vnteam.architecturetemplates.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.DemoObjectUseCase
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val demoObjectUseCase: DemoObjectUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper,
) : ViewModel() {

    val progressVisibilityLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val demoObjectsFromApiLiveData = MutableLiveData<List<DemoObject>>()
    val demoObjectsToDBInsertedLiveData = MutableLiveData<Unit>()
    val demoObjectsFromDBLiveData = MutableLiveData<List<DemoObjectUI>>()

    fun getDemoObjectFromApi() {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            val demoObjects = demoObjectUseCase.getDemoObjectsFromApi()
            demoObjects?.let { demoObjectsFromApiLiveData.postValue(it) }
        }
    }

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            demoObjectUseCase.insertDemoObjectsToDB(demoObjects)
            progressVisibilityLiveData.postValue(false)
            demoObjectsToDBInsertedLiveData.postValue(Unit)
        }
    }

    fun getDemoObjectFromDB() {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            val demoObjects = demoObjectUIMapper.mapToImplModelList(demoObjectUseCase.getDemoObjectsFromDB())
            demoObjectsFromDBLiveData.postValue(demoObjects)
            progressVisibilityLiveData.postValue(false)
        }
    }
}