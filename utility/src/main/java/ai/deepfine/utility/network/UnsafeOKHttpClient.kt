package ai.deepfine.utility.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

object UnsafeOKHttpClient {
  private const val CONNECT_TIMEOUT = 60L
  private const val WRITE_TIMEOUT = 60L
  private const val READ_TIMEOUT = 60L

  private val unSafeTrustAllCerts =
    arrayOf<TrustManager>(
      object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
          chain: Array<X509Certificate>,
          authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
          chain: Array<X509Certificate>,
          authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
          return arrayOf()
        }
      }
    )

  private fun createSSLSocketFactory(): SSLSocketFactory {
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, unSafeTrustAllCerts, SecureRandom())

    return sslContext.socketFactory
  }

  fun createUnsafeOkHttpClient(debug: Boolean = false): OkHttpClient = OkHttpClient.Builder().apply {
    sslSocketFactory(
      createSSLSocketFactory(),
      (unSafeTrustAllCerts[0] as X509TrustManager)
    )
    hostnameVerifier(HostnameVerifier { _, _ -> true })
    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    retryOnConnectionFailure(true)
    addInterceptor { chain: Interceptor.Chain ->
      val request = chain.request()
      chain.proceed(
        request.newBuilder().build()
      )
    }

    if (debug) {
      addInterceptor(
        HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.BODY
        }
      )
    }
  }.build()
}