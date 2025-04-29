// Dosya başında eklenen importlar:
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
  const [secilenTarih, setSecilenTarih] = useState("");

  const uzmanliklar = [
    "KARDIYOLOJI", "ORTOPEDI", "DERMATOLOJI", "GASTROENTEROLOJI",
    "PSIKIYATRI", "GOZ_HASTALIKLARI", "NOROLOJI", "DIS_HEKIMI"
  ];

  const kullanici = JSON.parse(localStorage.getItem("kullanici"));

  useEffect(() => {
    if (kullanici && kullanici.id) {
      axios
        .get(`http://localhost:8080/api/rezervasyonlar/kullanici/${kullanici.id}?tip=DOKTOR`)
        .then((res) => {
          setRandevularim(res.data);
        })
        .catch((err) => console.error("Randevular alınamadı", err));
    }
  }, [kullanici]);

  useEffect(() => {
    if (secilenUzmanlik) {
      axios
        .get(`http://localhost:8080/api/doktorlar/uzmanlik/${secilenUzmanlik}`)
        .then((res) => setDoktorlar(res.data))
        .catch((err) => console.error("Doktorlar alınamadı", err));
    }
  }, [secilenUzmanlik]);

  useEffect(() => {
    if (secilenDoktor && secilenTarih) {
      axios
        .get(`http://localhost:8080/api/rezervasyonlar/doktor/${secilenDoktor.id}`)
        .then((res) => {
          const dolu = res.data
            .filter((r) => r.tarih.startsWith(secilenTarih))
            .map((r) => new Date(r.tarih).toTimeString().substring(0, 5));
          setDoluSaatler(dolu);
        })
        .catch((err) => console.error("Dolu saatler alınamadı", err));
    }
  }, [secilenTarih, secilenDoktor]);

  const bugununTarihiStringFormatta = () => {
    const today = new Date();
    return today.toISOString().split('T')[0];
  };

  const buYilinSonGunuStringFormatta = () => {
    const now = new Date();
    return `${now.getFullYear()}-12-31`;
  };

  const handleDoktorSec = (doktor) => {
    setSecilenDoktor(doktor);
    setDoluSaatler([]); // Önce temizle
  
    axios
      .get(`http://localhost:8080/api/rezervasyonlar/doktor/${doktor.id}`)
      .then((res) => {
       
        if (secilenTarih) {
          const dolu = res.data
            .filter((r) => r.tarih.startsWith(secilenTarih)) 
            .map((r) => new Date(r.tarih).toTimeString().substring(0, 5)); 
          setDoluSaatler(dolu);
        }
      })
      .catch((err) => console.error("Dolu saatler alınamadı", err));
  };
  const handleRandevuOnayla = async () => {
    if (!secilenTarih || !seciliSaat) {
      alert("Lütfen tarih ve saat seçiniz!");
      return;
    }

    const tarihStr = `${secilenTarih} ${seciliSaat}`;

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
        window.location.reload();
      }, 1000);
    } catch (err) {
      console.error("Randevu kaydı başarısız", err);
      alert("Randevu oluşturulamadı!");
    }
  };

  const handleRandevuIptal = async (randevuId) => {
    const onay = window.confirm("Bu randevuyu iptal etmek istediğinizden emin misiniz?");
    if (!onay) return;

    try {
      await axios.delete(`http://localhost:8080/api/rezervasyonlar/${randevuId}`);
      setRandevularim(prev => prev.filter(r => r.id !== randevuId));
    } catch (err) {
      console.error("Randevu silinemedi", err);
      alert("İptal işlemi sırasında bir hata oluştu.");
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
              <h4>{secilenDoktor.ad} {secilenDoktor.soyad}</h4>

              <div className="tarih-secimi">
                <h4>Tarih Seçimi</h4>
                <input
                  type="date"
                  value={secilenTarih}
                  onChange={(e) => {
                    const secilenGun = new Date(e.target.value).getDay();
                    if (secilenGun === 0 || secilenGun === 6) {
                      alert("Sadece hafta içi bir gün seçebilirsiniz!");
                      setSecilenTarih("");
                    } else {
                      setSecilenTarih(e.target.value);
                    }
                  }}
                  min={bugununTarihiStringFormatta()}
                  max={buYilinSonGunuStringFormatta()}
                  onKeyDown={(e) => e.preventDefault()}
                  onPaste={(e) => e.preventDefault()}
                  onDrop={(e) => e.preventDefault()}
                />
              </div>

              <h4>Saat Seçimi</h4>
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
              <div className="randevu-karti-container">
                {randevularim.map((r, i) => (
                  <div key={i} className="randevu-karti">
                    <p><strong>Tarih:</strong> {new Date(r.tarih).toLocaleDateString()}</p>
                    <p><strong>Saat:</strong> {new Date(r.tarih).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}</p>
                    <p><strong>Doktor:</strong> {r.doktor?.ad} {r.doktor?.soyad}</p>
                    <p><strong>Uzmanlık:</strong> {r.doktor?.uzmanlikAlani}</p>
                    <div className="iptal-buton-sarici">
                      <button className="iptal-buton" onClick={() => handleRandevuIptal(r.id)}>Randevu İptali</button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>

        {modalAcik && (
          <div className="modal">
            <div className="modal-icerik">
              <h4>Randevuyu Onayla</h4>
              <p>Doktor: {secilenDoktor.ad} {secilenDoktor.soyad}</p>
              <p>Tarih: {secilenTarih}</p>
              <p>Saat: {seciliSaat}</p>
              <div className="modal-butons">
                <button onClick={handleRandevuOnayla}>Onayla</button>
                <button onClick={() => setModalAcik(false)}>İptal</button>
              </div>
            </div>
          </div>
        )}

        <div className="geri-buton-sol-alt">
          <button onClick={() => window.history.back()}>← Geri</button>
        </div>
      </div>
    </div>
  );
}

export default DoktorRandevuAl;
