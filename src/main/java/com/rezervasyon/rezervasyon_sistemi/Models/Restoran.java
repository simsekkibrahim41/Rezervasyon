package com.rezervasyon.rezervasyon_sistemi.Models;

import com.rezervasyon.rezervasyon_sistemi.Enums.MutfakTuru;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;;

// Restoran sınıfı bir JPA varlığıdır ve veri tabanında "restoran" adlı tabloya karşılık gelir.
@Entity
@Table(name = "restoran")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString   //toString() metodunu otomatik olarak oluşturmak için kullanılır
public class Restoran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "sehir", nullable = false)
    private String sehir;

    @Column(name = "adres")
    private String adres;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "aciklama")
    private String aciklama;

    @Enumerated(EnumType.STRING)
    @Column(name = "mutfak_turu", nullable = false)
    private MutfakTuru mutfakTuru;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "olusturma_tarihi", nullable = false, updatable = false)
    private  Date olusturmaTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "guncelleme_tarihi", nullable = false)
    private  Date guncellemeTarihi;

    @PrePersist
    protected void onCreate() {
        this.olusturmaTarihi = new Date();
        this.guncellemeTarihi = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.guncellemeTarihi = new Date();
    }
}