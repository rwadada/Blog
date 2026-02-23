package usecase

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response


class ReadFileUseCase {
    private val cache = mutableMapOf<String, String>()

    suspend operator fun invoke(path: String): String {
        return cache.getOrPut(path) {
            window.fetch(path).await<Response>().text().await<JsString>().toString()
        }
    }
}
