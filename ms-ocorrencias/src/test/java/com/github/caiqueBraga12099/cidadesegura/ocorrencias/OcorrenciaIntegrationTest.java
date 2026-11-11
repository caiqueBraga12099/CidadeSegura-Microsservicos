package com.github.caiqueBraga12099.cidadesegura.ocorrencias;

import java.net.URI;
import java.net.http.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.service.OcorrenciaService;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.dto.request.*;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.entity.StatusOcorrencia;
import com.github.caiqueBraga12099.cidadesegura.ocorrencias.exception.RegraNegocioException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OcorrenciaIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OcorrenciaService service;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void migrationsCriamTresRegistrosEEndpointRetornaDto() throws Exception {
        assertThat(service.findAll()).hasSizeGreaterThanOrEqualTo(3);
        var response = enviar("GET", "/api/v1/ocorrencias/1", null);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("Bueiro obstruido").doesNotContain("versao");
    }

    @Test
    void crudHttpValidacaoELocation() throws Exception {
        var invalido = enviar("POST", "/api/v1/ocorrencias", "{}");
        assertThat(invalido.statusCode()).isEqualTo(400);
        String json = """
                {"titulo":"Teste urbano","descricao":"Descricao do problema",
                 "endereco":"Rua Teste, 10","bairro":"Centro"}
                """;
        var criado = enviar("POST", "/api/v1/ocorrencias", json);
        assertThat(criado.statusCode()).isEqualTo(201);
        String path = URI.create(criado.headers().firstValue("Location").orElseThrow()).getPath();
        assertThat(enviar("PUT", path, json.replace("Teste urbano", "Teste editado")).statusCode()).isEqualTo(200);
        assertThat(enviar("DELETE", path, null).statusCode()).isEqualTo(204);
        assertThat(enviar("GET", path, null).statusCode()).isEqualTo(404);
    }

    @Test
    void statusIdempotenteProtegeVinculoEImpedeRegressao() {
        var dto = service.create(new OcorrenciaRequestDTO("Teste status", "Descricao teste", "Rua Teste", "Centro"));
        String ref = "33333333-3333-4333-8333-333333333333";
        var inicio = new StatusRequestDTO(StatusOcorrencia.EM_ATENDIMENTO, ref);
        service.alterarStatus(dto.getId(), inicio);
        assertThat(service.alterarStatus(dto.getId(), inicio).getStatus()).isEqualTo(StatusOcorrencia.EM_ATENDIMENTO);
        assertThatThrownBy(() -> service.alterarStatus(dto.getId(),
                new StatusRequestDTO(StatusOcorrencia.RESOLVIDA, "44444444-4444-4444-8444-444444444444")))
                .isInstanceOf(RegraNegocioException.class);
        service.alterarStatus(dto.getId(), new StatusRequestDTO(StatusOcorrencia.RESOLVIDA, ref));
        assertThatThrownBy(() -> service.alterarStatus(dto.getId(), inicio)).isInstanceOf(RegraNegocioException.class);
        assertThatThrownBy(() -> service.delete(dto.getId())).isInstanceOf(RegraNegocioException.class);
    }

    @Test
    void swaggerDisponivel() throws Exception {
        var response = enviar("GET", "/v3/api-docs", null);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("/api/v1/ocorrencias");
    }

    private HttpResponse<String> enviar(String method, String path, String json) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .method(method, json == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
