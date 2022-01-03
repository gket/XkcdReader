package com.gketdev.xkcdreader.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gketdev.xkcdreader.base.BaseFragment
import com.gketdev.xkcdreader.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
    }

    private fun onObserve() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when {
                    it.isLoading -> {
                        //TODO loading progress
                    }
                    it.error.isNotEmpty() -> {
                        // Message
                    }
                    it.xkcdItem.id != -1 -> {
                        binding?.imageViewXkcd?.let { iv ->
                            Glide.with(requireContext()).load(it.xkcdItem.image)
                                .into(iv)
                        }
                    }
                }
            }
        }
    }

}