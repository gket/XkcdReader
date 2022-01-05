package com.gketdev.xkcdreader.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.domain.DeleteToFavoriteUseCase
import com.gketdev.xkcdreader.domain.GetFavoriteItemsUseCase
import com.gketdev.xkcdreader.presentation.home.HomeUiState
import com.gketdev.xkcdreader.presentation.home.XkcdItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteItemsUseCase,
    private val removeFavoriteUseCase: DeleteToFavoriteUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState(null))
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getFavoriteItems()
    }

    private fun getFavoriteItems() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getFavoriteUseCase(null).collect {
                when (it) {
                    is DataResultState.Success -> {
                        _uiState.value = _uiState.value.copy(
                            item = it.data.map { it ->
                                XkcdItemUiState(
                                    id = it.xkcdId,
                                    title = it.title,
                                    alt = it.alt,
                                    image = it.image
                                )
                            },
                            isLoading = false
                        )
                    }
                    is DataResultState.Error -> {
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, error = it.message.toString())
                    }
                }
            }
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            removeFavoriteUseCase(id)
        }
    }

}