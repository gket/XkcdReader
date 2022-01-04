package com.gketdev.xkcdreader.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gketdev.xkcdreader.R
import com.gketdev.xkcdreader.base.BaseFragment
import com.gketdev.xkcdreader.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private var item = XkcdItemUiState()

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
                        item = it.xkcdItem
                        setUiItem(item.image, item.title, item.alt)
                        binding?.progressBar?.visibility = View.GONE
                    }
                }

                controlLatestItem(it.isLatestItem)

                controlFavorite(it.isFavorited)
            }
        }
    }

    private fun controlLatestItem(isLatestItem: Boolean) {
        if (isLatestItem) {
            binding?.imageViewNext?.visibility = View.GONE
        } else {
            binding?.imageViewNext?.visibility = View.VISIBLE
        }
    }

    private fun onListener() {
        binding?.imageViewPrev?.setOnClickListener {
            viewModel.getItemDetailById(-1)
        }
        binding?.imageViewNext?.setOnClickListener {
            viewModel.getItemDetailById(1)
        }
        binding?.imageViewFav?.setOnClickListener {
            viewModel.favoriteProcess(item)
        }
        binding?.textViewSeeDetail?.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToExplanationDialog(item.id, item.title)
            findNavController().navigate(direction)
        }
        binding?.imageViewShare?.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(item.image))
                type = "image/jpeg"
            }
            startActivity(Intent.createChooser(shareIntent, null))
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

    private fun controlFavorite(isFavorited: Boolean) {
        if (isFavorited) {
            binding?.imageViewFav?.setImageResource(R.drawable.ic_favorited)
        } else {
            binding?.imageViewFav?.setImageResource(R.drawable.ic_favorite)
        }
    }

}