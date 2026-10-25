package com.vnteam.architecturetemplates.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.ForkUIMapper
import com.vnteam.architecturetemplates.domain.models.Fork
import com.vnteam.architecturetemplates.domain.usecase.ForkUseCase
import com.vnteam.architecturetemplates.presentation.uimodels.ForkUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val forkUseCase: ForkUseCase,
    private val forkUIMapper: ForkUIMapper,
) : ViewModel() {

    val progressVisibilityLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val forksFromApiLiveData = MutableLiveData<List<Fork>>()
    val forksToDBInsertedLiveData = MutableLiveData<Unit>()
    val forksFromDBLiveData = MutableLiveData<List<ForkUI>>()

    fun getForksFromApi() {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            val forks = forkUseCase.getForksFromApi()
            forks?.let { forksFromApiLiveData.postValue(it) }
        }
    }

    fun insertForksToDB(forks: List<Fork>) {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            forkUseCase.insertForksToDB(forks)
            progressVisibilityLiveData.postValue(false)
            forksToDBInsertedLiveData.postValue(Unit)
        }
    }

    fun getForksFromDB() {
        progressVisibilityLiveData.postValue(true)
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            progressVisibilityLiveData.postValue(false)
            errorLiveData.postValue(exception.localizedMessage)
        }) {
            val forks = forkUIMapper.mapToImplModelList(forkUseCase.getForksFromDB())
            forksFromDBLiveData.postValue(forks)
            progressVisibilityLiveData.postValue(false)
        }
    }
}