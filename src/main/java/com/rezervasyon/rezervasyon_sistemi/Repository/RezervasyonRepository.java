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



    /**
     *      // Belirli bir tarih aralığındaki rezervasyonları getir
     *      List<Rezervasyon> findByTarihBetween(LocalDateTime baslangic, LocalDateTime bitis);
     *

     *
     *      // Kullanıcının belirli bir tarih aralığındaki rezervasyonlarını getir
     *      List<Rezervasyon> findByKullaniciIdAndTarihBetween(Long kullaniciId, LocalDateTime baslangic, LocalDateTime bitis);
     *
     *
     // En son eklenen rezervasyonları listele (Örn: Son 10 kayıt)
     List<Rezervasyon> findTop10ByOrderByOlusturmaTarihiDesc();
     *
     */

}