package forohub.Foro.Hub.services;

import forohub.Foro.Hub.ValidacionException;
import forohub.Foro.Hub.dtos.*;
import forohub.Foro.Hub.objects.Curso;
import forohub.Foro.Hub.objects.Topico;
import forohub.Foro.Hub.objects.Usuario;
import forohub.Foro.Hub.repositories.CursoRepository;
import forohub.Foro.Hub.repositories.TopicoRepository;
import forohub.Foro.Hub.repositories.UsuarioRepository;
import forohub.Foro.Hub.services.validations.ValidadorActualizar;
import forohub.Foro.Hub.services.validations.Validador;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    private List<Validador> validadores;
    @Autowired
    private List<ValidadorActualizar> validadoresActualizar;

    public DatosRespuestaTopico agregar(DatosRegistroTopico datos) throws ValidationException {
        if(!usuarioRepository.existsById(datos.usuario_id()))
            throw new ValidationException("No existe un usuario con este id.");
        if(!cursoRepository.existsById(datos.curso_id()))
            throw new ValidationException("No existe un usuario con este id.");

        // Validaciones
        validadores.forEach(v -> v.validar(datos));

        Usuario usuario = usuarioRepository.findById(datos.usuario_id()).get();
        Curso curso = cursoRepository.findById(datos.curso_id()).get();

        List<String> perfiles = usuario.getPerfiles().stream()
                .map(perfil -> perfil.getNombre().toString())
                .toList();

        Topico topico = topicoRepository.save(new Topico(datos, usuario, curso));

        return new DatosRespuestaTopico(
                topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaDeCreacion(), topico.getStatus(),
                new DatosInfoUsuario(usuario.getId(), usuario.getNombre(),
                        usuario.getCorreo()
                ), new DatosCurso(curso.getId(), curso.getNombre(), curso.getCategoria()
                )
        );
    }

    public DatosRespuestaTopico actualizar(DatosActualizarTopico datos){
        if(!usuarioRepository.existsById(datos.id()))
            throw new ValidacionException("No existe ningún tópico con el id elegido.");

        validadoresActualizar.forEach(v -> v.validar(datos));

        Topico topico = topicoRepository.getReferenceById(datos.id());
        topico.actualizar(datos);

        Usuario usuario = usuarioRepository.findById(topico.getUsuario().getId()).get();
        Curso curso = cursoRepository.findById(topico.getCurso().getId()).get();
        List<String> perfiles = usuario.getPerfiles().stream()
                .map(perfil -> perfil.getNombre().toString())
                .toList();

        return
            new DatosRespuestaTopico(
                    topico.getId(), topico.getTitulo(), topico.getMensaje(),
                    topico.getFechaDeCreacion(), topico.getStatus(),
                    new DatosInfoUsuario(usuario.getId(), usuario.getNombre(),
                            usuario.getCorreo()
                    ), new DatosCurso(
                            curso.getId(), curso.getNombre(), curso.getCategoria()
                    )
            );
    }
}
