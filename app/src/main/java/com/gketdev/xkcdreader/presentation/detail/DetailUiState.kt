package com.gketdev.xkcdreader.presentation.detail

data class DetailUiState(
    val detailItem: DetailItemUiState,
    val isLoading: Boolean = true,
    val error: String = ""
)
