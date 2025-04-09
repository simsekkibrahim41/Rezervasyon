package com.rezervasyon.rezervasyon_sistemi.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false) // Kullanıcı rezervasyon ilişkisi
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Kullanici kullanici;

    @Enumerated(EnumType.STRING) // Enum'u veri tabanında string olarak saklar
    @Column(nullable = false)
    private RezervasyonTipi rezervasyonTipi; // "DOKTOR", "RESTORAN", "OTEL"

    @Column(nullable = false)
    private LocalDateTime tarih;

    @Column(nullable = false)
    private String aciklama;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private  Date  olusturmaTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private  Date  guncellemeTarihi;

    // Yeni rezervasyon oluşturulurken çalışacak
    @PrePersist
    protected void onCreate() {
        this.olusturmaTarihi = new Date();
        this.guncellemeTarihi = new Date();
    }

    // Rezervasyon güncellendiğinde çalışacak
    @PreUpdate
    protected void onUpdate() {
        this.guncellemeTarihi = new Date();
    }

}
