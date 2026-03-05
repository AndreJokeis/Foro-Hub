package forohub.Foro.Hub.objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String mensaje;
    private LocalDate fechaCreacion;
    private String solucion;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topico topico;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
}
