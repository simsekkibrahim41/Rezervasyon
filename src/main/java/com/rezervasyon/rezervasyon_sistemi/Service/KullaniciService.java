package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Repository.KullaniciRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

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
}
