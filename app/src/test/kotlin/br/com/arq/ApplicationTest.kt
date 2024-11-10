package br.com.arq

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest
class ApplicationTest {

    @Test
    fun contextLoads() {
        // Verifica se o contexto do Spring Boot é carregado corretamente
        assertNotNull(this, "O contexto da aplicação deve carregar corretamente.")
    }
}
