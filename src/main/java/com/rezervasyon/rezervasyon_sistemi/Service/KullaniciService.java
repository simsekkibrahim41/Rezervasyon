package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Repository.KullaniciRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public Kullanici girisYap(String email, String sifre) {
        List<Kullanici> kullanicilar = kullaniciRepository.findAll(); // repository üzerinden eriş

        for (Kullanici kullanici : kullanicilar) {
            if (kullanici.getEmail().equals(email) && kullanici.getSifre().equals(sifre)) {
                return kullanici;
            }
        }

        return null;
    }

    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    // Yeni kullanıcı kaydetme
    public Kullanici kullaniciKaydet(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }
    //Kullanıcıyı güncelleme
    public Kullanici kullaniciGuncelle(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }

    // Tüm kullanıcıları listeleme
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    // Belirli bir kullanıcıyı ID ile getirme
    public Kullanici kullaniciGetir(Long id) {
        return kullaniciRepository.findById(id).orElse(null);
    }

    // Kullanıcı silme
    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }


    //MAİL GÖNDERME VE DOĞRULAMA



    @Autowired
    private EmailService emailService;

    private final Map<String, Kullanici> bekleyenKullanicilar = new HashMap<>();

    // Mail doğrulama süreci başlatılır (veritabanına kaydetmez)
    public void mailIleKayitOl(Kullanici kullanici) {
        // 1. Token üret
        String token = UUID.randomUUID().toString();

        // 2. Kullanıcıya token ata
        kullanici.setVerificationToken(token);
        kullanici.setEmailVerified(false);

        // 3. Bekleyen kullanıcılar listesine ekle (doğrulanana kadar)
        bekleyenKullanicilar.put(token, kullanici);

        // 4. Mail gönder
        String dogrulamaLinki = "http://localhost:8080/api/kullanicilar/dogrula?token=" + token;
        emailService.sendMail(
                kullanici.getEmail(),
                "Mail Doğrulama - Rezervasyon Sistemi",
                "Merhaba " + kullanici.getAd() + ",\n\n" +
                        "Kayıt işleminizi tamamlamak için lütfen aşağıdaki bağlantıya tıklayın:\n" +
                        dogrulamaLinki + "\n\nTeşekkürler!"
        );
    }

    // Doğrulama linkine tıklanınca çağrılır
    public boolean dogrulamaTamamla(String token) {
        Kullanici kullanici = bekleyenKullanicilar.get(token);

        if (kullanici != null) {
            kullanici.setEmailVerified(true);
            kullaniciRepository.save(kullanici);
            bekleyenKullanicilar.remove(token);
            return true;
        }
        return false;
    }










}
