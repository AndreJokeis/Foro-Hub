package forohub.Foro.Hub.objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Perfil")
@Table(name = "perfiles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Rol nombre;

    @ManyToMany(mappedBy = "perfiles")
    List<Usuario> usuarios;
}
