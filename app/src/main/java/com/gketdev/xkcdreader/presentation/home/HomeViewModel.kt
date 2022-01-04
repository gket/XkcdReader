package com.gketdev.xkcdreader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.domain.AddToFavoriteUseCase
import com.gketdev.xkcdreader.domain.DeleteToFavoriteUseCase
import com.gketdev.xkcdreader.domain.GetItemUseCase
import com.gketdev.xkcdreader.domain.IsItemFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase,
    private val isItemFavoriteUseCase: IsItemFavoriteUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteToFavoriteUseCase: DeleteToFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(XkcdItemUiState()))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var lastCollectedNum = 0
    private var latestNum = 0

    init {
        getXkdcItem(null)
    }

    private fun getXkdcItem(id: Int?) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getItemUseCase.invoke(id).collect {
                when (it) {
                    is DataResultState.Success -> {
                        val item = it.data
                        if (id == null) {
                            latestNum = item.num
                            _uiState.value = _uiState.value.copy(
                                xkcdItem = setXkcdItem(item),
                                isLoading = false,
                                isLatestItem = true,
                                isFavorited = isItemFavorited(item.num)
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                xkcdItem = setXkcdItem(item),
                                isLoading = false,
                                isLatestItem = false,
                                isFavorited = isItemFavorited(item.num)
                            )
                        }
                        lastCollectedNum = item.num
                    }
                    is DataResultState.Error -> {
                        _uiState.value =
                            _uiState.value.copy(isLoading = false, error = it.message.toString())
                    }
                }
            }
        }
    }

    private fun setXkcdItem(item: XkcdResponse): XkcdItemUiState {
        return XkcdItemUiState(
            id = item.num,
            title = item.title,
            image = item.img,
            alt = item.alt,
        )
    }

    fun getItemDetailById(additionNum: Int) {
        lastCollectedNum += additionNum
        if (lastCollectedNum != latestNum)
            getXkdcItem(lastCollectedNum)
        else
            getXkdcItem(null)
    }

    private suspend fun isItemFavorited(id: Int): Boolean {
        return isItemFavoriteUseCase(id)
    }

    fun favoriteProcess(xkcdItemUiState: XkcdItemUiState) {
        val isFavorite = _uiState.value.isFavorited
        viewModelScope.launch {
            if (!isFavorite) {
                addToFavoriteUseCase.invoke(
                    XkcdEntity(
                        xkcdId = xkcdItemUiState.id,
                        xkcdItemUiState.title,
                        xkcdItemUiState.alt,
                        xkcdItemUiState.image
                    )
                )
                _uiState.value = _uiState.value.copy(isFavorited = true)
            } else {
                deleteToFavoriteUseCase.invoke(xkcdItemUiState.id)
                _uiState.value = _uiState.value.copy(isFavorited = false)
            }
        }
    }

}