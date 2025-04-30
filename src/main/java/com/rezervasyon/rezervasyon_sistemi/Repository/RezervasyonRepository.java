package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.Rezervasyon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rezervasyon.rezervasyon_sistemi.Enums.RezervasyonTipi;
import java.util.List;

@Repository
public interface RezervasyonRepository extends JpaRepository<Rezervasyon, Long>
{
    //İLERİDE EKLENEBİLECEK LİSTELEMELER

    // Belirli bir kullanıcının tüm rezervasyonlarını getir
    List<Rezervasyon> findByKullaniciId(Long kullaniciId);

    // Belirli bir tipteki rezervasyonları getir (örneğin sadece "OTEL" rezervasyonlarını)
    List<Rezervasyon> findByRezervasyonTipi(RezervasyonTipi rezervasyonTipi);

    List<Rezervasyon> findByDoktorId(Long doktorId);


    List<Rezervasyon> findByKullaniciIdAndRezervasyonTipi(Long kullaniciId, RezervasyonTipi rezervasyonTipi);
    List<Rezervasyon> findByKullaniciIdAndRezervasyonTipiAndAktifTrue(Long kullaniciId, RezervasyonTipi rezervasyonTipi);



}