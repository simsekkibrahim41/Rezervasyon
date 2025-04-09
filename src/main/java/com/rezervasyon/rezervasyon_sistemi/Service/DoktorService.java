package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Doktor;
import com.rezervasyon.rezervasyon_sistemi.Repository.DoktorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;

    @Autowired
    public DoktorService(DoktorRepository doktorRepository) {
        this.doktorRepository = doktorRepository;
    }

    // Yeni doktor ekle
    public Doktor doktorEkle(Doktor doktor) {
        return doktorRepository.save(doktor);
    }

    // Tüm doktorları listele
    public List<Doktor> tumDoktorlariGetir() {
        return doktorRepository.findAll();
    }

    // ID’ye göre doktoru listele
    public Doktor doktorGetir(Long id) {
        return doktorRepository.findById(id).orElse(null);
    }

    // Doktor bilgilerini güncelle
    public Doktor doktorGuncelle(Doktor doktor) {
        return doktorRepository.save(doktor);
    }

    //Belirli bir doktor sil
    public void doktorSil(Long id) {
        doktorRepository.deleteById(id);
    }
}
