package dev.alimoussa.tedmob.domain


import dev.alimoussa.tedmob.common.network.Dispatcher
import dev.alimoussa.tedmob.common.network.TedmobDispatchers
import dev.alimoussa.tedmob.model.Post
import dev.alimoussa.tedmob.network.TedmobNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Use case for fetching posts from a network data source.
 * Inherits from SuspendableUseCase and uses a dummy parameter for compliance with Kotlin's data class requirements.
 */
class GetPostsUseCase @Inject constructor(
    private val networkDataSource: TedmobNetworkDataSource,
    @Dispatcher(TedmobDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SuspendableUseCase<List<Post>, GetPostsUseCase.Params>(ioDispatcher) {

    override suspend fun performAction(params: Params): List<Post> {
        val networkPosts = networkDataSource.getPots()
        return networkPosts.map { networkPost ->
            Post(
                userId = networkPost.userId,
                id = networkPost.id,
                title = networkPost.title,
                body = networkPost.body
            )
        }
    }

    /**
     * Parameters for fetching posts. Currently contains a dummy parameter.
     */
    data class Params(val dummy: Boolean = true)
}
