package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Bu sınıfın bir repository olduğunu belirtir (veritabanı işlemleri için kullanılır)
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

    // Şehre göre restoran listele
    List<Restoran> findBySehir(String sehir);

    // Mutfak türüne göre restoran listele
    List<Restoran> findByMutfakTuru(String mutfakTuru);
}
