package monster.monge.account.domain

data class AccountRegistered(
    val account: Account
) {
    val providerId: String
        get() = account.providerId

    val userId: String
        get() = account.id.toString()
}
