package com.rezervasyon.rezervasyon_sistemi.Enums;

public enum MutfakTuru
{
    TURK_MUTFAGI("Türk Mutfağı"),
    ITALYAN("İtalyan Mutfağı"),
    FAST_FOOD("Fast Food"),
    JAPON("Japon Mutfağı"),
    VEJETARYEN("Vejeteryen"),
    DENIZ_URUNLERI("Deniz Ürünleri");


    private final String Aciklama;//enum değişkeni sonradan değiştirilmesin


    MutfakTuru(String Aciklama)
    {
        this.Aciklama = Aciklama;
    }

    public String getAciklama()     //dışarıdan açıklama okunabilsin ama değiştirilmesin
    {
        return Aciklama;
    }
}
