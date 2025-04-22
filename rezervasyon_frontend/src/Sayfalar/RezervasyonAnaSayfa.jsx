import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';

function RezervasyonAnaSayfa() {
    const navigate = useNavigate();
    const kullanici = JSON.parse(localStorage.getItem("kullanici"));


    const handleLogout = () => {
        localStorage.removeItem("kullanici");
        navigate("/login");
    };

    return (
        <div className="d-flex flex-column justify-content-center align-items-center vh-100">
            <h2 className="form-title text-center"
                style={{
                    position: "absolute",
                    top: "50px",
                    left: "50%",
                    transform: "translateX(-50%)",
                    margin: 0
                }}
            >Rezervasyon Ana Sayfa</h2>


            {kullanici && kullanici.rol === "ADMIN" && (
                <button
                    onClick={() => navigate("/admin")}
                    className="admin-btn"
                >
                    Admin Paneli
                </button>
            )}


            {kullanici && (
                <div className="w-100" style={{ marginLeft: "0.5rem" }}>
                    <h4 className="hosgeldiniz-label">
                        Hoş geldiniz, {kullanici.ad} {kullanici.soyad}!
                    </h4>
                </div>
            )}




            <div className="d-flex gap-4 flex-wrap justify-content-center">
                {/* Doktor */}
                <div className="rez-kart" onClick={() => navigate('/doktor-randevu')}>
                    <img src="/doktor.jpg" alt="Doktor" className="rez-img" />
                    <div className="rez-overlay">
                        Doktor Randevusu
                    </div>
                </div>

                {/* Restoran */}
                <div className="rez-kart" onClick={() => navigate('/restoran')}>
                    <img src="/restoran.jpg" alt="Restoran" className="rez-img" />
                    <div className="rez-overlay">
                        Restoran Rezervasyonu
                    </div>
                </div>

                {/* Otel */}
                <div className="rez-kart" onClick={() => navigate('/otel')}>
                    <img src="/otel.jpg" alt="Otel" className="rez-img" />
                    <div className="rez-overlay">
                        Otel Rezervasyonu
                    </div>
                </div>
            </div>

            <div className="logout-buton-alti">
                <button onClick={handleLogout} className="logout-btn">Çıkış Yap</button>
            </div>
        </div>
    );
}

export default RezervasyonAnaSayfa;
