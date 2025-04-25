import React, { useEffect, useState } from "react";
import axios from "axios";
import "../RestoranRezervasyon.css";

function RestoranRezervasyon() {
  const [sehirler, setSehirler] = useState([]);
  const [mutfakTurleri, setMutfakTurleri] = useState([]);
  const [secilenSehir, setSecilenSehir] = useState("");
  const [secilenMutfak, setSecilenMutfak] = useState("");
  const [restoranlar, setRestoranlar] = useState([]);
  const [secilenRestoran, setSecilenRestoran] = useState(null);
  const [secilenMasa, setSecilenMasa] = useState(null);
  const [doluSaatler, setDoluSaatler] = useState([]);
  const [seciliSaat, setSeciliSaat] = useState(null);
  const [modalAcik, setModalAcik] = useState(false);
  const [rezervasyonlarim, setRezervasyonlarim] = useState([]);

  const kullanici = JSON.parse(localStorage.getItem("kullanici"));

  useEffect(() => {
    axios.get("http://localhost:8080/api/restoranlar").then((res) => {
      setSehirler([...new Set(res.data.map(r => r.sehir))]);
      setMutfakTurleri([...new Set(res.data.map(r => r.mutfakTuru))]);
    });
  }, []);

  useEffect(() => {
    if (secilenSehir && secilenMutfak) {
      axios.get("http://localhost:8080/api/restoranlar").then((res) => {
        const filtreli = res.data.filter(
          r => r.sehir === secilenSehir && r.mutfakTuru === secilenMutfak
        );
        setRestoranlar(filtreli);
      });
    }
  }, [secilenSehir, secilenMutfak]);

  const handleRestoranSec = (restoran) => {
    setSecilenRestoran(restoran);
    setSecilenMasa(null);
    setSeciliSaat(null);
  };

  const handleMasaSec = (masaNo) => {
    setSecilenMasa(masaNo);
    axios.get(`http://localhost:8080/api/restoran-rezervasyonlar/restoran/${secilenRestoran.id}`)
      .then(res => {
        const dolu = res.data
          .filter(r => r.masaNo === masaNo)
          .map(r => new Date(r.tarih).getHours().toString().padStart(2, '0') + ":00");
        setDoluSaatler(dolu);
      });
  };

  const handleRandevuOnayla = async () => {
    const bugun = new Date();
    const tarihStr = `${bugun.getFullYear()}-${(bugun.getMonth() + 1)
      .toString().padStart(2, "0")}-${bugun.getDate().toString().padStart(2, "0")} ${seciliSaat}`;

    const yeniRezervasyon = {
      tarih: tarihStr,
      masaNo: secilenMasa,
      restoran: { id: secilenRestoran.id },
      kullanici: { id: kullanici.id }
    };

    await axios.post("http://localhost:8080/api/restoran-rezervasyonlar", yeniRezervasyon);
    setModalAcik(false);
    window.location.reload();
  };

  return (
    <div className="restoran-container">
      <div className="restoran-sol-panel">
        <h4>Şehir Seçiniz</h4>
        <select value={secilenSehir} onChange={(e) => setSecilenSehir(e.target.value)}>
          <option value="">-- Seç --</option>
          {sehirler.map((s, i) => (
            <option key={i} value={s}>{s}</option>
          ))}
        </select>

        <h4>Mutfak Türü Seçiniz</h4>
        <select value={secilenMutfak} onChange={(e) => setSecilenMutfak(e.target.value)}>
          <option value="">-- Seç --</option>
          {mutfakTurleri.map((m, i) => (
            <option key={i} value={m}>{m}</option>
          ))}
        </select>
      </div>

      <div className="restoran-sag-panel">
        {!secilenRestoran ? (
          <div className="restoran-kartlar">
            {restoranlar.map((r) => (
              <div className="restoran-kart" key={r.id} onClick={() => handleRestoranSec(r)}>
                <h5>{r.ad}</h5>
                <p>{r.aciklama}</p>
                <p><strong>Şehir:</strong> {r.sehir}</p>
                <p><strong>Mutfak:</strong> {r.mutfakTuru}</p>
              </div>
            ))}
          </div>
        ) : (
          <div className="masa-saat-alani">
            <h4>{secilenRestoran.ad} - Masa Seçimi</h4>
            <div className="saat-grid">
              {[1, 2, 3, 4, 5].map((masa) => (
                <button key={masa} onClick={() => handleMasaSec(masa)}>
                  Masa {masa}
                </button>
              ))}
            </div>

            {secilenMasa && (
              <>
                <h4>Saat Seçimi (Masa {secilenMasa})</h4>
                <div className="saat-grid">
                  {["10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"].map((saat) => (
                    <button
                      key={saat}
                      className={doluSaatler.includes(saat) ? "dolu" : "bos"}
                      disabled={doluSaatler.includes(saat)}
                      onClick={() => {
                        setSeciliSaat(saat);
                        setModalAcik(true);
                      }}
                    >
                      {saat}
                    </button>
                  ))}
                </div>
              </>
            )}
          </div>
        )}
      </div>

      {modalAcik && (
        <div className="modal">
          <div className="modal-icerik">
            <h4>Rezervasyonu Onayla</h4>
            <p><strong>Restoran:</strong> {secilenRestoran.ad}</p>
            <p><strong>Masa:</strong> {secilenMasa}</p>
            <p><strong>Saat:</strong> {seciliSaat}</p>
            <button onClick={handleRandevuOnayla}>Onayla</button>
            <button onClick={() => setModalAcik(false)}>İptal</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default RestoranRezervasyon;
