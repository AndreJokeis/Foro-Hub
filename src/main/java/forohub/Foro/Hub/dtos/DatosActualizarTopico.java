package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje
) {
}
