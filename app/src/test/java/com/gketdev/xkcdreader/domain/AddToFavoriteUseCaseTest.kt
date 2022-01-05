package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AddToFavoriteUseCaseTest {

    lateinit var SUT: AddToFavoriteUseCase

    lateinit var repositoryImpl: XkcdRepository

    private val xkcdEntity = XkcdEntity(
        xkcdId = 1,
        title = "Aaa",
        alt = "bbb",
        image = ""
    )

    private val favList = listOf(xkcdEntity, xkcdEntity.copy(xkcdId = 2))

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = AddToFavoriteUseCase(repositoryImpl)
    }

    @Test
    fun `when item add to favorite list should be in fav list`() = runBlocking {
        val list = mutableListOf<XkcdEntity>()
        coEvery {
            repositoryImpl.addItemFavorite(any())
        } answers {
            list.add(this.arg(0))
        }
        SUT.invoke(xkcdEntity)
        assert(xkcdEntity in list)
    }
}