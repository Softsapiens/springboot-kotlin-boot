package softsapiens.kotlin.boot.allinone

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BootApplicationTests(@Autowired val customerRepository: CustomerRepository) {

	@Test
	fun contextLoads() {
		runBlocking {
			Assertions.assertEquals(3, customerRepository.findAll().count())
		}
	}

}
