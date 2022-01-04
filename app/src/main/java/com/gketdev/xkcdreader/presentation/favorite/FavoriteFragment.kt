package com.gketdev.xkcdreader.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gketdev.xkcdreader.base.BaseFragment
import com.gketdev.xkcdreader.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, attachToParent)
    }
}