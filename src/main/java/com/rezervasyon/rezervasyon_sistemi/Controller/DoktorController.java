package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Enums.UzmanlikAlani;
import com.rezervasyon.rezervasyon_sistemi.Models.Doktor;
import com.rezervasyon.rezervasyon_sistemi.Service.DoktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doktorlar")
public class DoktorController {

    private final DoktorService doktorService;

    @Autowired
    public DoktorController(DoktorService doktorService) {
        this.doktorService = doktorService;
    }

    // Yeni doktor ekle
    @PostMapping
    public Doktor doktorEkle(@RequestBody Doktor doktor) {
        return doktorService.doktorEkle(doktor);
    }

    // Tüm doktorları listele
    @GetMapping
    public List<Doktor> doktorlariGetir() {
        return doktorService.tumDoktorlariGetir();
    }

    // ID’ye göre doktor getirme
    @GetMapping("/{id}")
    public Doktor doktorGetir(@PathVariable Long id) {
        return doktorService.doktorGetir(id);
    }

    // Doktor güncelle
    @PutMapping("/{id}")
    public Doktor doktorGuncelle(@PathVariable Long id, @RequestBody Doktor doktor) {
        doktor.setId(id);
        return doktorService.doktorGuncelle(doktor);
    }

    // Doktor sil
    @DeleteMapping("/{id}")
    public void doktorSil(@PathVariable Long id) {
        doktorService.doktorSil(id);
    }


    @GetMapping("/uzmanlik/{uzmanlik}")
    public List<Doktor> doktorlariUzmanligaGoreGetir(@PathVariable UzmanlikAlani uzmanlik) {
        return doktorService.getDoktorlarByUzmanlik(uzmanlik);
    }





}
