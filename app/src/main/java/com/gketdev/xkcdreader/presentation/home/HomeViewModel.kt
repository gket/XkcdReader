package com.gketdev.xkcdreader.presentation.home

import com.gketdev.xkcdreader.domain.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetItemUseCase) {

}