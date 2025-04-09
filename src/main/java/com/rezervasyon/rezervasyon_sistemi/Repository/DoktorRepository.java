package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Enums.UzmanlikAlani;
import com.rezervasyon.rezervasyon_sistemi.Models.Doktor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoktorRepository extends JpaRepository<Doktor, Long> {

    // Gerekirse özel sorgular burada yazılabilir. Örneğin:
     List<Doktor> findByUzmanlikAlani(UzmanlikAlani uzmanlikAlani);
}
