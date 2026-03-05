package forohub.Foro.Hub.dtos;

import java.util.List;

public record DatosInfoUsuario(
        long usuario_id,
        String nombre,
        String correo) {
}
