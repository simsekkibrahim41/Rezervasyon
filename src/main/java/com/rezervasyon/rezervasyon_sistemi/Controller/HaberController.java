package com.rezervasyon.rezervasyon_sistemi.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController  // json veri tipi döndürmek için
@RequestMapping("/api")
@CrossOrigin(origins = "*")         // CORS hatasından dolayı izin amaçlı koyuldu
public class HaberController {

    private static final Logger log = LoggerFactory.getLogger(HaberController.class);
    //Hata loglarını kontrol amaçlı yazıldı. log oluşturur

    @Value("${haberler.api.url}")           //application.properties’te tanımlanan haberler.api.url’i bu değişkene atar.
    private String apiUrl;


    @Value("${haberler.api.authToken}")     //API için gerekli authorization token’ı da properties’ten alır.
    private String authToken;

    private final RestTemplate restTemplate = new RestTemplate();       //HTTP çağrısı yapmak için

    @PostMapping(value = "/haberler", produces = MediaType.APPLICATION_JSON_VALUE) //POST isteğinden gelen verinin json olmasını sağlar
    public ResponseEntity<String> getHaberler() {

        // 1) Header’ları hazırlar.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, authToken);
        headers.set(HttpHeaders.USER_AGENT, "PostmanRuntime/7.28.4");

        // 2) Body
        String bodyJson = "{}";
        HttpEntity<String> request = new HttpEntity<>(bodyJson, headers);

        try {
            // 3) Dış servise POST isteği at
            ResponseEntity<String> response =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

            // 4) Dönen cevabı client’a ilet
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());

        } catch (HttpClientErrorException e) {
            // Hata logla
            log.error("Haber servisi çağrılırken {} kodlu hata: {}", e.getRawStatusCode(), e.getResponseBodyAsString());

            // 5) Dönüş statüsünü belirle
            int raw = e.getRawStatusCode();
            HttpStatus status = (raw == HttpStatus.UNAUTHORIZED.value())
                    ? HttpStatus.BAD_GATEWAY
                    : HttpStatus.valueOf(raw);

            String err = String.format("{\"error\":\"Dış API hatası: %s\"}", e.getStatusText());
            return ResponseEntity.status(status).body(err);
        }
    }
}
