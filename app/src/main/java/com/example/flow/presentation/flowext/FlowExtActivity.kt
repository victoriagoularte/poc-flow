package com.example.flow.presentation.flowext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.flow.common.viewmodel.onInteraction
import com.example.flow.common.viewmodel.onStateChange
import com.example.flow.databinding.ActivityFlowBinding
import com.example.flow.presentation.components.PokeCardAdapter
import com.example.flow.presentation.flow.DetailPokemon
import com.example.flow.presentation.flow.PokeUiEffects
import com.example.flow.presentation.flow.PokeUiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlowExtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowBinding
    private val viewModel: FlowExtViewModel by viewModel()
    private val adapter = PokeCardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()

        onStateChange(viewModel, ::handleStateChange, ::repeatOnStarted)
        onInteraction(viewModel, ::handleEffect)
    }

    private fun setupListeners() {
        adapter.setOnClickItemListener { pokeName -> viewModel.detailPokemon(pokeName) }
    }

    private fun handleEffect(pokeUiEffects: PokeUiEffects) = when (pokeUiEffects) {
        is DetailPokemon -> Toast.makeText(this, pokeUiEffects.name, Toast.LENGTH_SHORT).show()
    }

    private fun handleStateChange(state: PokeUiState) = with(binding) {
        tvInfo.text = "Collecting data.."
        tvInfo.isVisible = state.isLoading
        rvPokeList.adapter = adapter
        adapter.submitList(state.pokemons)
    }

    private fun repeatOnStarted() {
        viewModel.getPokemons()
    }
}