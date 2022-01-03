package com.gketdev.xkcdreader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.domain.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetItemUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(XkcdItemUiState()))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getLatestItem()
    }

    private fun getLatestItem() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            useCase.invoke().collect {
                when (it) {
                    is DataResultState.Success -> {
                        val item = it.data
                        _uiState.value = _uiState.value.copy(
                            xkcdItem = XkcdItemUiState(
                                id = item.num,
                                title = item.title,
                                alt = item.alt,
                                image = item.img
                            ),
                            isLoading = false
                        )
                    }
                    is DataResultState.Error -> {
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, error = "Item Not Found!")
                    }
                }
            }
        }
    }

}