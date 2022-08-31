package com.example.flow.data.source

import app.cash.turbine.test
import com.example.flow.common.MockWebServerTestRule
import com.example.flow.common.dispatchRequest
import com.example.flow.common.startOn
import com.example.flow.common.with
import com.example.flow.data.service.PokeService
import com.example.flow.mocks.mockPokeResultResponse
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


internal class PokeDataSourceTest {

    @get:Rule
    val mockWebServer = MockWebServerTestRule()

    private val service = mockWebServer.retrofit().create(PokeService::class.java)
    private val dataSource = PokeDataSourceImpl(service)

    @Test
    fun `pokeFlow should emit a poke response flow when called`() {
        // Given
        val listExpected = mockPokeResultResponse
        dispatchRequest { 200 with "pokemons.json" startOn mockWebServer.server }

        // when
        val result = dataSource.pokeFlow()

        // Then
        runBlocking {
            result.test {
                assertEquals(listExpected, awaitItem())
                awaitComplete()
            }
        }
    }
}