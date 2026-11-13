package ge.dev.waroffingerskmm.network

data class NetworkConfig(
  val baseUrl: String,
  val versionApi: String = "",
  val networkLogLevel: KtorLogger = KtorLogger.ALL,
) {
  fun getCompleteUrl(path: String): String {
    val url = baseUrl + versionApi + path
    return url
  }
}