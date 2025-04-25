package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Models.RestoranMasasi;
import com.rezervasyon.rezervasyon_sistemi.Service.RestoranMasasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restoran-masalari")
@CrossOrigin(origins = "http://localhost:5173")
public class RestoranMasasiController {

    @Autowired
    private RestoranMasasiService restoranMasasiService;

    // Tüm masaları getir
    @GetMapping
    public List<RestoranMasasi> tumMasalariGetir() {
        return restoranMasasiService.getAllMasalar();
    }

    // Belirli restorana ait masaları getir
    @GetMapping("/restoran/{restoranId}")
    public List<RestoranMasasi> restoranMasalari(@PathVariable Long restoranId) {
        return restoranMasasiService.getMasalarByRestoranId(restoranId);
    }

    // Yeni masa ekle
    @PostMapping
    public RestoranMasasi masaEkle(@RequestBody RestoranMasasi masa) {
        return restoranMasasiService.masaEkle(masa);
    }

    // Masa güncelle
    @PutMapping
    public RestoranMasasi masaGuncelle(@RequestBody RestoranMasasi masa) {
        return restoranMasasiService.masaGuncelle(masa);
    }

    // Masa sil
    @DeleteMapping("/{id}")
    public void masaSil(@PathVariable Long id) {
        restoranMasasiService.masaSil(id);
    }
}