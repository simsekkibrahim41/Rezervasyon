package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestoranService {

    private final RestoranRepository restoranRepository;

    @Autowired
    public RestoranService(RestoranRepository restoranRepository) {
        this.restoranRepository = restoranRepository;
    }

    // Yeni restoran ekle
    public Restoran restoranEkle(Restoran restoran) {
        return restoranRepository.save(restoran);
    }

    // Tüm restoranları listele
    public List<Restoran> tumRestoranlariGetir() {
        return restoranRepository.findAll();
    }

    // ID ile restoran listele
    public Restoran restoranGetir(Long id) {
        return restoranRepository.findById(id).orElse(null);
    }

    // Restoranı güncelle
    public Restoran restoranGuncelle(Restoran restoran) {
        return restoranRepository.save(restoran);
    }

    // Restoranı sil
    public void restoranSil(Long id) {
        restoranRepository.deleteById(id);
    }

    public List<Restoran> getRestoranByMutfakTuru(String mutfakTuru) {
        return restoranRepository.findByMutfakTuru(mutfakTuru);
    }
}
