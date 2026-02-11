package monster.monge.account.adapter.web.clerk

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ClerkClient(
    @Value($$"${clerk.base-url}") private val clerkBaseUrl: String,
    @Value($$"${clerk.api-key}")private val clerkApiKey: String,
) {
    private val restClient = RestClient.create(clerkBaseUrl)

    fun mergeAndUpdate(providerId: String, metadata: Map<String, Any>) {
        restClient.patch()
            .uri("/v1/users/$providerId/metadata")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $clerkApiKey")
            .body(mapOf("public_metadata" to metadata))
            .retrieve()

    }

}
