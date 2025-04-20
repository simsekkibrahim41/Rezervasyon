package com.rezervasyon.rezervasyon_sistemi.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;
import com.rezervasyon.rezervasyon_sistemi.Enums.RezervasyonTipi;

@Entity
@Table(name = "rezervasyonlar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rezervasyon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kullanıcı ilişkisi
    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Kullanici kullanici;

    // Rezervasyon tipi: DOKTOR, OTEL, RESTORAN
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RezervasyonTipi rezervasyonTipi;

    // Tarih (LocalDateTime tercih edilmiş, güzel seçim)
    @Column(nullable = false)
    private LocalDateTime tarih;

    @Column(nullable = false)
    private String aciklama;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date olusturmaTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date guncellemeTarihi;

    // Yeni eklenecek: doktor/otel/restoran ilişkileri 👇

    @ManyToOne
    @JoinColumn(name = "doktor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doktor doktor;

    @ManyToOne
    @JoinColumn(name = "otel_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Otel otel;

    @ManyToOne
    @JoinColumn(name = "restoran_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Restoran restoran;

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
