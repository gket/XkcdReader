package com.gketdev.xkcdreader.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.domain.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase
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
                                isLatestItem = true
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                xkcdItem = setXkcdItem(item),
                                isLoading = false,
                                isLatestItem = false
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
            alt = item.alt
        )
    }

    fun getItemDetailById(isNext: Boolean) {
        if (isNext) lastCollectedNum += 1
        else lastCollectedNum -= 1

        if (lastCollectedNum != latestNum)
            getXkdcItem(lastCollectedNum)
        else
            getXkdcItem(null)
    }

}