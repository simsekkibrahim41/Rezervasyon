package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Dto.RestoranRezervasyonDto;
import com.rezervasyon.rezervasyon_sistemi.Enums.RezervasyonTipi;
import com.rezervasyon.rezervasyon_sistemi.Models.Kullanici;
import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Models.Rezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Models.RestoranRezervasyon;
import com.rezervasyon.rezervasyon_sistemi.Repository.KullaniciRepository;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranRepository;
import com.rezervasyon.rezervasyon_sistemi.Repository.RezervasyonRepository;
import com.rezervasyon.rezervasyon_sistemi.Repository.RestoranRezervasyonRepository;
import com.rezervasyon.rezervasyon_sistemi.Service.RestoranRezervasyonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/restoranRezervasyonlar")
@CrossOrigin(origins = "http://localhost:5175")
public class RestoranRezervasyonController {

    @Autowired
    private RestoranRezervasyonRepository restoranRezervasyonRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private RezervasyonRepository rezervasyonRepository;
    @Autowired
    private RestoranRezervasyonService restoranRezervasyonService;


    @PostMapping
    public ResponseEntity<?> restoranRezervasyonEkle(@RequestBody RestoranRezervasyonDto rezervasyonDto) {

        // Önce restorandaki masa + saat + tarih çakışması var mı kontrol et
        boolean rezervasyonVarMi = restoranRezervasyonService.rezervasyonZatenVarMi(
                rezervasyonDto.getRestoranId(),
                rezervasyonDto.getMasaNo(),
                rezervasyonDto.getTarih(),
                rezervasyonDto.getSaat()
        );

        if (rezervasyonVarMi) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Bu restoranın bu masası, bu tarih ve saatte zaten rezerve edilmiştir.");
        }

        // Kullanıcı ve restoran bilgilerini al
        Kullanici kullanici = kullaniciRepository.findById(rezervasyonDto.getKullaniciId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Restoran restoran = restoranRepository.findById(rezervasyonDto.getRestoranId())
                .orElseThrow(() -> new RuntimeException("Restoran bulunamadı"));

        // RestoranRezervasyon tablosuna kayıt
        RestoranRezervasyon restoranRezervasyon = new RestoranRezervasyon();
        restoranRezervasyon.setKullanici(kullanici);
        restoranRezervasyon.setRestoran(restoran);
        restoranRezervasyon.setMasaNo(rezervasyonDto.getMasaNo());
        restoranRezervasyon.setSaat(rezervasyonDto.getSaat());
        restoranRezervasyon.setTarih(rezervasyonDto.getTarih());
        restoranRezervasyon.setAciklama(rezervasyonDto.getAciklama());

        restoranRezervasyonRepository.save(restoranRezervasyon);

        // Genel Rezervasyon tablosuna da kayıt
        Rezervasyon rezervasyon = new Rezervasyon();
        rezervasyon.setKullanici(kullanici);
        rezervasyon.setRestoran(restoran);
        rezervasyon.setTarih(LocalDateTime.now());
        rezervasyon.setRezervasyonTipi(RezervasyonTipi.RESTORAN);
        rezervasyon.setAciklama("Masa " + rezervasyonDto.getMasaNo() + " için saat " + rezervasyonDto.getSaat() + " rezervasyonu.");

        rezervasyonRepository.save(rezervasyon);

        return ResponseEntity.ok(restoranRezervasyon);
    }
}
