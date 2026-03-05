package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Status;

import java.time.LocalDate;

public record DatosRespuestaTopico (
        long id,
        String titulo,
        String mensaje,
        LocalDate fechaDeCreacion,
        Status status,
        DatosInfoUsuario usuario,
        DatosCurso curso
){
}
