package com.gketdev.xkcdreader.presentation.home

data class HomeUiState(
    val xkcdItem : XkcdItemUiState,
    val isLoading: Boolean = false,
    val error : String = "",
    val isLatestItem : Boolean = false
)
