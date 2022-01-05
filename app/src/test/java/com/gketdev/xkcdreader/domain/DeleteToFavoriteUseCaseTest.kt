package com.gketdev.xkcdreader.domain

import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class DeleteToFavoriteUseCaseTest {

    private lateinit var SUT: DeleteToFavoriteUseCase

    private lateinit var repositoryImpl: XkcdRepository

    private val xkcdEntity = XkcdEntity(
        xkcdId = 1,
        title = "Aaa",
        alt = "bbb",
        image = ""
    )

    private val favList = mutableListOf(xkcdEntity, xkcdEntity.copy(xkcdId = 2))

    @Before
    fun setUp() {
        repositoryImpl = mockk(relaxed = true)
        SUT = DeleteToFavoriteUseCase(repositoryImpl)
    }

    @Test
    fun `when item delete from favorite list should not be in fav list`() = runBlocking {
        val size = favList.size
        coEvery {
            repositoryImpl.deleteItemFavorite(any())
        } answers {
            val item = favList.findLast {
                it.xkcdId == this.arg(0)
            }
            favList.remove(item)
        }
        SUT.invoke(1)
        assert(favList.size == size - 1)
    }
}