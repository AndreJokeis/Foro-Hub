package forohub.Foro.Hub.objects;

import forohub.Foro.Hub.dtos.DatosActualizarTopico;
import forohub.Foro.Hub.dtos.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String mensaje;
    private LocalDate fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean activo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "topico")
    List<Respuesta> respuestas;

    public Topico(DatosRegistroTopico datos, Usuario usuario, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = datos.fechaDeCreacion();
        this.status = datos.status();
        this.activo = true;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void actualizar(DatosActualizarTopico datos){
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.status = datos.status();
    }

    public void eliminar(){
        this.activo = false;
    }
}
