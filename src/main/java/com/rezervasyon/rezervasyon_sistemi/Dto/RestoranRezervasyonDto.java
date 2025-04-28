package com.rezervasyon.rezervasyon_sistemi.Dto;

import lombok.Data;
import java.util.Date;

@Data
public class RestoranRezervasyonDto {
    private Long kullaniciId;
    private Long restoranId;
    private Integer masaNo;
    private String saat;
    private Date tarih;
    private String aciklama;

}
