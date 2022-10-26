package softsapiens.kotlin.boot.allinone

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class BootApplication {
    @Bean
    fun runner(repository: CustomerRepository) = ApplicationRunner {
        runBlocking {
            val customers = flowOf("John", "El", "Foo")
                .map { Customer(null, it) }
            repository.saveAll(customers)
                .collect{ println(it) }
        }
    }

    @Bean
    fun http(repository: CustomerRepository) = coRouter {
        GET("/customers") {
            ServerResponse.ok().bodyAndAwait(repository.findAll())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<BootApplication>(*args)
}

interface CustomerRepository : CoroutineCrudRepository<Customer, Int>

data class Customer(@Id val id: Int?, val name: String)

