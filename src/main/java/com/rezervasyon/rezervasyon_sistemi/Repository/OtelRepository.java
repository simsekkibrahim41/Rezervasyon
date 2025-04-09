package com.rezervasyon.rezervasyon_sistemi.Repository;

import com.rezervasyon.rezervasyon_sistemi.Models.Otel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OtelRepository extends JpaRepository<Otel, Long> {
    // sorguları tanımlayacaksın unutma
}
