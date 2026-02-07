package monster.monge.account.application

import monster.monge.MongeServiceApplicationTests
import monster.monge.account.application.provided.AccountRegister
import monster.monge.account.domain.AccountRegisterRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccountRegisterTest(
    private val accountRegister: AccountRegister
): MongeServiceApplicationTests() {

    @Test
    fun `계정 등록이 정상적으로 수행된다`() {
        // given
        val request = AccountRegisterRequest("test@example.com", "provider-id")

        // when
        val result = accountRegister.register(request)

        // then
        assertThat(result.email).isEqualTo("test@example.com")
        assertThat(result.providerId).isEqualTo("provider-id")
    }
}
