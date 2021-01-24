package info.metaboy.shorty.controllers


import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.MalformedURLException
import java.net.URL

@RestController
class RestController(private val recordRepository: RecordRepository) {


    @PostMapping(value = ["/create"])
    fun create(@RequestBody createBody: CreateBody): String {
        try {
            val url: URL = URL(createBody.url)
            val valid = url.toURI()
        } catch (e: MalformedURLException) {
            return "Invalid URL"
        }

        val shortner = Base62TimestampUrlIdentifierGenerator()
        val shortened = shortner.generate()
        val urlRecord = URLRecord(slug = shortened, destination = createBody.url)
        val s = recordRepository.save(urlRecord)
        println(s)
        return shortened


    }


}
