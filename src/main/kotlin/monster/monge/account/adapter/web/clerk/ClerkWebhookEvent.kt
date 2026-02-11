package monster.monge.account.adapter.web.clerk

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ClerkWebhookEvent(
    val data: ClerkUserData,
    val type: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ClerkUserData(
    val id: String,
    @JsonProperty("email_addresses") val emailAddresses: List<ClerkEmail>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ClerkEmail(
    @JsonProperty("email_address") val emailAddress: String
)
