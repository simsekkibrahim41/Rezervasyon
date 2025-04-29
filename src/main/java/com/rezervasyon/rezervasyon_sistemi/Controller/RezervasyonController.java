package com.rezervasyon.rezervasyon_sistemi.Controller;

// Gerekli importlar
import com.rezervasyon.rezervasyon_sistemi.Enums.RezervasyonTipi;
import com.rezervasyon.rezervasyon_sistemi.Models.Rezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Service.RezervasyonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController  //Sınıfı bir web controller olarak tanımlar. JSON döndürür.
@RequestMapping("/api/rezervasyonlar") // Bu controller altında kullanılacak genel path ,,
public class RezervasyonController {

    // Service katmanına ulaşmak için dependency injection
    @Autowired
    private RezervasyonService rezervasyonService;


    @PostMapping
    public ResponseEntity<Rezervasyon> rezervasyonOlustur(@RequestBody Rezervasyon rezervasyon) {
        Rezervasyon kaydedilen = rezervasyonService.rezervasyonKaydet(rezervasyon);
        return ResponseEntity.ok(kaydedilen);
    }

    // Tüm rezervasyonları listelemek için endpoint
    // HTTP GET isteği alır ve tüm rezervasyonları döner
    @GetMapping
    public List<Rezervasyon> rezervasyonlariGetir() {
        return rezervasyonService.tumRezervasyonlariGetir();
    }

    // ID ile belirli bir rezervasyonu getirir
    @GetMapping("/{id}")
    public Rezervasyon rezervasyonGetir(@PathVariable Long id) {
        return rezervasyonService.rezervasyonGetir(id);
    }

    // Var olan rezervasyonu günceller
    @PutMapping("/{id}")
    public Rezervasyon rezervasyonGuncelle(@PathVariable Long id, @RequestBody Rezervasyon rezervasyon) {
        rezervasyon.setId(id); // Güncellenecek kayıt bu id'ye sahip
        return rezervasyonService.rezervasyonGuncelle(rezervasyon);
    }

    // Rezervasyonu siler
    @DeleteMapping("/{id}")
    public ResponseEntity<String> rezervasyonSil(@PathVariable Long id) {
        Rezervasyon rezervasyon = rezervasyonService.rezervasyonGetir(id);
        if (rezervasyon == null) {
            return ResponseEntity.status(404).body("Rezervasyon bulunamadı.");
        }
        rezervasyonService.rezervasyonSil(id);
        if (rezervasyon.getRezervasyonTipi().toString().equals("RESTORAN")) {
            rezervasyonService.restoranRezervasyonSil(rezervasyon.getRestoran(), rezervasyon.getKullanici());
        }
        return ResponseEntity.ok("Rezervasyon başarıyla silindi.");
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public List<Rezervasyon> getKullaniciRezervasyonlari(
            @PathVariable Long kullaniciId,
            @RequestParam(required = false) RezervasyonTipi tip
    ) {
        if (tip != null) {
            return rezervasyonService.getKullaniciRezervasyonlariByTip(kullaniciId, tip);
        } else {
            return rezervasyonService.getRezervasyonlarByKullaniciId(kullaniciId);
        }
    }

    @GetMapping("/doktor/{doktorId}")
    public List<Rezervasyon> getDoktorRandevulari(@PathVariable Long doktorId) {
        return rezervasyonService.getRezervasyonlarByDoktorId(doktorId);
    }
}
