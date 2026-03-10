package forohub.Foro.Hub.controllers;

import forohub.Foro.Hub.ValidacionException;
import forohub.Foro.Hub.dtos.*;
import forohub.Foro.Hub.objects.Curso;
import forohub.Foro.Hub.objects.Topico;
import forohub.Foro.Hub.objects.Usuario;
import forohub.Foro.Hub.repositories.CursoRepository;
import forohub.Foro.Hub.repositories.TopicoRepository;
import forohub.Foro.Hub.repositories.UsuarioRepository;
import forohub.Foro.Hub.services.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    TopicoService topicoService;
    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                                UriComponentsBuilder uriComponentsBuilder) throws ValidationException {

        var datosRespuestaTopico = topicoService.agregar(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosRespuestaTopico.id()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 4) Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        System.out.println(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {
        if(!topicoRepository.existsById(id))
            throw new ValidacionException("No existe un topico con el id elegido.");

        Topico topico = topicoRepository.getReferenceById(id);
        Usuario usuario = usuarioRepository.findById(topico.getUsuario().getId()).get();
        Curso curso = cursoRepository.findById(topico.getCurso().getId()).get();
        List<String> perfiles = usuario.getPerfiles().stream()
                .map(perfil -> perfil.getNombre().toString())
                .toList();

        var datosMedico = new DatosRespuestaTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaDeCreacion(), topico.getStatus(),
                new DatosInfoUsuario(usuario.getId(), usuario.getNombre(),
                        usuario.getCorreo()
                ), new DatosCurso(
                curso.getId(), curso.getNombre(), curso.getCategoria()
                )
        );

        return ResponseEntity.ok(datosMedico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico (@RequestBody @Valid DatosActualizarTopico datos){
        topicoService.actualizar(datos);

        return ResponseEntity.ok(datos);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminar();
        return ResponseEntity.noContent().build();
    }
}
