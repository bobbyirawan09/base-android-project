package bobby.irawan.moviecatalogue.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Bobby Irawan on 25/10/20.
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url =
            chain.request().url.newBuilder().addQueryParameter("KEY HERE", "VALUE HERE").build()
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(url)
                .build()
        )
    }
}