package com.example.flow.data

import com.example.flow.common.createRetrofit
import com.example.flow.common.enqueueResponse
import com.example.flow.data.service.PokeService
import com.example.flow.mocks.mockPokeResultResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

class PokeServiceTest {

    private val server = MockWebServer()
    private val baseUrl = server.url("/").toString()
    private val retrofit = createRetrofit(baseUrl)
    private val service = retrofit.create(PokeService::class.java)

    @Test
    fun `pokemons service should return success pokemon list response when api return success`() {
        // Given
        val listExpected = mockPokeResultResponse
        server.enqueueResponse("pokemons.json", 200)

        // Then
        runBlocking {
            val responseBody = service.pokemons()
            assertThat(responseBody).isEqualTo(listExpected)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
