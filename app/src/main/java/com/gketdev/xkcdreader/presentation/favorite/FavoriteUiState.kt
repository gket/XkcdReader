package com.gketdev.xkcdreader.presentation.favorite

import com.gketdev.xkcdreader.presentation.home.XkcdItemUiState

data class FavoriteUiState(
    val item: List<XkcdItemUiState>?,
    val error: String = "",
    val isLoading: Boolean = false,
    val emptyStatus: Boolean = false
)
