package forohub.Foro.Hub.objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "curso")
    List<Topico> topico;

    public Curso(long id, String nombre, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }
}
