package com.example.flow.data

import com.example.flow.common.MockWebServerTestRule
import com.example.flow.common.dispatchRequest
import com.example.flow.common.startOn
import com.example.flow.common.with
import com.example.flow.data.service.PokeService
import com.example.flow.mocks.mockPokeResultResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class PokeServiceTest {

    @get:Rule
    val mockWebServer = MockWebServerTestRule()

    private val service = mockWebServer.retrofit().create(PokeService::class.java)

    @Test
    fun `pokemons service should return success pokemon list response when api return success`() {
        // Given
        val listExpected = mockPokeResultResponse
        dispatchRequest { 200 with "pokemons.json" startOn mockWebServer.server }

        // Then
        runBlocking {
            val responseBody = service.pokemons()
            assertThat(responseBody).isEqualTo(listExpected)
        }
    }
}
