package com.rezervasyon.rezervasyon_sistemi.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rezervasyon.rezervasyon_sistemi.Enums.Rol;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


// Kullanici sınıfı bir JPA varlığıdır ve veri tabanında "kullanicilar" adlı tabloya karşılık gelir.
@Entity
@Table(name = "kullanici")
@Getter
@Setter
@NoArgsConstructor // Parametresiz (boş) bir constructor ekler.
@AllArgsConstructor // Tüm alanları içeren bir constructor ekler.
@ToString(exclude = "sifre") // Şifre alanı hariç tutulacak şekilde toString metodunu oluşturur.
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin otomatik artmasını sağlar.
    private Long id;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "soyad", nullable = false)
    private String soyad;

    @Column(name = "sifre", nullable = false)
    private String sifre;

    @Column(name = "email", nullable = false, unique = true) // Email alanı boş bırakılamaz ve her kullanıcı için benzersiz olmalıdır.
    private String email;

    // Kullanıcının maili doğrulayıp doğrulamadığını belirtir
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    // Mail doğrulama işlemi için gönderilecek token
    @Column
    private String verificationToken;


    @Enumerated(EnumType.STRING) // Enum türünün veri tabanında string olarak saklanmasını sağlar.
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "olusturma_tarihi", nullable = false, updatable = false)
    private Date olusturmaTarihi;

    // Kullanıcının son güncellenme tarihini tutar.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "guncelleme_tarihi", nullable = false)
    private Date guncellemeTarihi;

    // Kullanıcı silindiğinde ilişkili rezervasyonları da sil
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rezervasyon> rezervasyonlar;




    // Kullanıcı oluşturulduğunda çalışacak metot
    @PrePersist  //veritabanında bir işlem yapılmadan hemen önce çalışır. Manuel değil otomatik tarih ataması yapar
    protected void onCreate() {
        this.olusturmaTarihi = new Date();// Kullanıcı oluşturulurken tarih atanır.
        this.guncellemeTarihi = new Date(); // İlk oluşturulma anında güncelleme tarihi de aynı olur.
    }

    // Kullanıcı güncellendiğinde çalışacak metot
    @PreUpdate // kayıt güncellenmeden hemen önce çalışır.  Manuel değil otomatik tarih ataması yapar
    protected void onUpdate() {
        this.guncellemeTarihi = new Date(); // Kullanıcı her güncellendiğinde tarih güncellenir.
    }




}

