package forohub.Foro.Hub.objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @Column(unique = true)
    private String correo;
    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfiles;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Respuesta> respuestas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Tomamos nuestra lista de Perfiles y la mapeamos al formato de Spring Security
        return this.perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getNombre().toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
