package com.akinci.projectfinder.core.network

import com.akinci.projectfinder.core.network.json.ProjectListServiceResponse
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import java.nio.charset.Charset

class HttpEngineFactoryMock : HttpEngineFactory() {

    private val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")
    var statusCode: HttpStatusCode = OK
    var simulateException = false

    override fun create(): MockEngine {
        return MockEngine {
            if(simulateException){
                throw Throwable("Simulated Network Exception")
            }

            respond(
                content = getProjectListContent(statusCode),
                status = statusCode,
                headers = responseHeaders,
            )
        }
    }

    private fun getProjectListContent(statusCode: HttpStatusCode): ByteArray {
        return when (statusCode) {
            OK -> ProjectListServiceResponse.success.toByteArray(Charset.defaultCharset())
            NotFound -> ProjectListServiceResponse.fail.toByteArray(Charset.defaultCharset())
            else -> statusCode.description.toByteArray()
        }
    }
}