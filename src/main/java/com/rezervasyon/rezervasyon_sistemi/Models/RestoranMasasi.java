package com.rezervasyon.rezervasyon_sistemi.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restoran_masasi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestoranMasasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int masaNumarasi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restoran_id")
    private Restoran restoran;
}
