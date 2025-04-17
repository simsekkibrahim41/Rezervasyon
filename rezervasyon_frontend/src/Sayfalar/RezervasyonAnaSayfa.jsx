import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';

function RezervasyonAnaSayfa() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("kullanici");
        navigate("/login");
    };

    return (
        <div className="d-flex flex-column justify-content-center align-items-center vh-100">
            <h2 className="form-title mb-5">Rezervasyon Ana Sayfa</h2>

            

            <div className="d-flex gap-4 flex-wrap justify-content-center">
                {/* Doktor */}
                <div className="rez-kart" onClick={() => navigate('/doktor')}>
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
