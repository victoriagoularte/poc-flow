package com.example.flow.presentation.flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.flow.databinding.ActivityFlowBinding
import com.example.flow.presentation.components.PokeCardAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowBinding
    private val viewModel: FlowViewModel by viewModel()
    private val adapter = PokeCardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    with(binding) {
                        tvInfo.text = "Collecting data.."
                        tvInfo.isVisible = it.isLoading
                        rvPokeList.adapter = adapter
                        adapter.submitList(it.pokemons)
                    }
                }
            }
        }
    }
}