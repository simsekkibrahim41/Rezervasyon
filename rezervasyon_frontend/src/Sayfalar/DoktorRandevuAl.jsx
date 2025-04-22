import React, { useState, useEffect } from "react";
import axios from "axios";
import "../DoktorRandevuAl.css";

function DoktorRandevuAl() {
  const [uzmanlik, setUzmanlik] = useState("");
  const [doktorlar, setDoktorlar] = useState([]);
  const [secilenDoktor, setSecilenDoktor] = useState(null);
  const [doluSaatler, setDoluSaatler] = useState([]);
  const [seciliSaat, setSeciliSaat] = useState(null);
  const [randevularim, setRandevularim] = useState([]);

  const uzmanliklar = [
    "KARDIYOLOJI",
    "ORTOPEDI",
    "DERMATOLOJI",
    "GASTROENTEROLOJI",
    "PSIKIYATRI",
    "GOZ_HASTALIKLARI",
    "NOROLOJI",
    "DIS_HEKIMI",
  ];

  useEffect(() => {
    if (uzmanlik) {
      axios
        .get(`http://localhost:8080/api/doktorlar/uzmanlik/${uzmanlik}`)
        .then((res) => setDoktorlar(res.data))
        .catch((err) => console.error("Doktorlar alınamadı", err));
    } else {
      setDoktorlar([]);
      setSecilenDoktor(null);
    }
  }, [uzmanlik]);

  useEffect(() => {
    const kullanici = JSON.parse(localStorage.getItem("kullanici"));
    if (kullanici) {
      axios
        .get(`http://localhost:8080/api/rezervasyonlar`)
        .then((res) => {
          const kullaniciRandevulari = res.data.filter(
            (r) => r.kullanici?.id === kullanici.id && r.rezervasyonTipi === "DOKTOR"
          );
          setRandevularim(kullaniciRandevulari);
        })
        .catch((err) => console.error("Randevular getirilemedi", err));
    }
  }, []);

  const handleDoktorSec = (doktor) => {
    setSecilenDoktor(doktor);
    axios
      .get(`http://localhost:8080/api/rezervasyonlar/doktor/${doktor.id}`)
      .then((res) => {
        const saatler = res.data.map((r) => {
          const date = new Date(r.tarih);
          return date.getHours().toString().padStart(2, "0") + ":00";
        });
        setDoluSaatler(saatler);
      })
      .catch((err) => console.error("Dolu saatler alınamadı", err));
  };

  const handleRandevuOnayla = async () => {
    if (doluSaatler.includes(seciliSaat)) {
      alert("Bu saat dolu!");
      return;
    }

    const kullanici = JSON.parse(localStorage.getItem("kullanici"));
    const bugun = new Date();
    const tarihStr = `${bugun.getFullYear()}-${(bugun.getMonth() + 1)
      .toString()
      .padStart(2, "0")}-${bugun.getDate().toString().padStart(2, "0")} ${seciliSaat}`;

    const yeniRandevu = {
      aciklama: `${secilenDoktor.ad} ${secilenDoktor.soyad} muayenesi`,
      tarih: tarihStr,
      rezervasyonTipi: "DOKTOR",
      kullanici: { id: kullanici.id },
      doktor: { id: secilenDoktor.id },
    };

    try {
      await axios.post("http://localhost:8080/api/rezervasyonlar", yeniRandevu);
      window.location.reload(); // Sayfayı sıfırla
    } catch (err) {
      console.error("Randevu oluşturulamadı", err);
      alert("Randevu oluşturulamadı!");
    }
  };

  return (
    <div className="doktor-randevu-container">
      <div className="klinik-panel">
        <h3>KLİNİK SEÇİMİ</h3>
        <select
          value={uzmanlik}
          onChange={(e) => setUzmanlik(e.target.value)}
          className="dropdown"
        >
          <option value="">Uzmanlık Seçiniz</option>
          {uzmanliklar.map((u, i) => (
            <option key={i} value={u}>
              {u.replaceAll("_", " ")}
            </option>
          ))}
        </select>

        {doktorlar.map((doktor, i) => (
          <button
            key={i}
            className="doktor-btn"
            onClick={() => handleDoktorSec(doktor)}
          >
            {doktor.ad} {doktor.soyad}
          </button>
        ))}
      </div>

      <div className="saat-panel">
        {secilenDoktor ? (
          <>
            <h4>{secilenDoktor.ad.toUpperCase()} - Randevu Saatleri</h4>
            <div className="saatler">
              {["09:00", "10:00", "11:00", "13:00", "14:00"].map((saat, i) => (
                <button
                  key={i}
                  className="saat-btn"
                  disabled={doluSaatler.includes(saat)}
                  onClick={() => {
                    setSeciliSaat(saat);
                    handleRandevuOnayla();
                  }}
                  style={{
                    backgroundColor: doluSaatler.includes(saat)
                      ? "#ccc"
                      : "#4caf50",
                    cursor: doluSaatler.includes(saat)
                      ? "not-allowed"
                      : "pointer",
                  }}
                >
                  {saat}
                </button>
              ))}
            </div>
          </>
        ) : (
          <p style={{ opacity: 0.5 }}>Doktor seçiniz</p>
        )}
      </div>

      <div className="randevular-panel">
        <h4>Randevularım</h4>
        {randevularim.length > 0 ? (
          <ul>
            {randevularim.map((r, i) => (
              <li key={i}>
                {r.aciklama} -{" "}
                {new Date(r.tarih).toLocaleTimeString("tr-TR", {
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </li>
            ))}
          </ul>
        ) : (
          <p>Aktif Randevunuz Yok</p>
        )}
      </div>
    </div>
  );
}

export default DoktorRandevuAl;
