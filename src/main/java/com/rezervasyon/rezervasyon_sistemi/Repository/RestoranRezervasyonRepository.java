package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Models.RestoranRezervasyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RestoranRezervasyonRepository extends JpaRepository<RestoranRezervasyon, Long> {

    // Belirli bir restorana ait rezervasyonları getir
    List<RestoranRezervasyon> findByRestoranId(Long restoranId);

    // Belirli bir restoranda, belirli tarihte yapılan rezervasyonlar
    List<RestoranRezervasyon> findByRestoranIdAndTarih(Long restoranId, Date tarih);

    // Belirli bir restoranda, masa numarasına ve tarihe göre rezervasyonlar
    List<RestoranRezervasyon> findByRestoranIdAndMasaNoAndTarih(Long restoranId, int masaNo, Date tarih);

    // Belirli bir kullanıcıya ait tüm restoran rezervasyonları
    List<RestoranRezervasyon> findByKullaniciId(Long kullaniciId);

    // Eski method - nesne bazlı silme
    void deleteByRestoranAndKullanici(Restoran restoran, Kullanici kullanici);

    // Yeni method - id bazlı silme (BUNU ŞİMDİ EKLİYORSUN)
    void deleteByRestoranIdAndKullaniciId(Long restoranId, Long kullaniciId);

    List<RestoranRezervasyon> findByRestoranIdAndKullaniciId(Long restoranId, Long kullaniciId);


    boolean existsByRestoranIdAndMasaNoAndTarihAndSaat(Long restoranId, int masaNo, Date tarih, String saat);

    List<RestoranRezervasyon> findByKullaniciIdAndAktifTrue(Long kullaniciId);



}

