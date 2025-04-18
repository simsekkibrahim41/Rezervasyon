import React, { useState, useEffect } from "react";
import axios from "axios";
import '../AdminPanel.css';

function AdminPanel() {
    const kullanici = JSON.parse(localStorage.getItem("kullanici"));
    const [kullanicilar, setKullanicilar] = useState([]);
    const [secilenKullanici, setSecilenKullanici] = useState(null);
    const [rezervasyonlar, setRezervasyonlar] = useState([]);
    const [arama, setArama] = useState('');
    const [seciliRezervasyon, setSeciliRezervasyon] = useState(null);
    const [rezModalAcik, setRezModalAcik] = useState(false);



    useEffect(() => {
        if (kullanici?.rol === "ADMIN") {
            axios
                .get("http://localhost:8080/api/kullanicilar")
                .then((res) => setKullanicilar(res.data))
                .catch((err) => console.error("Kullanıcılar alınamadı", err));
        }
    }, []);

    const handleKullaniciSec = (kullanici) => {
        setSecilenKullanici(kullanici);
        axios
            .get("http://localhost:8080/api/rezervasyonlar")
            .then((res) => {
                const filtreli = res.data.filter(
                    (r) => r.kullanici.id === kullanici.id
                );
                setRezervasyonlar(filtreli);
            })
            .catch((err) => console.error("Rezervasyonlar alınamadı", err));
    };

    const handleLogout = () => {
        localStorage.removeItem("kullanici");
        window.location.href = "/login";
    };

    if (!kullanici || kullanici.rol !== "ADMIN") {
        return <h3 className="text-danger"> Erişim Reddedildi !!!</h3>;
    }

    return (
        <div className="admin-panel">
            <div className="admin-container">

                {/* Sol: Kullanıcı listesi */}
                <div className="admin-kullanici-listesi">
                    <h5>Kullanıcılar</h5>

                    <input
                        type="text"
                        placeholder="Ad veya soyad ara..."
                        value={arama}
                        onChange={(e) => setArama(e.target.value)}
                        style={{ width: "100%", padding: "5px", marginBottom: "10px", borderRadius: "5px" }}
                    />

                    <div className="kullanici-scroll">
                        {kullanicilar
                            .filter(k =>
                                (k.ad + ' ' + k.soyad).toLowerCase().includes(arama.toLowerCase())
                            )
                            .map((k, index) => (
                                <div
                                    key={index}
                                    onClick={() => handleKullaniciSec(k)}
                                    className={`kullanici-item ${secilenKullanici?.id === k.id ? "aktif" : ""}`}
                                >
                                    {k.ad} {k.soyad}
                                </div>
                            ))}
                    </div>
                </div>

                {/* Orta: Rezervasyon bilgileri */}
                <div className="admin-rezervasyonlar">
                    <h4>Rezervasyonlar</h4>

                    {secilenKullanici && (
                        <div className="rezervasyon-kolonlar">
                            {["DOKTOR", "RESTORAN", "OTEL"].map((tip) => {
                                const ilgiliRezervasyonlar = rezervasyonlar.filter((r) => r.rezervasyonTipi === tip);

                                return (
                                    <div key={tip} className="rez-kolon">
                                        <h6>{tip} Rezervasyonları ({ilgiliRezervasyonlar.length})</h6>

                                        {ilgiliRezervasyonlar.map((r, i) => (
                                            <div
                                                key={i}
                                                className="rez-item"
                                                onClick={() => {
                                                    setSeciliRezervasyon(r);
                                                    setRezModalAcik(true);
                                                }}
                                                style={{ cursor: "pointer" }}
                                            >
                                                Rezervasyon #{i + 1}
                                            </div>
                                        ))}
                                    </div>
                                );
                            })}
                        </div>
                    )}
                </div>
                {/* Kullanıcı silme işlemi */}
                {secilenKullanici && (
                    <button
                        className="admin-logout"
                        style={{ right: "150px", bottom: "20px", backgroundColor: "darkred" }}
                        onClick={async () => {
                            const onay = window.confirm(`${secilenKullanici.ad} ${secilenKullanici.soyad} adlı kullanıcı silinsin mi?`);
                            if (!onay) return;

                            try {
                                await axios.delete(`http://localhost:8080/api/kullanicilar/${secilenKullanici.id}`);
                                alert("Kullanıcı silindi.");

                                // Listeyi güncelle
                                setKullanicilar(prev => prev.filter(k => k.id !== secilenKullanici.id));
                                setSecilenKullanici(null);
                                setRezervasyonlar([]);
                            } catch (err) {
                                alert("Silme işlemi başarısız!");
                                console.error(err);
                            }
                        }}
                    >
                        Kullanıcıyı Sil
                    </button>
                )}

                {rezModalAcik && seciliRezervasyon && (
                    <div className="sil-modal-overlay">
                        <div className="sil-modal">
                            <h5>Rezervasyon Detayı</h5>
                            <p><strong>Açıklama:</strong> {seciliRezervasyon.aciklama}</p>
                            <p><strong>Tarih:</strong> {new Date(seciliRezervasyon.tarih).toLocaleString()}</p>
                            <p><strong>Tip:</strong> {seciliRezervasyon.rezervasyonTipi}</p>

                            <div className="button-group">
                                <button
                                    className="custom-btn iptal-btn"
                                    onClick={() => handleRezervasyonSil(seciliRezervasyon.id)}
                                >
                                    Rezervasyonu İptal Et
                                </button>

                                <button
                                    className="custom-btn"
                                    onClick={() => setRezModalAcik(false)}
                                >
                                    Kapat
                                </button>
                            </div>
                        </div>
                    </div>
                )}



                {/* Sağ alt çıkış butonu */}
                <button className="admin-logout" onClick={handleLogout}>
                    Çıkış Yap
                </button>
            </div>
        </div>
    );
}

export default AdminPanel;
