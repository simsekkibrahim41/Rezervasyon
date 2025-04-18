package com.rezervasyon.rezervasyon_sistemi.Controller;

// Gerekli importlar
import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Service.KullaniciService;
import jakarta.servlet.http.HttpServletResponse;
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
            if (!mevcutKullanici.isEmailVerified()) {
                return ResponseEntity.status(403).body("Mail adresiniz doğrulanmamış. Lütfen e-posta kutunuzu kontrol edin.");
            }
            return ResponseEntity.ok(mevcutKullanici);
        }

        return ResponseEntity.status(401).body("Geçersiz e-posta veya şifre");
    }


    // Kullanıcı kayıt olur (henüz veritabanına eklenmez)
    @PostMapping("/kayit")
    public ResponseEntity<String> mailIleKayit(@RequestBody Kullanici kullanici) {
        try {
            kullaniciService.mailIleKayitOl(kullanici);
            return ResponseEntity.ok("Doğrulama bağlantısı mailinize gönderildi: " + kullanici.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Mail gönderme sırasında hata oluştu");
        }
    }

    // Kullanıcı maildeki bağlantıya tıklayınca bu çalışır
    @GetMapping("/dogrula")
    public ResponseEntity<String> dogrula(@RequestParam("token") String token, HttpServletResponse response) {
        boolean basarili = kullaniciService.dogrulamaTamamla(token);

        if (basarili) {
            // Doğrulama başarılıysa frontend'e yönlendirme
            try {
                response.sendRedirect("http://localhost:5175/login"); // React login sayfası
            } catch (Exception e) {
                return ResponseEntity.ok("Kayıt başarılı! Giriş yapabilirsiniz.");
            }
            return ResponseEntity.ok("Kayıt başarılı! Giriş yapabilirsiniz.");
        } else {
            return ResponseEntity.badRequest().body("Geçersiz veya süresi dolmuş bağlantı.");
        }
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
    public ResponseEntity<String> kullaniciSil(@PathVariable Long id) {
        try {
            kullaniciService.kullaniciSil(id);
            return ResponseEntity.ok("Kullanıcı başarıyla silindi.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Kullanıcı silinemedi: " + e.getMessage());
        }
    }


//güncelleme cuma akşamı
}
