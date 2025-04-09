package com.rezervasyon.rezervasyon_sistemi.Controller;

import com.rezervasyon.rezervasyon_sistemi.Models.Restoran;
import com.rezervasyon.rezervasyon_sistemi.Service.RestoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restoranlar")
public class RestoranController {

    private final RestoranService restoranService;

    @Autowired
    public RestoranController(RestoranService restoranService) {
        this.restoranService = restoranService;
    }

    // Yeni restoran ekle
    @PostMapping
    public Restoran restoranEkle(@RequestBody Restoran restoran) {
        return restoranService.restoranEkle(restoran);
    }

    // Tüm restoranları listele
    @GetMapping
    public List<Restoran> restoranlariListele() {
        return restoranService.tumRestoranlariGetir();
    }

    // ID ile restoran listele
    @GetMapping("/{id}")
    public Restoran restoranGetir(@PathVariable Long id) { //@PathVariable ile url içindeki id değerinin alıyoruz
        return restoranService.restoranGetir(id);
    }

    // Restoran güncelle
    @PutMapping("/{id}")
    public Restoran restoranGuncelle(@PathVariable Long id, @RequestBody Restoran restoran) {
        restoran.setId(id); // Body'deki objeye URL'den gelen ID atanır
        return restoranService.restoranGuncelle(restoran);
    }

    // Restoran sil
    @DeleteMapping("/{id}")
    public void restoranSil(@PathVariable Long id) {
        restoranService.restoranSil(id);
    }
}
