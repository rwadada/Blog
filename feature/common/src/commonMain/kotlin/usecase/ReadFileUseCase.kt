package usecase

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response


class ReadFileUseCase {
    suspend operator fun invoke(path: String): String {
        return window.fetch(path).await<Response>().text().await<JsString>().toString()
    }
}
