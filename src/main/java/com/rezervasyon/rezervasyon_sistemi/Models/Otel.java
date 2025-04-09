package com.rezervasyon.rezervasyon_sistemi.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;;

// Otel sınıfı, veri tabanında "otel" tablosunu temsil eder.
@Entity
@Table(name = "otel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Otel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID
    private Long id;

    @Column(name = "ad", nullable = false) // Otelin adı
    private String ad;

    @Column(name = "sehir", nullable = false) // Otelin bulunduğu şehir
    private String sehir;

    @Column(name = "adres")
    private String adres;

    @Column(name = "telefon")
    private String telefon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "olusturma_tarihi", nullable = false, updatable = false)
    private Date olusturmaTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "guncelleme_tarihi", nullable = false)
    private Date guncellemeTarihi;

    // Otel kaydı oluşturulurken tarih otomatik atanır
    @PrePersist
    protected void onCreate() {
        this.olusturmaTarihi = new Date();
        this.guncellemeTarihi = new Date();
    }

    // Otel güncellenirse tarih otomatik güncellenir
    @PreUpdate
    protected void onUpdate() {
        this.guncellemeTarihi = new Date();
    }

}