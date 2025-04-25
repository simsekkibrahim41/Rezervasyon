package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.RestoranRezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranRezervasyonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RestoranRezervasyonService {

    @Autowired
    private RestoranRezervasyonRepository rezervasyonRepository;

    // Tüm restoran rezervasyonlarını getir
    public List<RestoranRezervasyon> tumRezervasyonlariGetir() {
        return rezervasyonRepository.findAll();
    }

    // Belirli bir kullanıcının rezervasyonları
    public List<RestoranRezervasyon> kullanicininRezervasyonlari(Long kullaniciId) {
        return rezervasyonRepository.findByKullaniciId(kullaniciId);
    }

    // Belirli restoranın rezervasyonları
    public List<RestoranRezervasyon> restoranRezervasyonlari(Long restoranId) {
        return rezervasyonRepository.findByRestoranId(restoranId);
    }

    // Belirli restoranda, masa numarası ve tarih ile eşleşen rezervasyonları getir
    public List<RestoranRezervasyon> masaRezervasyonlari(Long restoranId, int masaNo, Date tarih) {
        return rezervasyonRepository.findByRestoranIdAndMasaNoAndTarih(restoranId, masaNo, tarih);
    }

    // Yeni rezervasyon oluştur
    public RestoranRezervasyon rezervasyonKaydet(RestoranRezervasyon rezervasyon) {
        return rezervasyonRepository.save(rezervasyon);
    }

    // İstenirse rezervasyon silme özelliği
    public void rezervasyonSil(Long id) {
        rezervasyonRepository.deleteById(id);
    }
}