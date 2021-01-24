package info.metaboy.shorty.controllers


import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.reactive.result.view.RedirectView

@Controller
class WebController(private val recordRepository: RecordRepository) {
    @GetMapping(value = ["/{slug}"])
    fun resolve(@PathVariable slug: String): RedirectView {
        val thing = recordRepository.findBySlug(slug)
        return if (thing != null) RedirectView(
            thing.destination,
            HttpStatus.PERMANENT_REDIRECT
        ) else RedirectView("/404", HttpStatus.PERMANENT_REDIRECT)

    }

    @GetMapping(value = ["/404"])
    fun notFound(model: Model): String {
        return "404"
    }
}