package com.ourproject.cryptotddcase

import org.junit.Assert.assertTrue
import org.junit.Test


class LoadCryptoFeedRemoteUseCase {

}


class LoadCryptoFeedRemoteUseCaseTest {


    @Test
    fun testInitDoesNotLoad() {
        val client = HttpClient()
        LoadCryptoFeedRemoteUseCase()


        assertTrue(client.getCount == 0)
    }
}

class HttpClient {

    var getCount = 0
}
