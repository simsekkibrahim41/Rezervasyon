package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Repository.KullaniciRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        System.out.println("Gelen e-posta: " + email);
        System.out.println("Gelen şifre: " + sifre);

        if (kullanici != null) {
            System.out.println("Veritabanındaki hashli şifre: " + kullanici.getSifre());
            boolean eslesme = passwordEncoder.matches(sifre, kullanici.getSifre());
            System.out.println("Şifre eşleşiyor mu? " + eslesme);

            if (eslesme) {
                return kullanici;
            }
        }

        System.out.println("Giriş başarısız!");
        return null;
    }



    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    // Yeni kullanıcı kaydetme(JSON veri testi)
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


    //MAİL GÖNDERME VE DOĞRULAMA, KAYDETME



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
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean dogrulamaTamamla(String token) {
        Kullanici kullanici = bekleyenKullanicilar.get(token);

        if (kullanici != null) {
            kullanici.setEmailVerified(true);

            // Şifreyi hashle
            String hashliSifre = passwordEncoder.encode(kullanici.getSifre());
            kullanici.setSifre(hashliSifre);

            kullaniciRepository.save(kullanici); // veritabanına hashli şifre ile kayıt olur
            bekleyenKullanicilar.remove(token);// Artık map'ten sil
            return true;
        }
        return false;       // Token bulunamazsa: geçersiz ya da süresi dolmuş
    }









}
