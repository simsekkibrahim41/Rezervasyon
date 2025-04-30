package com.rezervasyon.rezervasyon_sistemi.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "restoran_rezervasyonlar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"kullanici", "restoran"}) // Lazy ilişkilerden dolayı döngüden kaçınmak için
public class RestoranRezervasyon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kullanıcı ile ilişki
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    // Restoran ile ilişki
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restoran_id")
    private Restoran restoran;

    // Hangi masa?
    @Column(name = "masa_no")
    private int masaNo;

    // Tarih bilgisi
    @Temporal(TemporalType.DATE)
    @Column(name = "tarih")
    private Date tarih;

    // Saat bilgisi
    @Column(name = "saat")
    private String saat;

    // Açıklama
    @Column(name = "aciklama")
    private String aciklama;

    @Column(name = "aktif")
    private boolean aktif = true;

}
