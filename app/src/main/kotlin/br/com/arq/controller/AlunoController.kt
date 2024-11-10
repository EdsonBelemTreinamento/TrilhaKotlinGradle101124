package br.com.arq.controller



import br.com.arq.dto.AlunoDTO
import br.com.arq.service.AlunoService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/alunos")
class AlunoController(private val alunoService: AlunoService) {


    @PostMapping("/save")
    fun addAlunoWithTurmas(@RequestBody alunoDTO: AlunoDTO): Mono<AlunoDTO> {
        return alunoService.addAlunoWithTurmas(alunoDTO)
    }


    @GetMapping("/lista")
    fun listAllAlunos(): Flux<AlunoDTO> {
        return alunoService.listAllAlunos()
    }


    @PutMapping("/{alunoId}")
    fun updateAluno(@PathVariable alunoId: Long, @RequestBody alunoDTO: AlunoDTO): Mono<AlunoDTO> {
        return alunoService.updateAluno(alunoId, alunoDTO)
    }


    @DeleteMapping("/{alunoId}")
    fun deleteAluno(@PathVariable alunoId: Long): Mono<Void> {
        return alunoService.deleteAluno(alunoId)
    }
}
