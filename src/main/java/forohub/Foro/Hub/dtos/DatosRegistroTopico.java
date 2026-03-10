package forohub.Foro.Hub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        long usuario_id,
        @NotNull
        long curso_id
) {
}
