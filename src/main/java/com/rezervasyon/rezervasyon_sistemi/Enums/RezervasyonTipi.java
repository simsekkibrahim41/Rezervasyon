package com.rezervasyon.rezervasyon_sistemi.Enums;

public enum RezervasyonTipi {
    DOKTOR("Doktor"),
    RESTORAN("Restoran"),
    OTEL("Otel");

    private final String Aciklama;   //enum değişkeni sonradan değiştirilmesin

    RezervasyonTipi(String Aciklama)
    {
        this.Aciklama = Aciklama;
    }

    public String getAciklama()     //dışarıdan açıklama okunabilsin ama değiştirilmesin
    {
        return Aciklama;
    }



}
