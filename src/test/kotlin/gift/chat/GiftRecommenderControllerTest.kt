package gift.chat

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(GiftRecommenderController::class)
@ApplyExtension(SpringExtension::class)
class GiftRecommenderControllerTest(
    private val mockMvc: MockMvc
) : FunSpec() {

    init {
        context("recommendGift") {
            test("추천된 상품 정보를 응답한다") {
                mockMvc.post("/api/gifts/recommend") {
                    contentType = APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                    jsonPath("$") { value("생일 추천 선물은 케이크") }
                }
            }
        }
    }
}
