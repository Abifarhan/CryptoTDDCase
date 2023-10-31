package com.ourproject.cryptotddcase

import com.ourproject.cryptotddcase.api.Connectivity
import com.ourproject.cryptotddcase.api.HttpClient
import com.ourproject.cryptotddcase.api.LoadCryptoFeedRemoteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test




class LoadCryptoFeedRemoteUseCaseTest {


    @Test
    fun testInitDoesNotRequestData() {
        val (_, client) = makeSut()


        assertTrue(client.getCount == 0)
    }

    @Test
    fun testLoadRequestsData() {
        // Given
        val (sut, client) = makeSut()


        // When
        sut.load()

        // Then
        assertEquals(1, client.getCount)
    }

    @Test
    fun testLoadTwiceRequestsData() {
        // Given
        val (sut, client) = makeSut()


        // When
        sut.load()
        sut.load()

        // Then
        assertEquals(2, client.getCount)
    }

    @Test
    fun testLoadDeliversErrorOnClientError() = runBlocking {
        val (sut, client) = makeSut()

        client.error = Exception("Test")
        var capturedError: Exception? = null
        sut.load().collect {error ->
            capturedError = error
        }

        assertEquals(Connectivity::class.java, capturedError?.javaClass)
    }

    private fun makeSut(): Pair<LoadCryptoFeedRemoteUseCase, HttpClientSpy> {
        val client = HttpClientSpy()
        val sut = LoadCryptoFeedRemoteUseCase(client = client)

        return Pair(sut, client)
    }

    private class HttpClientSpy : HttpClient {
        var getCount = 0
        var error: Exception? = null


        override fun get(): Flow<Exception> = flow {
            if (error != null) {
                emit(error ?: Exception())
            }
            getCount += 1
        }
    }

}


