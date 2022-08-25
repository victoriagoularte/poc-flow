package com.example.flow.presentation.flowext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.flow.common.onAction
import com.example.flow.common.onStateChange
import com.example.flow.databinding.ActivityFlowBinding
import com.example.flow.presentation.components.PokeCardAdapter
import com.example.flow.presentation.flow.DetailPokemon
import com.example.flow.presentation.flow.PokeUiInteraction
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

        onStateChange(viewModel, ::handleStateChange, ::repeat)
        onAction(viewModel, ::handleInteraction)
    }

    private fun setupListeners() {
        adapter.setOnClickItemListener { pokeName -> viewModel.detailPokemon(pokeName) }
    }

    private fun handleInteraction(pokeUiInteraction: PokeUiInteraction) = when (pokeUiInteraction) {
        is DetailPokemon -> Toast.makeText(this, pokeUiInteraction.name, Toast.LENGTH_SHORT).show()
    }

    private fun handleStateChange(state: PokeUiState) = with(binding) {
        tvInfo.text = "Collecting data.."
        tvInfo.isVisible = state.isLoading
        rvPokeList.adapter = adapter
        adapter.submitList(state.pokemons)
    }

    private fun repeat() {
        viewModel.getPokemons()
    }
}