package com.rezervasyon.rezervasyon_sistemi.Controller;

// Gerekli importlar
import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kullanicilar") // Ana URL: /api/kullanicilar
public class KullaniciController {

    // Service katmanı ile iletişim kurmak için otomatik enjekte edilir
    @Autowired
    private KullaniciService kullaniciService;

    @PostMapping("/giris")
    public ResponseEntity<?> girisYap(@RequestBody Kullanici gelenKullanici) {
        Kullanici mevcutKullanici = kullaniciService.girisYap(gelenKullanici.getEmail(), gelenKullanici.getSifre());

        if (mevcutKullanici != null) {
            return ResponseEntity.ok(mevcutKullanici); // Giriş başarılıysa kullanıcı bilgilerini döner
        } else {
            return ResponseEntity.status(401).body("Geçersiz e-posta veya şifre");
        }
    }

    // Yeni kullanıcı oluşturma endpoint'i
    @PostMapping
    public Kullanici kullaniciKaydet(@RequestBody Kullanici kullanici) {
        return kullaniciService.kullaniciKaydet(kullanici);
    }

    // Tüm kullanıcıları getirme endpoint'i
    @GetMapping
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciService.tumKullanicilariGetir();
    }

    // Belirli bir kullanıcıyı ID ile getirme endpoint'i
    @GetMapping("/{id}")
    public Kullanici kullaniciGetir(@PathVariable Long id) {
        return kullaniciService.kullaniciGetir(id);
    }

    // Kullanıcı güncelleme endpoint'i
    @PutMapping
    public Kullanici kullaniciGuncelle(@RequestBody Kullanici kullanici) {
        return kullaniciService.kullaniciGuncelle(kullanici);
    }

    // Kullanıcı silme endpoint'i
    @DeleteMapping("/{id}")
    public void kullaniciSil(@PathVariable Long id) {
        kullaniciService.kullaniciSil(id);
    }



}
