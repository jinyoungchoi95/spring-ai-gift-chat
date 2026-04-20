package gift.chat.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(GiftRecommenderController::class)
@ApplyExtension(SpringExtension::class)
class GiftRecommenderControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) : FunSpec() {

    init {
        context("recommendGift") {
            test("추천된 상품 정보를 응답한다") {
                mockMvc.post("/api/gifts/recommend") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(
                        RecommendGiftRequest(
                            message = "친구 생일 선물 추천해 줘",
                            sessionId = null,
                        )
                    )
                }.andExpect {
                    status { isOk() }
                    jsonPath("$") { value("생일 추천 선물은 케이크") }
                }
            }
        }
    }
}
