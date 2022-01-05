package com.gketdev.xkcdreader.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.xkcdreader.domain.GetExplanationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: GetExplanationUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(DetailItemUiState()))
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getExplanationInfo(id: Int, title: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val desc = useCase(Pair(id, title)).response.text.description
            if (desc.isNotEmpty()) {
                _uiState.value =
                    _uiState.value.copy(detailItem = DetailItemUiState(desc), isLoading = true)
            }
        }
    }
}