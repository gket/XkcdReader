package com.gketdev.xkcdreader.data.repository

import app.cash.turbine.test
import com.gketdev.xkcdreader.data.database.entity.XkcdEntity
import com.gketdev.xkcdreader.data.model.ExplanationParse
import com.gketdev.xkcdreader.data.model.ExplanationResponse
import com.gketdev.xkcdreader.data.model.ExplanationText
import com.gketdev.xkcdreader.data.model.XkcdResponse
import com.gketdev.xkcdreader.data.source.LocalDataSource
import com.gketdev.xkcdreader.data.source.RemoteExplanationDataSource
import com.gketdev.xkcdreader.data.source.RemoteXkcdDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class XkcdRepositoryImplTest {

    private lateinit var SUT: XkcdRepositoryImpl

    private lateinit var remoteXkcdDataSource: RemoteXkcdDataSource

    private lateinit var remoteExplanationDataSource: RemoteExplanationDataSource

    private lateinit var localDataSource: LocalDataSource

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

    private val xkcdEntity = XkcdEntity(
        xkcdId = 1,
        title = "Aaa",
        alt = "bbb",
        image = ""
    )

    private val explanationResponse = ExplanationResponse(
        ExplanationParse(
            title = "",
            pageId = 0,
            text = ExplanationText(description = "")
        )
    )

    private val favList = listOf(xkcdEntity, xkcdEntity.copy(xkcdId = 2))

    @Before
    fun setUp() {
        remoteXkcdDataSource = mockk(relaxed = true)
        remoteExplanationDataSource = mockk(relaxed = true)
        localDataSource = mockk(relaxed = true)
        SUT = XkcdRepositoryImpl(remoteXkcdDataSource, remoteExplanationDataSource, localDataSource)
    }

    @Test
    fun `when id null should call latestitem`() = runBlocking {
        coEvery {
            remoteXkcdDataSource.getLatestItem()
        } returns xkcdResponseItem

        val data = SUT.getXkcdItem(null)

        data.test {
            val awaitItem = awaitItem()
            assert(awaitItem == xkcdResponseItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when id is filled should call item by id`() = runBlocking {
        coEvery {
            remoteXkcdDataSource.getItemById(any())
        } returns xkcdResponseItem

        val data = SUT.getXkcdItem(1)

        data.test {
            val awaitItem = awaitItem()
            assert(awaitItem == xkcdResponseItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when item is favorite should call is favorite true`() = runBlocking {
        val list = mutableListOf<XkcdEntity>()
        coEvery {
            localDataSource.addFavorite(any())
        } answers {
            list.add(this.arg(0))
        }
        coEvery {
            localDataSource.isItemFavorited(any())
        } answers {
            println(arg<Int>(0).toString())
            list.any { it.xkcdId == (arg(0) as Int) }
        }
        SUT.addItemFavorite(xkcdEntity)
        println(list)
        val isFavorite = SUT.isItemInFavorited(-5)
        assert(!isFavorite)
        val isFavoriteSec = SUT.isItemInFavorited(xkcdEntity.xkcdId)
        assert(isFavoriteSec)
    }

    @Test
    fun `when explanation item is success should be same`() = runBlocking {
        coEvery {
            remoteExplanationDataSource.getExplanationData(any(), any())
        } returns explanationResponse
        val data = SUT.getExplanationItem(1, "")
        assert(data == explanationResponse)
    }

    @Test
    fun `when favlist is filled item should return success`() = runBlocking {
        coEvery {
            localDataSource.getAllFavoritedItems()
        } returns flowOf(favList)
        val data = SUT.getFavoriteItems()
        data.test {
            val awaitItem = awaitItem()
            assert(awaitItem.isNotEmpty())
            assert(awaitItem.size == 2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when item added to favlist item should be in favlist`() = runBlocking {
        val list = mutableListOf<XkcdEntity>()
        coEvery {
            localDataSource.addFavorite(any())
        } answers {
            list.add(this.arg(0))
        }
        SUT.addItemFavorite(xkcdEntity)
        assert(xkcdEntity in list)
    }
}