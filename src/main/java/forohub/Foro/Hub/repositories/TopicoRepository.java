package forohub.Foro.Hub.repositories;

import forohub.Foro.Hub.objects.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);

    boolean existsByTitulo(String titulo);

    boolean existsByMensaje(String mensaje);
}
