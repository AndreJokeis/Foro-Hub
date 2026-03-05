package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DatosCurso(
        long id,
        String nombre,
        Categoria categoria
) {
}
