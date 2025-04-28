import React, { useEffect, useState } from "react";
import "../RestoranRezervasyon.css";
import axios from "axios";

function RestoranRezervasyon() {
  const [sehirler, setSehirler] = useState([]);
  const [mutfakTurleri, setMutfakTurleri] = useState([]);
  const [secilenSehir, setSecilenSehir] = useState("");
  const [secilenMutfak, setSecilenMutfak] = useState("");
  const [restoranlar, setRestoranlar] = useState([]);
  const [secilenRestoran, setSecilenRestoran] = useState(null);
  const [secilenMasa, setSecilenMasa] = useState(null);
  const [secilenSaat, setSecilenSaat] = useState(null);
  const [modalAcik, setModalAcik] = useState(false);

  // Kullanıcı bilgisi localStorage'dan doğru şekilde çekiliyor
  const kullanici = JSON.parse(localStorage.getItem("kullanici"));
  const kullaniciId = kullanici?.id;

  useEffect(() => {
    axios.get("http://localhost:8080/api/restoranlar/sehirler")
      .then(res => setSehirler(res.data))
      .catch(err => console.error("Şehirler alınamadı", err));

    axios.get("http://localhost:8080/api/restoranlar/mutfakTurleri")
      .then(res => setMutfakTurleri(res.data))
      .catch(err => console.error("Mutfak türleri alınamadı", err));
  }, []);

  useEffect(() => {
    if (secilenSehir && secilenMutfak) {
      const temizSehir = secilenSehir === "İstanbul" ? "istanbul" : secilenSehir;
      const temizMutfak = secilenMutfak
        .toUpperCase()
        .replaceAll("İ", "I")
        .replace(/\s+/g, "_");

      axios.get(`http://localhost:8080/api/restoranlar/filtreli?sehir=${encodeURIComponent(temizSehir)}&mutfakTuru=${encodeURIComponent(temizMutfak)}`)
        .then(res => setRestoranlar(res.data))
        .catch(err => console.error("Restoranlar alınamadı", err));
    }
  }, [secilenSehir, secilenMutfak]);

  const masaListesi = [1, 2, 3, 4, 5];
  const saatListesi = ["10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"];

  const rezervasyonuOnayla = async () => {
    try {
      if (!kullaniciId) {
        alert("Kullanıcı bulunamadı.");
        return;
      }

      const rezervasyonDto = {
        kullaniciId: kullaniciId,
        restoranId: secilenRestoran.id,
        masaNo: secilenMasa,
        saat: secilenSaat,
        tarih: new Date().toISOString().slice(0, 10),
        aciklama: `Masa ${secilenMasa} için saat ${secilenSaat} rezervasyonu`
      };
      

      console.log("Gönderilen Rezervasyon DTO:", rezervasyonDto);

      await axios.post("http://localhost:8080/api/restoranRezervasyonlar", rezervasyonDto);

      alert("Rezervasyon başarıyla oluşturuldu!");
      window.location.reload();
    } catch (error) {
      console.error("Rezervasyon kaydedilemedi", error);
      alert("Rezervasyon sırasında bir hata oluştu.");
    }
  };

  return (
    <div className="restoran-rezervasyon-container">
      <div className="restoran-sol-panel">
        <h3>Şehir Seç</h3>
        <select
          className="select-box"
          value={secilenSehir}
          onChange={(e) => setSecilenSehir(e.target.value)}
        >
          <option value="">-- Şehir Seçiniz --</option>
          {sehirler.map((sehir, index) => (
            <option key={index} value={sehir}>{sehir}</option>
          ))}
        </select>

        <h3>Mutfak Türü Seç</h3>
        <select
          className="select-box"
          value={secilenMutfak}
          onChange={(e) => setSecilenMutfak(e.target.value)}
        >
          <option value="">-- Mutfak Türü Seçiniz --</option>
          {mutfakTurleri.map((mutfak, index) => (
            <option key={index} value={mutfak}>
              {mutfak.replaceAll("_", " ").toLowerCase().replace(/\b\w/g, (c) => c.toUpperCase())}
            </option>
          ))}
        </select>
      </div>

      <div className="restoran-sag-panel">
        {secilenRestoran ? (
          <>
            <button className="geri-butonu" onClick={() => {
              setSecilenRestoran(null);
              setSecilenMasa(null);
              setSecilenSaat(null);
            }}>Geri</button>

            <h2>{secilenRestoran.ad} - Masa Seçimi</h2>
            <div className="masa-secim">
              {masaListesi.map((masa) => (
                <button
                  key={masa}
                  className="masa-button"
                  onClick={() => setSecilenMasa(masa)}
                >
                  Masa {masa}
                </button>
              ))}
            </div>

            {secilenMasa && (
              <>
                <h3>Saat Seçimi (Masa {secilenMasa})</h3>
                <div className="saat-secim">
                  {saatListesi.map((saat) => (
                    <button
                      key={saat}
                      className="saat-button"
                      onClick={() => {
                        setSecilenSaat(saat);
                        setModalAcik(true);
                      }}
                    >
                      {saat}
                    </button>
                  ))}
                </div>
              </>
            )}

            {modalAcik && (
              <div className="modal">
                <div className="modal-icerik">
                  <h3>Rezervasyonu Onayla</h3>
                  <p><strong>Restoran:</strong> {secilenRestoran.ad}</p>
                  <p><strong>Masa:</strong> {secilenMasa}</p>
                  <p><strong>Saat:</strong> {secilenSaat}</p>

                  <div className="modal-buton-container">
                    <button className="onayla-button" onClick={rezervasyonuOnayla}>Onayla</button>
                    <button className="iptal-button" onClick={() => setModalAcik(false)}>İptal</button>
                  </div>
                </div>
              </div>
            )}
          </>
        ) : (
          <>
            <h3>Restoranlar</h3>
            {restoranlar.length > 0 ? (
              restoranlar.map((restoran) => (
                <div key={restoran.id} className="restoran-karti" onClick={() => setSecilenRestoran(restoran)}>
                  <h4>{restoran.ad}</h4>
                  <p>{restoran.aciklama}</p>
                  <p><strong>Şehir:</strong> {restoran.sehir}</p>
                  <p><strong>Mutfak:</strong> {restoran.mutfakTuru.replaceAll("_", " ")}</p>
                  <p><strong>Boş Masa Sayısı:</strong> {restoran.bosMasaSayisi}</p>
                </div>
              ))
            ) : (
              <p>Bu kriterlere uygun restoran bulunamadı.</p>
            )}
          </>
        )}
      </div>
    </div>
  );
}

export default RestoranRezervasyon;
