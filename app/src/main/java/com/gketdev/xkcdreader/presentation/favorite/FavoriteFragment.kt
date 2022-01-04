package com.gketdev.xkcdreader.presentation.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gketdev.xkcdreader.base.BaseFragment
import com.gketdev.xkcdreader.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var adapter: FavoriteAdapter

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObserver()
        initListener()
    }

    private fun initUi() {
        binding?.recyclerView?.adapter = adapter
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when {
                    it.item?.isNotEmpty() == true -> {
                        adapter.favorites = it.item ?: emptyList()
                    }
                    it.item?.isEmpty() == true -> {
                        Toast.makeText(
                            requireContext(),
                            "Your favorite list is empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.favorites = emptyList()
                    }
                    it.isLoading -> {
                        //Loading
                    }
                    it.error.isNotEmpty() -> {
                        Toast.makeText(
                            requireContext(),
                            it.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initListener() {
        adapter.onFavClicked = {
            viewModel.removeFavorite(it)
        }
        adapter.onShareClicked = {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(it))
                type = "image/jpeg"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}