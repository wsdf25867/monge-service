package monster.monge.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccountRegisteredTest {

    @Test
    fun `AccountRegistered의 providerId는 Account의 providerId를 반환한다`() {
        val account = Account("test@test.test", "testProviderId", 1L)
        val event = AccountRegistered(account)

        assertThat(event.providerId).isEqualTo("testProviderId")
    }

    @Test
    fun `AccountRegistered의 userId는 Account의 id를 문자열로 반환한다`() {
        val account = Account("test@test.test", "testProviderId", 42L)
        val event = AccountRegistered(account)

        assertThat(event.userId).isEqualTo("42")
    }

    @Test
    fun `AccountRegistered의 userId는 id가 null이면 null 문자열을 반환한다`() {
        val account = Account("test@test.test", "testProviderId", null)
        val event = AccountRegistered(account)

        assertThat(event.userId).isEqualTo("null")
    }
}
