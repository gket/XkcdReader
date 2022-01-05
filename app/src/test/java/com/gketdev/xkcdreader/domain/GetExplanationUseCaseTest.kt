package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.model.ExplanationParse
import com.gketdev.xkcdreader.data.model.ExplanationResponse
import com.gketdev.xkcdreader.data.model.ExplanationText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetExplanationUseCaseTest {

    private lateinit var SUT: GetExplanationUseCase

    private lateinit var repositoryImpl: XkcdRepository

    private val explanationResponse = ExplanationResponse(
        ExplanationParse(
            title = "",
            pageId = 0,
            text = ExplanationText(description = "")
        )
    )

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = GetExplanationUseCase(repositoryImpl)
    }

    @Test
    fun `when response is success data should be same`() = runBlocking {
        coEvery {
            repositoryImpl.getExplanationItem(any(), any())
        } returns explanationResponse

        val data = SUT.invoke(Pair(0, ""))

        assert(data == explanationResponse)
    }
}