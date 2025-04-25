package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Models.RestoranRezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Service.RestoranRezervasyonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/restoran-rezervasyonlar")
@CrossOrigin(origins = "http://localhost:5173") // Frontend için CORS ayarı
public class RestoranRezervasyonController {

    @Autowired
    private RestoranRezervasyonService rezervasyonService;

    // Tüm rezervasyonları getir (gerekirse)
    @GetMapping
    public List<RestoranRezervasyon> getTumRezervasyonlar() {
        return rezervasyonService.tumRezervasyonlariGetir();
    }

    // Kullanıcının rezervasyonlarını getir
    @GetMapping("/kullanici/{kullaniciId}")
    public List<RestoranRezervasyon> getKullaniciRezervasyonlari(@PathVariable Long kullaniciId) {
        return rezervasyonService.kullanicininRezervasyonlari(kullaniciId);
    }

    // Restorana ait rezervasyonları getir
    @GetMapping("/restoran/{restoranId}")
    public List<RestoranRezervasyon> getRestoranRezervasyonlari(@PathVariable Long restoranId) {
        return rezervasyonService.restoranRezervasyonlari(restoranId);
    }

    // Masa doluluk kontrolü (opsiyonel)
    @GetMapping("/kontrol")
    public List<RestoranRezervasyon> kontrolEt(
            @RequestParam Long restoranId,
            @RequestParam int masaNo,
            @RequestParam Date tarih
    ) {
        return rezervasyonService.masaRezervasyonlari(restoranId, masaNo, tarih);
    }

    // Yeni rezervasyon oluştur
    @PostMapping
    public RestoranRezervasyon rezervasyonOlustur(@RequestBody RestoranRezervasyon rezervasyon) {
        return rezervasyonService.rezervasyonKaydet(rezervasyon);
    }

    // Rezervasyon sil
    @DeleteMapping("/{id}")
    public void rezervasyonSil(@PathVariable Long id) {
        rezervasyonService.rezervasyonSil(id);
    }
}
