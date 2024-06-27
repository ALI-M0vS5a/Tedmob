package dev.alimoussa.tedmob.network.retrofit

import androidx.tracing.trace
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.alimoussa.tedmob.network.TedmobNetworkDataSource
import dev.alimoussa.tedmob.network.model.NetworkPost
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface RetrofitTedmobNetworkApi {
    @GET("/posts/")
    suspend fun getPosts(): List<NetworkPost>
}

private const val TEDMOB_BASE_URL = "https://jsonplaceholder.typicode.com/posts/"

@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

@Singleton
internal class RetrofitNiaNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : TedmobNetworkDataSource {

    private val networkApi = trace("RetrofitTedmobNetwork") {
        Retrofit.Builder()
            .baseUrl(TEDMOB_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitTedmobNetworkApi::class.java)
    }

    override suspend fun getPots(): List<NetworkPost> =
        networkApi.getPosts()

}