package com.gketdev.xkcdreader.presentation.home

import app.cash.turbine.test
import com.gketdev.xkcdreader.data.model.DataResultState
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.domain.AddToFavoriteUseCase
import com.gketdev.xkcdreader.domain.DeleteToFavoriteUseCase
import com.gketdev.xkcdreader.domain.GetItemUseCase
import com.gketdev.xkcdreader.domain.IsItemFavoriteUseCase
import com.gketdev.xkcdreader.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var SUT: HomeViewModel
    private lateinit var getItemUseCase: GetItemUseCase
    private lateinit var isItemFavoriteUseCase: IsItemFavoriteUseCase
    private lateinit var addToFavoriteUseCase: AddToFavoriteUseCase
    private lateinit var deleteToFavoriteUseCase: DeleteToFavoriteUseCase

    val homeUiState = HomeUiState(XkcdItemUiState())

    private val xkcdResponseItem = XkcdResponse(
        num = 0,
        alt = "",
        title = "",
        month = "",
        year = "",
        day = "",
        transcript = "",
        link = "",
        img = "",
        safe_title = "",
        news = ""
    )

    @Before
    fun setUp() {
        getItemUseCase = mockk(relaxed = true)
        isItemFavoriteUseCase = mockk(relaxed = true)
        addToFavoriteUseCase = mockk(relaxed = true)
        deleteToFavoriteUseCase = mockk(relaxed = true)
        SUT = HomeViewModel(
            getItemUseCase,
            isItemFavoriteUseCase,
            addToFavoriteUseCase,
            deleteToFavoriteUseCase
        )
    }

    @Test
    fun `when get item is success homeuistate should filled`() = runBlocking {
        coEvery {
            getItemUseCase.invoke(any())
        } returns flow {
            emit(DataResultState.Success(xkcdResponseItem))
        }

        SUT.uiState.test {
            SUT.getItemDetailById(1)
            val item = awaitItem()
            assert(item == homeUiState.copy(isLoading = true))
            val secondaryItem = awaitItem()
            assert(
                secondaryItem == homeUiState.copy(
                    xkcdItem = XkcdItemUiState(
                        id = xkcdResponseItem.num,
                        alt = xkcdResponseItem.alt,
                        title = xkcdResponseItem.title,
                        image = xkcdResponseItem.img
                    ),
                )
            )
            cancelAndIgnoreRemainingEvents()
        }


    }

    @Test
    fun `when get item is error homeuistate should be error`() = runBlocking {
        coEvery {
            getItemUseCase.invoke(any())
        } returns flow {
            emit(DataResultState.Error<XkcdResponse>(message = "Error"))
        }

        SUT.uiState.test {
            SUT.getItemDetailById(1)
            val item = awaitItem()
            assert(item == homeUiState.copy(isLoading = true))
            val secondaryItem = awaitItem()
            assert(
                secondaryItem == homeUiState.copy(
                    error = "Error"
                )
            )
            cancelAndIgnoreRemainingEvents()
        }


    }


}