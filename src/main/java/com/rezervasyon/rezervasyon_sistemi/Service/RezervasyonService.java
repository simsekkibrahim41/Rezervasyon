package com.rezervasyon.rezervasyon_sistemi.Service;

import com.rezervasyon.rezervasyon_sistemi.Enums.RezervasyonTipi;
import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Models.RestoranRezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Models.Rezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranRezervasyonRepository;
import com.rezervasyon.rezervasyon_sistemi.Repository.RezervasyonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RezervasyonService {

    @Autowired
    private RestoranRezervasyonRepository restoranRezervasyonRepository;

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
    public Rezervasyon rezervasyonGuncelle(Rezervasyon rezervasyon) {return rezervasyonRepository.save(rezervasyon);}

    // Rezervasyonu siler
    public void rezervasyonSil(Long id) {
        Rezervasyon rezervasyon = rezervasyonRepository.findById(id).orElse(null);
        if (rezervasyon != null) {
            rezervasyon.setAktif(false); // pasif yap
            rezervasyonRepository.save(rezervasyon);
        }
    }
    public List<Rezervasyon> getKullaniciRezervasyonlariByTip(Long kullaniciId, RezervasyonTipi tip) {
        return rezervasyonRepository.findByKullaniciIdAndRezervasyonTipiAndAktifTrue(kullaniciId, tip);
    }



    @Transactional
    public void restoranRezervasyonSil(Restoran restoran, Kullanici kullanici) {
        List<RestoranRezervasyon> rezervasyonlar = restoranRezervasyonRepository
                .findByRestoranIdAndKullaniciId(restoran.getId(), kullanici.getId());

        if (rezervasyonlar != null && !rezervasyonlar.isEmpty()) {
            for (RestoranRezervasyon r : rezervasyonlar) {
                r.setAktif(false); // silme yerine pasif yap
            }
            restoranRezervasyonRepository.saveAll(rezervasyonlar);
        }
    }

    public List<Rezervasyon> getRezervasyonlarByDoktorId(Long doktorId) {return rezervasyonRepository.findByDoktorId(doktorId);}

    public List<Rezervasyon> getRezervasyonlarByKullaniciId(Long kullaniciId) {return rezervasyonRepository.findByKullaniciId(kullaniciId);}

}
