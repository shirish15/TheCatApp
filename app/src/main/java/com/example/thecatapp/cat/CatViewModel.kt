package com.example.thecatapp.cat

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.Breed
import com.example.network.network.Result
import com.example.network.repo.CatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val catRepo: CatRepo) : ViewModel() {

    private val _catStateFlow = MutableStateFlow(CatState())
    val catStateFlow = _catStateFlow.asStateFlow()

    init {
        fetchCatData()
    }

    fun setEvents(events: CatEvents) {
        when (events) {
            CatEvents.LoadMoreData -> {
                try {
                    viewModelScope.launch(Dispatchers.IO) {
                        _catStateFlow.update {
                            it.copy(
                                isPagingInProgress = true,
                                pagingError = null,
                                hasMoreItems = false
                            )
                        }
                        delay(2000)
                        val newPage = _catStateFlow.value.pageNumber + 1
                        when (val catResponse =
                            catRepo.getCatImagesList(newPage)) {
                            is Result.Error -> {
                                _catStateFlow.update {
                                    it.copy(
                                        pagingError = "Something went wrong",
                                        isPagingInProgress = false,
                                    )
                                }
                            }

                            is Result.Success -> {
                                val catList = _catStateFlow.value.catImagesList?.toMutableList()
                                catList?.addAll(catResponse.data)
                                _catStateFlow.update {
                                    it.copy(
                                        catImagesList = catList,
                                        hasMoreItems = catResponse.data.isNotEmpty(),
                                        pageNumber = newPage,
                                        isPagingInProgress = false,
                                    )
                                }
                            }
                        }
                    }
                } catch (exception: Exception) {
                    _catStateFlow.update {
                        it.copy(pagingError = "Something went wrong", isPagingInProgress = false)
                    }
                }
            }
        }
    }


    private fun fetchCatData() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _catStateFlow.update {
                    it.copy(loading = true, errorState = null, hasMoreItems = false)
                }
                when (val catResponse = catRepo.getCatImagesList(_catStateFlow.value.pageNumber)) {
                    is Result.Error -> {
                        _catStateFlow.update {
                            it.copy(
                                errorState = "Something went wrong, please try again later.",
                                loading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        _catStateFlow.update {
                            it.copy(
                                catImagesList = catResponse.data,
                                loading = false,
                                hasMoreItems = catResponse.data.isNotEmpty()
                            )
                        }
                    }
                }
            }
        } catch (exception: Exception) {
            _catStateFlow.update {
                it.copy(
                    errorState = "Something went wrong, please try again later.",
                    loading = false
                )
            }
        }
    }
}


sealed interface CatEvents {
    data object LoadMoreData : CatEvents
}

@Immutable
data class CatState(
    val catImagesList: List<Breed>? = null,
    val loading: Boolean = false,
    val errorState: String? = null,
    val hasMoreItems: Boolean = false,
    val isPagingInProgress: Boolean = false,
    val pagingError: String? = null,
    val pageNumber: Int = 0,
)