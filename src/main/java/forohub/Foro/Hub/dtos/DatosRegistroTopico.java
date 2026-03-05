package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        LocalDate fechaDeCreacion,
        @NotNull
        Status status,
        @NotNull
        long usuario_id,
        @NotNull
        long curso_id
) {
}
