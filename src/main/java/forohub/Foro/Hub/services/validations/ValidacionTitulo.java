package forohub.Foro.Hub.services.validations;

import forohub.Foro.Hub.ValidacionException;
import forohub.Foro.Hub.dtos.DatosRegistroTopico;
import forohub.Foro.Hub.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionTitulo implements Validador{
    @Autowired
    TopicoRepository repository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        if(repository.existsByTitulo(datos.titulo()))
            throw new ValidacionException("No puede haber dos topicos con el mismo titulo.");
    }
}
