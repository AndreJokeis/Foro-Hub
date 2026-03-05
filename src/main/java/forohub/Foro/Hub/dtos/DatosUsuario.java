package forohub.Foro.Hub.dtos;

import forohub.Foro.Hub.objects.Rol;

import java.util.List;

public record DatosUsuario(
        long usuario_id,
        String nombre,
        String correo,
        String contrasena,
        List<String> perfiles
) {
}
