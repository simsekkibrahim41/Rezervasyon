package com.rezervasyon.rezervasyon_sistemi.Controller;

// Gerekli importlar
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

    // Yeni rezervasyon oluşturmak için endpoint
    // HTTP POST isteği alır ve gelen rezervasyonu kaydeder
//    @PostMapping
//    public Rezervasyon rezervasyonOlustur(@RequestBody Rezervasyon rezervasyon) {
//        return rezervasyonService.rezervasyonKaydet(rezervasyon);
//    }

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

    //@PathVariable → URL'den gelen ID
    //@RequestBody → Gövde (body)'deki rezervasyon verisi


    // Rezervasyonu siler
    @DeleteMapping("/{id}")
    public void rezervasyonSil(@PathVariable Long id) {
        rezervasyonService.rezervasyonSil(id);
    }

    @GetMapping("/doktor/{doktorId}")
    public List<Rezervasyon> getDoktorRandevulari(@PathVariable Long doktorId) {
        return rezervasyonService.getRezervasyonlarByDoktorId(doktorId);
    }
}
