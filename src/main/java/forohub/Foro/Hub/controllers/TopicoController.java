package forohub.Foro.Hub.controllers;

import forohub.Foro.Hub.dtos.*;
import forohub.Foro.Hub.objects.Curso;
import forohub.Foro.Hub.objects.Topico;
import forohub.Foro.Hub.objects.Usuario;
import forohub.Foro.Hub.repositories.CursoRepository;
import forohub.Foro.Hub.repositories.TopicoRepository;
import forohub.Foro.Hub.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
//@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;


    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuario = usuarioRepository.findById(datos.usuario_id()).get();
        Curso curso = cursoRepository.findById(datos.curso_id()).get();
        List<String> perfiles = usuario.getPerfiles().stream()
                .map(perfil -> perfil.getNombre().toString())
                .toList();

        Topico topico = topicoRepository.save(new Topico(datos, usuario, curso));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaDeCreacion(), topico.getStatus(),
                new DatosInfoUsuario(usuario.getId(), usuario.getNombre(),
                        usuario.getCorreo()
                ), new DatosCurso(curso.getId(), curso.getNombre(), curso.getCategoria()
                )
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
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
        Topico topico = topicoRepository.getReferenceById(datos.id());
        topico.actualizar(datos);

        Usuario usuario = usuarioRepository.findById(topico.getUsuario().getId()).get();
        Curso curso = cursoRepository.findById(topico.getCurso().getId()).get();
        List<String> perfiles = usuario.getPerfiles().stream()
                .map(perfil -> perfil.getNombre().toString())
                .toList();

        return ResponseEntity.ok(
                new DatosRespuestaTopico(
                        topico.getId(), topico.getTitulo(), topico.getMensaje(),
                        topico.getFechaDeCreacion(), topico.getStatus(),
                        new DatosInfoUsuario(usuario.getId(), usuario.getNombre(),
                                usuario.getCorreo()
                        ), new DatosCurso(
                                curso.getId(), curso.getNombre(), curso.getCategoria()
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminar();
        return ResponseEntity.noContent().build();
    }
}
