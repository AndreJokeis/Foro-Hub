package forohub.Foro.Hub.controllers;

import forohub.Foro.Hub.dtos.*;
import forohub.Foro.Hub.objects.*;
import forohub.Foro.Hub.repositories.*;
import forohub.Foro.Hub.services.TokenService;
import forohub.Foro.Hub.services.TopicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TopicoController.class)
@AutoConfigureJsonTesters
class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosRegistroTopico> datosRegistroTopicoJson;

    @Autowired
    private JacksonTester<DatosRespuestaTopico> datosRespuestaTopicoJson;

    @MockitoBean
    private TopicoRepository topicoRepository;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CursoRepository cursoRepository;

    @MockitoBean
    private TopicoService topicoService;

    @MockitoBean
    private TokenService tokenService;

    @Test
    @DisplayName("Debería devolver HTTP 400 cuando la request no tenga datos")
    @WithMockUser
    void registrarTopico_escenario1() throws Exception {
        var response = mvc.perform(post("/topicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver HTTP 201 Creado cuando la request reciba un JSON válido")
    @WithMockUser
    void registrarTopico_escenario2() throws Exception {
        // given
        var datosRegistro = new DatosRegistroTopico(
                "Duda con JPA", "No entiendo el mappedBy", 1L, 1L
        );

        var datosRespuestaEsperada = new DatosRespuestaTopico(
                1L, "Duda con JPA", "No entiendo el mappedBy", LocalDate.now(), Status.ACTIVO,
                new DatosInfoUsuario(1L, "Chuy", "chupacoagulos@cabron.com"),
                new DatosCurso(1L, "Java", Categoria.BACKEND)
        );

        when(topicoService.agregar(any())).thenReturn(datosRespuestaEsperada);

        // when
        var response = mvc.perform(post("/topicos")
                .with(csrf()) // Previene el error 403
                .contentType(MediaType.APPLICATION_JSON)
                .content(datosRegistroTopicoJson.write(datosRegistro).getJson())
        ).andReturn().getResponse();

        // then
        var jsonEsperado = datosRespuestaTopicoJson.write(datosRespuestaEsperada).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}