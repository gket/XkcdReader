package com.gketdev.xkcdreader.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gketdev.xkcdreader.base.BaseFragment
import com.gketdev.xkcdreader.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
        onListener()
    }

    private fun onObserve() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when {
                    it.isLoading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    it.error.isNotEmpty() -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        binding?.progressBar?.visibility = View.GONE
                    }
                    it.xkcdItem.id != -1 -> {
                        val item = it.xkcdItem
                        setUiItem(item.image, item.title, item.alt)
                        binding?.progressBar?.visibility = View.GONE
                    }
                }

                if (it.isLatestItem) {
                    binding?.imageViewNext?.visibility = View.GONE
                } else {
                    binding?.imageViewNext?.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun onListener() {
        binding?.imageViewPrev?.setOnClickListener {
            viewModel.getItemDetailById(false)
        }
        binding?.imageViewNext?.setOnClickListener {
            viewModel.getItemDetailById(true)
        }
    }

    private fun setUiItem(url: String, title: String, alt: String) {
        binding?.imageViewXkcd?.let { iv ->
            Glide.with(requireContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv)
        }
        binding?.textViewTitle?.text = title
        binding?.textViewAlt?.text = alt
    }

}