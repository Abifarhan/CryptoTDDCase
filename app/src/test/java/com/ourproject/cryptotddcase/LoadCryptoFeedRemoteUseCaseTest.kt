package com.ourproject.cryptotddcase

import app.cash.turbine.test
import com.ourproject.cryptotddcase.api.Connectivity
import com.ourproject.cryptotddcase.api.ConnectivityException
import com.ourproject.cryptotddcase.api.HttpClient
import com.ourproject.cryptotddcase.api.InvalidData
import com.ourproject.cryptotddcase.api.InvalidDataException
import com.ourproject.cryptotddcase.api.LoadCryptoFeedRemoteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test



class LoadCryptoFeedRemoteUseCaseTest {

    private val client = mockk<HttpClient>()
    lateinit var sut : LoadCryptoFeedRemoteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        sut = LoadCryptoFeedRemoteUseCase(client)
    }
    @Test
    fun testInitDoesNotRequestData() {

        verify(exactly = 0) {
            client.get()
        } throws Exception()

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestsData()  = runBlocking{
        every {
            client.get()
        } throws Exception()

        sut.load().test {
            awaitError()
        }

        verify(exactly = 1) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadTwiceRequestsData() = runBlocking{
        // Given
        every {
            client.get()
        } throws Exception()


        // When
        sut.load().test {
            awaitComplete()
        }
        sut.load().test {
            awaitComplete()
        }

        // Then
        verify(exactly = 2) {
            client.get()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversErrorOnClientError() = runBlocking {

        // Given
        every {
            client.get()
        } returns flowOf(ConnectivityException())


        sut.load().test {
            assertEquals(Connectivity::class.java, awaitItem().javaClass)
            awaitComplete()
        }

        verify(exactly = 2) {
            client.get()
        }

        confirmVerified(client)

    }

    @Test
    fun testLoadDeliversInvalidDataErrorOnClientError() = runBlocking {
        every {
            client.get()
        } returns flowOf(InvalidDataException())

        sut.load().test {
            assertEquals(InvalidData::class.java, awaitItem()::class.java)
            awaitComplete()
        }

        verify(exactly = 1){
            client.get()
        }

        confirmVerified(client)

    }


}


