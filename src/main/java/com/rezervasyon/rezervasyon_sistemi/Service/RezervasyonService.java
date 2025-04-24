package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Rezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Repository.RezervasyonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezervasyonService {

    private final RezervasyonRepository rezervasyonRepository;

    @Autowired
    public RezervasyonService(RezervasyonRepository rezervasyonRepository) {
        this.rezervasyonRepository = rezervasyonRepository;
    }

    // Yeni rezervasyon ekleme
    public Rezervasyon rezervasyonKaydet(Rezervasyon rezervasyon) {
        return rezervasyonRepository.save(rezervasyon);
    }

    // Tüm rezervasyonları getirme
    public List<Rezervasyon> tumRezervasyonlariGetir() {
        return rezervasyonRepository.findAll();
    }

    // ID ile belirli bir rezervasyonu getirir
    public Rezervasyon rezervasyonGetir(Long id) {
        return rezervasyonRepository.findById(id).orElse(null);
    }

    // Rezervasyonu günceller
    public Rezervasyon rezervasyonGuncelle(Rezervasyon rezervasyon) {
        return rezervasyonRepository.save(rezervasyon);
    }

    // Rezervasyonu siler
    public void rezervasyonSil(Long id) {
        rezervasyonRepository.deleteById(id);
    }

    public List<Rezervasyon> getRezervasyonlarByDoktorId(Long doktorId) {
        return rezervasyonRepository.findByDoktorId(doktorId);
    }

    public List<Rezervasyon> getRezervasyonlarByKullaniciId(Long kullaniciId) {
        return rezervasyonRepository.findByKullaniciId(kullaniciId);
    }

}
