package monster.monge.account.adapter.web.clerk

import monster.monge.MongeServiceApplicationTests
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class SvixSignatureValidatorTest(
    private val validator: SvixSignatureValidator
): MongeServiceApplicationTests() {
    @Test
    fun `svixId가 null이면 예외가 발생한다`() {
        assertThatThrownBy {
            validator.verify("payload", null, "sig", "ts")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("svixId cannot be null")
    }

    @Test
    fun `svixSignature가 null이면 예외가 발생한다`() {
        assertThatThrownBy {
            validator.verify("payload", "id", null, "ts")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("svixSignature cannot be null")
    }

    @Test
    fun `svixTimestamp가 null이면 예외가 발생한다`() {
        assertThatThrownBy {
            validator.verify("payload", "id", "sig", null)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("svixTimestamp cannot be null")
    }

    @Test
    fun `잘못된 시그니처인 경우 예외가 발생한다`() {
        // Webhook.verify will throw an exception if verification fails
        assertThatThrownBy {
            validator.verify("payload", "id", "v1,invalid", "123456789")
        }.isNotNull()
    }
}
