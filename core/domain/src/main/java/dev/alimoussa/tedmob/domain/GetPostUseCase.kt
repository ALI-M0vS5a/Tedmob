package dev.alimoussa.tedmob.domain

import dev.alimoussa.tedmob.common.network.Dispatcher
import dev.alimoussa.tedmob.common.network.TedmobDispatchers
import dev.alimoussa.tedmob.model.Post
import dev.alimoussa.tedmob.network.TedmobNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val networkDataSource: TedmobNetworkDataSource,
    @Dispatcher(TedmobDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SuspendableUseCase<List<Post>, GetPostsUseCase.Params>(ioDispatcher) {
    override suspend fun performAction(params: Params): List<Post> {
        val networkPosts = networkDataSource.getPots(params.ids)
        return networkPosts.map { networkPost ->
            Post(
                userId = networkPost.userId,
                id = networkPost.id,
                title = networkPost.title,
                body = networkPost.body
            )
        }
    }

    data class Params(val ids: List<String>? = null)
}