package com.sina.data.network

import com.sina.data.di.dataModule
import com.sina.data.model.ProductDto
import com.sina.domain.model.Product
import com.sina.domain.network.NetworkService
import com.sina.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import java.io.IOException

class NetworkServiceImpl(val client: HttpClient) : NetworkService {
    private val baseUrl = "https://fakestoreapi.com"
    override suspend fun getProducts(category: String?): ResultWrapper<List<Product>> {
        return makeWebRequest(
            url = if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products",
            method = HttpMethod.Get,
            mapper = { dataModule: List<ProductDto> ->
                dataModule.map { it.toProduct() }
            }
        )
    }

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) -> append(key, value) }
                    })
                }

                headers.forEach { (key, value) ->
                    header(key, value)
                }

                if (body != null) {
                    this.body = body
                }

                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}