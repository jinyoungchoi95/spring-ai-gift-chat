package gift.chat.service

import org.springframework.ai.chat.client.ChatClientRequest
import org.springframework.ai.chat.client.ChatClientResponse
import org.springframework.ai.chat.client.advisor.api.CallAdvisor
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain
import mu.KotlinLogging

class LoggerAdvisor : CallAdvisor{
    override fun adviseCall(
        chatClientRequest: ChatClientRequest,
        callAdvisorChain: CallAdvisorChain
    ): ChatClientResponse {
        val userMessage = chatClientRequest.prompt().userMessage.text
        log.info("userMessage=${userMessage} context=${chatClientRequest.context}")

        val start = System.currentTimeMillis()
        val response = callAdvisorChain.nextCall(chatClientRequest)
        val durationMs = System.currentTimeMillis() - start

        log.info("response=${response.chatResponse} durationMs=${durationMs}")
        return response
    }

    override fun getName(): String {
        return this.javaClass.simpleName
    }

    override fun getOrder(): Int {
        return DEFAULT_ORDER
    }

    companion object {
        private val log = KotlinLogging.logger {}
        private const val DEFAULT_ORDER = 0
    }
}
