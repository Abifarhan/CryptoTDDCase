package com.ourproject.cryptotddcase.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HttpClient{
    fun get(): Flow<Exception>
}
class LoadCryptoFeedRemoteUseCase constructor(
    private val client: HttpClient
){

    fun load() : Flow<Exception> = flow{
        client.get().collect {error ->

            when (error) {
                is ConnectivityException -> {
                    emit(Connectivity())
                }
                is InvalidDataException -> {
                    emit(InvalidData())
                }
            }
        }
    }
}


class Connectivity: Exception()
class BadRequest: Exception()

class ConnectivityException : Exception()
class InvalidDataException : Exception()
class InvalidData : Exception()

