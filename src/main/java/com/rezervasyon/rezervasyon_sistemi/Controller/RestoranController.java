package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Enums.MutfakTuru;
import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restoranlar")
public class RestoranController {

    private final RestoranService restoranService;

    @Autowired
    public RestoranController(RestoranService restoranService) {
        this.restoranService = restoranService;
    }

    // Yeni restoran ekle
    @PostMapping
    public Restoran restoranEkle(@RequestBody Restoran restoran) {
        return restoranService.restoranEkle(restoran);
    }

    // Tüm restoranları listele
    @GetMapping
    public List<Restoran> restoranlariListele() {
        return restoranService.tumRestoranlariGetir();
    }

    // ID ile restoran listele
    @GetMapping("/{id}")
    public Restoran restoranGetir(@PathVariable Long id) { //@PathVariable ile url içindeki id değerinin alıyoruz
        return restoranService.restoranGetir(id);
    }

    // Restoran güncelle
    @PutMapping("/{id}")
    public Restoran restoranGuncelle(@PathVariable Long id, @RequestBody Restoran restoran) {
        restoran.setId(id); // Body'deki objeye URL'den gelen ID atanır
        return restoranService.restoranGuncelle(restoran);
    }

    // Restoran sil
    @DeleteMapping("/{id}")
    public void restoranSil(@PathVariable Long id) {
        restoranService.restoranSil(id);
    }

    @GetMapping("/mutfak/{mutfakTuru}")
    public List<Restoran> mutfagaGoreRestoranlar(@PathVariable String mutfakTuru) {
        return restoranService.getRestoranByMutfakTuru(mutfakTuru);
    }


    // Tüm şehirleri getir
    @GetMapping("/sehirler")
    public List<String> sehirleriGetir() {
        return restoranService.tumSehirleriGetir();
    }

    @GetMapping("/mutfakTurleri")
    public ResponseEntity<List<String>> getMutfakTurleri() {
        List<String> mutfakTurleri = Arrays.stream(MutfakTuru.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mutfakTurleri);
    }

    @GetMapping("/mutfakturleri")
    public List<String> tumMutfakTurleriniGetir() {
        return restoranService.tumMutfakTurleriniGetir();
    }

    @GetMapping("/filtreli")
    public List<Restoran> filtreliRestoranlar(
            @RequestParam("sehir") String sehir,
            @RequestParam("mutfakTuru") String mutfakTuru) {

        return restoranService.getRestoranBySehirAndMutfakTuru(sehir, mutfakTuru);
    }
}
