package monster.monge.account.application

import monster.monge.account.application.required.AccountRepository
import monster.monge.account.domain.Account
import monster.monge.account.domain.AccountRegisterRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AccountModifyServiceTest {

    @Mock
    lateinit var accountRepository: AccountRepository

    @InjectMocks
    lateinit var accountModifyService: AccountModifyService

    @Test
    fun `register는 Account를 생성하고 저장한다`() {
        val request = AccountRegisterRequest("test@test.test", "testProviderId")
        val savedAccount = Account("test@test.test", "testProviderId", 1L)
        `when`(accountRepository.save(any(Account::class.java))).thenReturn(savedAccount)

        val result = accountModifyService.register(request)

        assertThat(result.email).isEqualTo("test@test.test")
        assertThat(result.providerId).isEqualTo("testProviderId")
        assertThat(result.id).isEqualTo(1L)
        verify(accountRepository).save(any(Account::class.java))
    }
}
