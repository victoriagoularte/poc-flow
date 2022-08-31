package com.example.flow.domain.usecase

import app.cash.turbine.test
import com.example.flow.common.MockWebServerTestRule
import com.example.flow.common.dispatchRequest
import com.example.flow.common.startOn
import com.example.flow.common.with
import com.example.flow.data.repository.PokeRepositoryImpl
import com.example.flow.data.repository.toDomain
import com.example.flow.data.service.PokeService
import com.example.flow.data.source.PokeDataSourceImpl
import com.example.flow.mocks.mockPokeResultResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class FlowUseCaseTest {

    @get:Rule
    val mockWebServer = MockWebServerTestRule()

    private val service = mockWebServer.retrofit().create(PokeService::class.java)
    private val dataSource = PokeDataSourceImpl(service)
    private val repository = PokeRepositoryImpl(dataSource)
    private val useCase = FlowUseCase(repository)

    @Test
    fun `invoke should emit a poke response flow when data source was called`() {
        // Given
        val listExpected = mockPokeResultResponse.toDomain()
        dispatchRequest { 200 with "pokemons.json" startOn mockWebServer.server }

        // when
        val result = useCase()

        // Then
        runBlocking {
            result.test {
                assertEquals(listExpected, awaitItem())
                awaitComplete()
            }
        }
    }
}