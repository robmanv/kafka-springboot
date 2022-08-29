package br.com.kafka.helpers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class ApiMocks {
    public static void setupMockPostResponse(WireMockServer mockService) throws IOException {
        System.out.println("Iniciando teste");

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/posts"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        ApiMocks.class.getClassLoader().getResourceAsStream("payload/get-json-placeholder-client-response.json"),
                                        defaultCharset()))));
    }
}
