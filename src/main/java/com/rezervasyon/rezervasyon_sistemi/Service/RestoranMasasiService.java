package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.RestoranMasasi;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranMasasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestoranMasasiService {

    @Autowired
    private RestoranMasasiRepository restoranMasasiRepository;

    // Tüm masaları getir
    public List<RestoranMasasi> getAllMasalar() {
        return restoranMasasiRepository.findAll();
    }

    // Belirli bir restorana ait tüm masaları getir
    public List<RestoranMasasi> getMasalarByRestoranId(Long restoranId) {
        return restoranMasasiRepository.findByRestoranId(restoranId);
    }

    // Yeni masa ekle
    public RestoranMasasi masaEkle(RestoranMasasi masa) {
        return restoranMasasiRepository.save(masa);
    }

    // Masa güncelle
    public RestoranMasasi masaGuncelle(RestoranMasasi masa) {
        return restoranMasasiRepository.save(masa);
    }

    // Masa sil
    public void masaSil(Long id) {
        restoranMasasiRepository.deleteById(id);
    }
}