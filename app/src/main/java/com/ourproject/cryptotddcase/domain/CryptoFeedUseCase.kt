package com.ourproject.cryptotddcase.domain

import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface CryptoFeedUseCase {

    fun load(): Flow<CryptoFeedResult>
}

sealed class CryptoFeedResult {

    data class Success(val cryptoFeed: List<CryptoFeed>)

    data class Error(val exception: Exception)
}
