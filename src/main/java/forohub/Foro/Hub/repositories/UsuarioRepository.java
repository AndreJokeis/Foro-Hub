package forohub.Foro.Hub.repositories;

import forohub.Foro.Hub.objects.Rol;
import forohub.Foro.Hub.objects.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findActivoById(long id);

    UserDetails findByCorreo(String correo);

    @Query("SELECT p.nombre FROM Usuario u JOIN u.perfiles p WHERE u.id = :id")
    List<Rol> findPerfiles(long id);
}
