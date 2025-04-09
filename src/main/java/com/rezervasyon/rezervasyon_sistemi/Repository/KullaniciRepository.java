package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long>
{
    Kullanici findByEmail(String email);

    /*
    * // Belirli bir rolün tüm kullanıcılarını listele
    List<Kullanici> findByRol(Kullanici.Rol rol);

    // İsme göre kullanıcı ara
    List<Kullanici> findByAdContainingIgnoreCase(String ad);

    // Email veya adrese göre kullanıcı getir
    Optional<Kullanici> findByEmailOrAd(String email, String ad);
    *
    * */

}
