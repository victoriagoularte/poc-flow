package com.example.flow.presentation.flowext

import com.example.flow.common.DispatcherRule
import com.example.flow.data.repository.toDomain
import com.example.flow.domain.usecase.FlowUseCase
import com.example.flow.mocks.mockPokeResultResponse
import com.example.flow.presentation.flow.DetailPokemon
import com.example.flow.presentation.flow.PokeUiEffects
import com.example.flow.presentation.flow.PokeUiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class FlowExtViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private val useCase: FlowUseCase = mockk()
    private val dispatcher = UnconfinedTestDispatcher()

    private val viewModel by lazy { FlowExtViewModel(useCase = useCase, dispatcher = dispatcher) }

    @Test
    fun `getPokemons should set correct states when useCase returns success`() = runBlocking {
        // Given
        val listExpected = mockPokeResultResponse.toDomain()
        val initialState = PokeUiState()
        val loadingState = initialState.showLoading()
        val successState = loadingState.showPokemons(listExpected)

        every { useCase() } returns flowOf(listExpected)

        val testResults = arrayListOf<PokeUiState>()
        val job = launch(dispatcher) { viewModel.state.toList(destination = testResults) }

        // When
        viewModel.getPokemons()

        // Then
        assertEquals(3, testResults.size)
        assertEquals(initialState, testResults[0])
        assertEquals(loadingState, testResults[1])
        assertEquals(successState, testResults[2])

        job.cancel()
    }

    @Test
    fun `getPokemons should set correct states when useCase returns error`() = runBlocking {
        // Given
        val initialState = PokeUiState()
        val loadingState = initialState.showLoading()
        val errorState = loadingState.showError()

        every { useCase() } returns flow { throw Exception() }

        val testResults = arrayListOf<PokeUiState>()
        val job = launch(dispatcher) { viewModel.state.toList(destination = testResults) }

        // When
        viewModel.getPokemons()

        // Then
        assertEquals(3, testResults.size)
        assertEquals(initialState, testResults[0])
        assertEquals(loadingState, testResults[1])
        assertEquals(errorState, testResults[2])

        job.cancel()
    }

    @Test
    fun `detailPokemon should trigger side effect DetailPokemon`() = runBlocking {
        //Given
        val pokeName = "some name"

        val testResults = arrayListOf<PokeUiEffects>()
        val job = launch(dispatcher) {
            viewModel.effect.toList(destination = testResults)
        }

        // When
        viewModel.detailPokemon(pokeName)

        // Then
        assertEquals(1, testResults.size)
        assertTrue(testResults[0] is DetailPokemon)

        job.cancel()
    }
}