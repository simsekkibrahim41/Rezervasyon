package com.rezervasyon.rezervasyon_sistemi.Enums;

public enum Rol
{
    ADMIN("Admin"),
    KULLANICI("Kullanıcı");

    private final String Aciklama;   //enum değişkeni sonradan değiştirilmesin

    Rol(String Aciklama)
    {
        this.Aciklama = Aciklama;
    }

    public String getAciklama()     //dışarıdan açıklama okunabilsin ama değiştirilmesin
    {
        return Aciklama;
    }
}
