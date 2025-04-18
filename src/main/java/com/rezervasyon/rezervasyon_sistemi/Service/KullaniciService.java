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
        Kullanici kullanici = kullaniciRepository.findByEmail(email);

        if (kullanici != null && kullanici.getSifre().equals(sifre)) {
            return kullanici;
        }

        for (Kullanici bekleyen : bekleyenKullanicilar.values()) {
            if (bekleyen.getEmail().equals(email) && bekleyen.getSifre().equals(sifre)) {
                return bekleyen;
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
        // Token üret
        String token = UUID.randomUUID().toString();
        kullanici.setVerificationToken(token);
        kullanici.setEmailVerified(false);

        bekleyenKullanicilar.put(token, kullanici);

        System.out.println("Bekleyen kullanıcı eklendi: " + kullanici.getEmail());

        // Mail gönder
        String dogrulamaLinki = "http://localhost:8080/api/kullanicilar/dogrula?token=" + token;

        String icerik = "Merhaba " + kullanici.getAd() + " " + kullanici.getSoyad() + ",\n\n" +
                "Rezervasyon Sistemine Hoşgeldiniz.\n" +
                "Kayıt işleminizi tamamlamak için lütfen aşağıdaki bağlantıya tıklayın:\n\n" +
                dogrulamaLinki + "\n\n";

        emailService.sendMail(
                kullanici.getEmail(),
                "Hesabınızı Aktifleştirin - Mail Doğrulama ",
                icerik
        );
    }

    // Doğrulama linkine tıklanınca çağrılır
    public boolean dogrulamaTamamla(String token) {

        Kullanici kullanici = bekleyenKullanicilar.get(token);

        if (kullanici != null) {

            System.out.println(" Doğrulama ile veritabanına kaydedilen şifre: " + kullanici.getSifre());

            kullanici.setEmailVerified(true);
            kullaniciRepository.save(kullanici); //  İlk kez burada veritabanına kaydolur
            bekleyenKullanicilar.remove(token); // Artık map'ten sil
            return true;
        }

        return false; // Token bulunamazsa: geçersiz ya da süresi dolmuş
    }










}
