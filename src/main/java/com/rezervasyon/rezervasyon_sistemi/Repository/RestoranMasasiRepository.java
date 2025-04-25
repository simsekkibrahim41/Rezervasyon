package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.RestoranMasasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestoranMasasiRepository extends JpaRepository<RestoranMasasi, Long> {

    // Belirli bir restorana ait tüm masaları getirir
    List<RestoranMasasi> findByRestoranId(Long restoranId);
}