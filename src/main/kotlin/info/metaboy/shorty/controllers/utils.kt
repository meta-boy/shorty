package info.metaboy.shorty.controllers

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.lang.Math.pow
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

@Document
data class URLRecord(
    @Id
    val slug: String,
    val destination: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),

    )

interface RecordRepository : MongoRepository<URLRecord, String> {
    fun findBySlug(slug: String): URLRecord?

}

data class CreateBody @JsonCreator constructor(
    val url: String
)


class Base62TimestampUrlIdentifierGenerator {
    private var counter = AtomicInteger()
    fun generate(): String {
        val counterValue = counter.getAndUpdate { operand -> (operand + 1) % 1000 }
        val base10Id = java.lang.Long.valueOf("" + counterValue + System.currentTimeMillis())
        return Base62.fromBase10(base10Id)
    }
}

object Base62 {
    val ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val BASE = ALPHABET.length
    fun fromBase10(j: Long): String {
        val sb = StringBuilder("")
        var i = j
        while (i > 0) {
            i = fromBase10(i, sb)
        }
        return sb.reverse().toString()
    }

    private fun fromBase10(i: Long, sb: StringBuilder): Long {
        val rem = (i % BASE).toInt()
        sb.append(ALPHABET.get(rem))
        return i / BASE
    }

    fun toBase10(str: String): Long {
        return toBase10(StringBuilder(str).reverse().toString().toCharArray())
    }

    private fun toBase10(chars: CharArray): Long {
        var n: Long = 0
        for (i in chars.indices.reversed()) {
            n += toBase10(ALPHABET.indexOf(chars[i]).toLong(), i.toLong())
        }
        return n
    }

    private fun toBase10(n: Long, pow: Long): Long {
        return n * pow(BASE.toDouble(), pow.toDouble()).toLong()
    }

}