package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Otel;
import com.rezervasyon.rezervasyon_sistemi.Repository.OtelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OtelService {

    private final OtelRepository otelRepository;

    @Autowired
    public OtelService(OtelRepository otelRepository) {
        this.otelRepository = otelRepository;
    }

    //Otel Ekle
    public Otel otelKaydet(Otel otel) {
        return otelRepository.save(otel);
    }

    // Tüm otelleri listele
    public List<Otel> tumOtelleriGetir() {
        return otelRepository.findAll();
    }

    // ID’ye göre listele
    public Otel otelGetir(Long id) {
        return otelRepository.findById(id).orElse(null);
    }

    // Otel kaydı güncelleme
    public Otel otelGuncelle(Otel otel) {
        return otelRepository.save(otel);
    }

    // Otel sil
    public void otelSil(Long id) {
        otelRepository.deleteById(id);
    }
}
