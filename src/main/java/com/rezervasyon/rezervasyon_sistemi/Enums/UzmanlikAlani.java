package com.rezervasyon.rezervasyon_sistemi.Enums;

public enum UzmanlikAlani {
    KARDIYOLOJI("Kardiyoloji"),
    ORTOPEDI("Ortopedi"),
    DERMATOLOJI("Dermatoloji"),
    GASTROENTEROLOJI("Gastroenteroloji"),
    PSIKIYATRI("Psikiyatri"),
    GOZ_HASTALIKLARI("Goz Hastaliklari"),
    NOROLOJI("Noroloji"),
    DIS_HEKIMI("Dis Hekimi"),;

    private final String Aciklama;   //enum değişkeni sonradan değiştirilmesin

    UzmanlikAlani(String Aciklama)
    {
        this.Aciklama = Aciklama;
    }

    public String getAciklama()     //dışarıdan açıklama okunabilsin ama değiştirilmesin
    {
        return Aciklama;
    }

}