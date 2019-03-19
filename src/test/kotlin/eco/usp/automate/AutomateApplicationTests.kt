package eco.usp.automate

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AutomateApplicationTests {

	@Autowired
	lateinit var env: Environment

	@Test
	fun contextLoads() {
	}

}

