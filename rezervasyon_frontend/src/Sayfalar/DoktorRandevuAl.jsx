
import React, { useEffect, useState } from "react";
import axios from "axios";
import "../DoktorRandevuAl.css";

function DoktorRandevuAl() {
  const [secilenUzmanlik, setSecilenUzmanlik] = useState("");
  const [doktorlar, setDoktorlar] = useState([]);
  const [secilenDoktor, setSecilenDoktor] = useState(null);
  const [doluSaatler, setDoluSaatler] = useState([]);
  const [seciliSaat, setSeciliSaat] = useState(null);
  const [modalAcik, setModalAcik] = useState(false);
  const [basariBilgisi, setBasariBilgisi] = useState(null);
  const [randevularim, setRandevularim] = useState([]);

  const uzmanliklar = [
    "KARDIYOLOJI", "ORTOPEDI", "DERMATOLOJI", "GASTROENTEROLOJI",
    "PSIKIYATRI", "GOZ_HASTALIKLARI", "NOROLOJI", "DIS_HEKIMI"
  ];

  const kullanici = JSON.parse(localStorage.getItem("kullanici"));

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/rezervasyonlar/kullanici/${kullanici.id}`)
      .then((res) => {
        console.log("Randevularım:", res.data);
        setRandevularim(res.data);
      })
      .catch((err) => console.error("Randevular alınamadı", err));
  }, []);

  useEffect(() => {
    if (secilenUzmanlik) {
      axios
        .get(`http://localhost:8080/api/doktorlar/uzmanlik/${secilenUzmanlik}`)
        .then((res) => setDoktorlar(res.data))
        .catch((err) => console.error("Doktorlar alınamadı", err));
    }
  }, [secilenUzmanlik]);

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
    const bugun = new Date();
    const tarihStr = `${bugun.getFullYear()}-${(bugun.getMonth() + 1)
      .toString().padStart(2, "0")}-${bugun.getDate().toString().padStart(2, "0")} ${seciliSaat}`;

    const yeniRandevu = {
      aciklama: `${secilenDoktor.ad} ${secilenDoktor.soyad} muayenesi`,
      tarih: tarihStr,
      rezervasyonTipi: "DOKTOR",
      kullanici: { id: kullanici.id },
      doktor: { id: secilenDoktor.id },
    };

    try {
      await axios.post("http://localhost:8080/api/rezervasyonlar", yeniRandevu);
      setDoluSaatler((prev) => [...prev, seciliSaat]);
      setModalAcik(false);
      setBasariBilgisi({ doktor: secilenDoktor, saat: seciliSaat, tarih: tarihStr });

      setTimeout(() => {
        window.location.reload(); // sayfayı tamamen sıfırlar
      }, 1000);
    } catch (err) {
      console.error("Randevu kaydı başarısız", err);
      alert("Randevu oluşturulamadı!");
    }
  };

  return (
    <div>
      <div className="doktor-randevu-container">
        <div className="sol-panel">
          <h4>Uzmanlık Seçiniz:</h4>
          <select
            value={secilenUzmanlik}
            onChange={(e) => setSecilenUzmanlik(e.target.value)}
          >
            <option value="">-- Seçiniz --</option>
            {uzmanliklar.map((u, i) => (
              <option key={i} value={u}>
                {u.replaceAll("_", " ")}
              </option>
            ))}
          </select>

          {secilenUzmanlik && doktorlar.length > 0 && (
            <ul className="doktor-listesi">
              {doktorlar.map((doktor) => (
                <li key={doktor.id} onClick={() => handleDoktorSec(doktor)}>
                  {doktor.ad} {doktor.soyad}
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="sag-panel">
          {secilenDoktor ? (
            <div className="saat-secimi">
              <h4>{secilenDoktor.ad} {secilenDoktor.soyad} - Saat Seçimi</h4>
              <div className="saat-grid">
                {["09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"].map((saat) => (
                  <button
                    key={saat}
                    disabled={doluSaatler.includes(saat)}
                    onClick={() => {
                      setSeciliSaat(saat);
                      setModalAcik(true);
                    }}
                    className={doluSaatler.includes(saat) ? "dolu" : "bos"}
                  >
                    {saat}
                  </button>
                ))}
              </div>
            </div>
          ) : (
            <div>
              <h3>Randevularım</h3>
              <ul>
                {randevularim.map((r, i) => (
                  <li key={i}>
                    <strong>
                      {new Date(r.tarih).toLocaleDateString()} -{" "}
                      {new Date(r.tarih).toLocaleTimeString([], {
                        hour: "2-digit",
                        minute: "2-digit",
                      })}
                    </strong>{" "}
                    | Doktor :  {r.doktor?.ad} {r.doktor?.soyad} -{" "} {r.doktor?.uzmanlikAlani}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>

        {modalAcik && (
          <div className="modal">
            <div className="modal-icerik">
              <h4>Randevuyu Onayla</h4>
              <p>Doktor: {secilenDoktor.ad} {secilenDoktor.soyad}</p>
              <p>Saat: {seciliSaat}</p>
              <div className="modal-butons">
                <button onClick={handleRandevuOnayla}>Onayla</button>
                <button onClick={() => setModalAcik(false)}>İptal</button>
              </div>
            </div>
          </div>
        )}
      </div>

      <div className="geri-buton-sol-alt">
        <button onClick={() => window.history.back()}>← Geri</button>
      </div>
    </div>
  );
}

export default DoktorRandevuAl;
