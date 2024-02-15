package com.akinci.projectfinder.core.network

import com.akinci.projectfinder.core.network.exception.HttpNotFound
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess

suspend inline fun <reified T> HttpResponse.toResponse(): Result<T> {
    return if (status.isSuccess()) {
        Result.success(body<T>())
    } else {
        Result.failure(
            when (status) {
                HttpStatusCode.NotFound -> HttpNotFound()
                else -> Throwable("Serverside error with code: ${status.value}")
            }
        )
    }
}