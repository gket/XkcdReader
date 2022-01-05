package com.gketdev.xkcdreader.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64.NO_PADDING
import android.util.Base64.encodeToString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gketdev.xkcdreader.databinding.FragmentDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class DetailFragment : DialogFragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        observeData()
        listenEvents()
        viewModel.getExplanationInfo(args.xkcdId, args.xkcdTitle)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.settings.javaScriptEnabled = true
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when {
                    it.detailItem.htmlText.isNotEmpty() -> {

                        binding.webView.loadDataWithBaseURL(
                            null,
                            it.detailItem.htmlText,
                            "text/html; charset=utf-8",
                            "UTF-8",
                            null
                        )
                    }
                }
            }
        }
    }

    private fun listenEvents() {
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

}