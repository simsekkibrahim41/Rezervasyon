# 🗓️ Rezervasyon Sistemi

Bu proje, Spring Boot ile geliştirilmiş bir **rezervasyon otomasyon sistemidir**. Kullanıcılar; otel, restoran ve doktor randevularını yönetebilir.

##  Kullanılan Teknolojiler

- Java 17+
- Spring Boot (JPA, Web, Security)
- Microsoft SQL Server
- Lombok
- Git & GitHub
- Postman (test için)
- React 

##  Katmanlar

- `Controller` — API endpoint'leri
- `Service` — İş mantığı
- `Repository` — Veritabanı işlemleri
- `Models` — Entity (veri modelleri)
- `Enums` — Enum türleri
- `Frontend` — React ile kullanıcı arayüzü

##  Özellikler

 Kullanıcı yönetimi (CRUD)  
 Rezervasyon işlemleri (Otel, Restoran, Doktor)  
 Enum ile sınırlı değerler  
 Spring Security ile temel güvenlik altyapısı  
 Veritabanı işlemleri otomatik (`@PrePersist`, `@PreUpdate`)  
 Tamamen `DTO`suz sade mimari

##  Kurulum

1. Projeyi klonlayın:
   ```bash
   git clone https://github.com/simsekkibrahim41/Rezervasyon.git

2. IntelliJ IDEA ile açın.
3. SQL Server'da RezervasyonDatabase adında bir veritabanı oluşturun.

4. src/main/resources/application.properties dosyasını düzenleyin:
    spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=RezervasyonDatabase
    spring.datasource.username=KULLANICI_ADINIZ
    spring.datasource.password=ŞİFRENİZ
5. Projeyi çalıştırın.









   
