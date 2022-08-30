package com.example.flow.data.source

import app.cash.turbine.test
import com.example.flow.common.createRetrofit
import com.example.flow.common.enqueueResponse
import com.example.flow.data.service.PokeService
import com.example.flow.mocks.mockPokeResultResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import kotlin.test.assertEquals


internal class PokeDataSourceTest {

    private val server = MockWebServer()
    private val baseUrl = server.url("/").toString()
    private val retrofit = createRetrofit(baseUrl)
    private val service = retrofit.create(PokeService::class.java)
    private val dataSource = PokeDataSourceImpl(service)

    @Test
    fun `pokeFlow should emit a poke response flow when called`() {
        // Given
        val listExpected = mockPokeResultResponse
        server.enqueueResponse("pokemons.json", 200)

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

    @After
    fun tearDown() {
        server.shutdown()
    }
}