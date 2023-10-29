package com.ourproject.cryptotddcase.domain

import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface LoadCryptoFeedRemoteUseCase {

    fun load(): Flow<LoadCryptoFeedResult>
}

sealed class LoadCryptoFeedResult {

    data class Success(val cryptoFeed: List<CryptoFeed>)

    data class Error(val exception: Exception)
}
