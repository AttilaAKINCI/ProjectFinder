package com.akinci.projectfinder.data.projects

import com.akinci.projectfinder.core.application.AppConfig
import com.akinci.projectfinder.core.network.HttpClientFactory
import com.akinci.projectfinder.core.network.HttpEngineFactoryMock
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.http.HttpStatusCode
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectRepositoryTest {
    private val appConfigMock: AppConfig = mockk(relaxed = true)

    private val httpEngineFactory = HttpEngineFactoryMock()
    private val httpClientFactory = HttpClientFactory(
        httpEngineFactory = httpEngineFactory,
        appConfig = appConfigMock,
    )

    private lateinit var testedClass: ProjectRepository

    @BeforeEach
    fun setup() {
        testedClass = ProjectRepository(httpClientFactory.create())
    }

    @Test
    fun `should return repository list when successful response`() = runTest {
        httpEngineFactory.statusCode = HttpStatusCode.OK

        val result = testedClass.getProjectRepositories(repositoryOwnerName = "AttilaAKINCI")

        result.isSuccess shouldBe true
        result.getOrNull()!![0].id shouldBe 353730900
        result.getOrNull()!![0].name shouldBe "Chatter"
    }

    @Test
    fun `should return failure with code 404 when not found response`() = runTest {
        httpEngineFactory.statusCode = HttpStatusCode.NotFound

        val result = testedClass.getProjectRepositories(repositoryOwnerName = "AttilaAKINCI1")

        result.isFailure shouldBe true
        result.exceptionOrNull()!!.message shouldContain "code: 404"
    }

    @Test
    fun `should avoid from exception when http client throws an exception`() = runTest {
        httpEngineFactory.statusCode = HttpStatusCode.BadRequest
        httpEngineFactory.simulateException = true

        val result = testedClass.getProjectRepositories(repositoryOwnerName = "AttilaAKINCI")

        result.isFailure shouldBe true
        result.exceptionOrNull()!! shouldBe Throwable("Simulated Network Exception")
    }
}