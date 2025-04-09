package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Models.Otel;
import com.rezervasyon.rezervasyon_sistemi.Service.OtelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oteller")
public class OtelController {

    @Autowired
    private OtelService otelService;

    // Yeni otel ekle
    @PostMapping
    public Otel otelKaydet(@RequestBody Otel otel) {
        return otelService.otelKaydet(otel);
    }

    // Tüm otelleri listele
    @GetMapping
    public List<Otel> tumOtelleriGetir() {
        return otelService.tumOtelleriGetir();
    }

    // ID’ye göre tek bir oteli listele
    @GetMapping("/{id}")
    public Otel otelGetir(@PathVariable Long id) {
        return otelService.otelGetir(id);
    }

    // ID’ye göre otel güncelle
    @PutMapping("/{id}")
    public Otel otelGuncelle(@PathVariable Long id, @RequestBody Otel otel) {
        otel.setId(id); // Gelen body’de ID olmayabilir. URL’den alınanı set ederiz.
        return otelService.otelGuncelle(otel);
    }

    // ID’ye göre otel sil
    @DeleteMapping("/{id}")
    public void otelSil(@PathVariable Long id) {
        otelService.otelSil(id);
    }
}
