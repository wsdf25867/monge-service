package monster.monge.account.adapter.web.clerk

import monster.monge.account.application.provided.AccountRegister
import monster.monge.account.domain.Account
import monster.monge.account.domain.AccountRegisterRequest
import monster.monge.global.config.SecurityConfig
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.assertj.MockMvcTester
import tools.jackson.databind.ObjectMapper

@WebMvcTest(ClerkController::class)
@Import(SecurityConfig::class, ClerkWebConfig::class, ClerkPayloadArgumentResolver::class)
class ClerkControllerTest(
    @MockitoBean private val signatureValidator: SvixSignatureValidator,
    @MockitoBean private val accountRegister: AccountRegister,
    private val objectMapper: ObjectMapper,
    private val tester: MockMvcTester
) {

    @Test
    fun `웹훅이 정상적으로 처리된다`() {
        // given
        val email = "test@example.com"
        val providerId = "user_123"
        val request = AccountRegisterRequest(email, providerId)
        
        val clerkPayload = mapOf(
            "data" to mapOf(
                "email_addresses" to listOf(
                    mapOf("email_address" to email)
                ),
                "id" to providerId
            ),
            "type" to "user.created"
        )

        // when
        `when`(accountRegister.register(request)).thenReturn(Account(email, providerId, 1L))

        //then
        tester.post()
            .uri("/api/webhooks/clerk")
            .header("svix-id", "msg_123")
            .header("svix-signature", "v1,abc")
            .header("svix-timestamp", "1234567890")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clerkPayload))
            .assertThat()
            .hasStatusOk()
    }
}
