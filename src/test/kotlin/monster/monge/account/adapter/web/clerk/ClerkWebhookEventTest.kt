package monster.monge.account.adapter.web.clerk

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tools.jackson.databind.ObjectMapper

class ClerkWebhookEventTest {

    private val objectMapper = ObjectMapper()

    @Test
    fun `ClerkWebhookEvent JSON 역직렬화가 정상적으로 동작한다`() {
        val json = """
            {
                "data": {
                    "id": "user_123",
                    "email_addresses": [
                        {"email_address": "test@test.test"}
                    ]
                },
                "type": "user.created"
            }
        """.trimIndent()

        val event = objectMapper.readValue(json, ClerkWebhookEvent::class.java)

        assertThat(event.type).isEqualTo("user.created")
        assertThat(event.data.id).isEqualTo("user_123")
        assertThat(event.data.emailAddresses).hasSize(1)
        assertThat(event.data.emailAddresses.first().emailAddress).isEqualTo("test@test.test")
    }

    @Test
    fun `알 수 없는 필드가 있어도 역직렬화에 실패하지 않는다`() {
        val json = """
            {
                "data": {
                    "id": "user_123",
                    "email_addresses": [
                        {"email_address": "test@test.test", "unknown_field": "value"}
                    ],
                    "unknown_field": "value"
                },
                "type": "user.created",
                "unknown_field": "value"
            }
        """.trimIndent()

        val event = objectMapper.readValue(json, ClerkWebhookEvent::class.java)

        assertThat(event.type).isEqualTo("user.created")
        assertThat(event.data.id).isEqualTo("user_123")
    }

    @Test
    fun `여러 이메일 주소가 있을 때 모두 역직렬화된다`() {
        val json = """
            {
                "data": {
                    "id": "user_123",
                    "email_addresses": [
                        {"email_address": "first@test.test"},
                        {"email_address": "second@test.test"}
                    ]
                },
                "type": "user.created"
            }
        """.trimIndent()

        val event = objectMapper.readValue(json, ClerkWebhookEvent::class.java)

        assertThat(event.data.emailAddresses).hasSize(2)
        assertThat(event.data.emailAddresses[0].emailAddress).isEqualTo("first@test.test")
        assertThat(event.data.emailAddresses[1].emailAddress).isEqualTo("second@test.test")
    }
}
