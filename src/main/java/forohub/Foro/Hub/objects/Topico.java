package forohub.Foro.Hub.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Topico {
    private int id;
    private String título;
    private String mensaje;
    private LocalDate fechaDeCreación;
    private String status;
    private String autor;
    private String curso;
}
