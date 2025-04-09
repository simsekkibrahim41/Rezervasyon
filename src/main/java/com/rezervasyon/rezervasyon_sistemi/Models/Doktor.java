package com.rezervasyon.rezervasyon_sistemi.Models;

import com.rezervasyon.rezervasyon_sistemi.Enums.UzmanlikAlani;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "doktor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doktor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID
    private Long id;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "soyad", nullable = false)
    private String soyad;

    @Enumerated(EnumType.STRING) // Enum'u string olarak veritabanında saklar
    @Column(name = "uzmanlik_alani", nullable = false)
    private UzmanlikAlani uzmanlikAlani;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email", unique = true)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "olusturma_tarihi", nullable = false, updatable = false)
    private Date olusturmaTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "guncelleme_tarihi", nullable = false)
    private Date guncellemeTarihi;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dogum_tarihi", nullable = true)
    private Date dogumTarihi;

    @PrePersist // Doktor ilk kez oluşturulurken tarih atanır
    protected void onCreate() {
        this.olusturmaTarihi = new Date();
        this.guncellemeTarihi = new Date();
    }

    @PreUpdate // Doktor bilgisi güncellenince tarih yenilenir
    protected void onUpdate() {
        this.guncellemeTarihi = new Date();
    }
}
