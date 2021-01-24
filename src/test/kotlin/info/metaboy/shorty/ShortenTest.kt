package info.metaboy.shorty

import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject

import org.springframework.http.HttpEntity
import java.net.URI

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShortenTest( @Autowired val restTemplate: TestRestTemplate) {
    @Test
    fun `Shorten URL, check status code`() {
        val urlJsonObject = JSONObject()
        urlJsonObject.put("url", "https://www.google.com/")
        val request = HttpEntity<String>(urlJsonObject.toString())
        val urlResult = restTemplate.postForObject<String>(
            URI.create("http://localhost:8080/create"),
            request
        )
        assert(!urlResult.isNullOrEmpty())
    }
}
