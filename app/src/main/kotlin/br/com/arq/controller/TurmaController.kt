package br.com.arq.controller

import br.com.arq.dto.TurmaDTO
import br.com.arq.service.TurmaService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/turmas")
class TurmaController(private val turmaService: TurmaService) {


    @PostMapping
    fun addTurmaWithAlunos(@RequestBody turmaDTO: TurmaDTO): Mono<TurmaDTO> {
        return turmaService.addTurmaWithAlunos(turmaDTO)
    }


    @GetMapping
    fun listAllTurmas(): Flux<TurmaDTO> {
        return turmaService.listAllTurmas()
    }


    @PutMapping("/{turmaId}")
    fun updateTurma(@PathVariable turmaId: Long, @RequestBody turmaDTO: TurmaDTO): Mono<TurmaDTO> {
        return turmaService.updateTurma(turmaId, turmaDTO)
    }


    @DeleteMapping("/{turmaId}")
    fun deleteTurma(@PathVariable turmaId: Long): Mono<Void> {
        return turmaService.deleteTurma(turmaId)
    }
}
