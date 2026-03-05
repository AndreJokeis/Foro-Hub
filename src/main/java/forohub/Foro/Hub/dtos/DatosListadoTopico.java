package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Status;
import forohub.Foro.Hub.objects.Topico;

import java.time.LocalDate;

public record DatosListadoTopico(
        long id,
        String titulo,
        String mensaje,
        LocalDate fechaDeCreacion,
        Status status
) {
    public DatosListadoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus()
        );
    }
}
