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
  const [rezervasyonlarim, setRezervasyonlarim] = useState([]);
  const [secilenTarih, setSecilenTarih] = useState("");
  const [doluSaatler, setDoluSaatler] = useState([]);

  const kullanici = JSON.parse(localStorage.getItem("kullanici"));
  const kullaniciId = kullanici?.id;

  useEffect(() => {
    axios.get("http://localhost:8080/api/restoranlar/sehirler")
      .then(res => setSehirler(res.data))
      .catch(err => console.error("Şehirler alınamadı", err));

    axios.get("http://localhost:8080/api/restoranlar/mutfakTurleri")
      .then(res => setMutfakTurleri(res.data))
      .catch(err => console.error("Mutfak türleri alınamadı", err));

    axios.get(`http://localhost:8080/api/rezervasyonlar/kullanici/${kullaniciId}?tip=RESTORAN`)
      .then(res => setRezervasyonlarim(res.data))
      .catch(err => console.error("Restoran rezervasyonları alınamadı", err));
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

  useEffect(() => {
    if (secilenRestoran && secilenTarih) {
      axios
        .get(`http://localhost:8080/api/restoranRezervasyonlar/doluSaatler?restoranId=${secilenRestoran.id}&tarih=${secilenTarih}`)
        .then(res => setDoluSaatler(res.data))
        .catch(err => console.error("Dolu saatler alınamadı", err));
    }
  }, [secilenRestoran, secilenTarih]);

  const masaListesi = [1, 2, 3, 4, 5];
  const saatListesi = ["10:00", "12:00", "14:00", "16:00"];

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
        tarih: secilenTarih,
        aciklama: `Masa ${secilenMasa} için saat ${secilenSaat} rezervasyonu`
      };

      console.log("Gönderilen Rezervasyon DTO:", rezervasyonDto);

      await axios.post("http://localhost:8080/api/restoranRezervasyonlar", rezervasyonDto);

      alert("Rezervasyon başarıyla oluşturuldu!");
      window.location.reload();
    } catch (error) {
      console.error("Rezervasyon kaydedilemedi", error);
      if (error.response && error.response.status === 409) {
        alert("Bu masa, bu tarih ve saatte zaten rezerve edilmiştir.");
        window.location.reload();
      } else {
        alert("Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin.");
      }
    }
  };

  const rezervasyonIptalEt = async (id) => {
    const onay = window.confirm("Bu rezervasyonu iptal etmek istediğinizden emin misiniz?");
    if (!onay) return;

    try {
      await axios.delete(`http://localhost:8080/api/rezervasyonlar/${id}`);
      alert("Rezervasyon iptal edildi.");
      window.location.reload();
    } catch (err) {
      console.error("Silme hatası:", err);
      alert("İptal işlemi başarısız oldu.");
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

            <div className="tarih-secimi">
              <h4>Tarih Seçimi</h4>
              <input
                type="date"
                value={secilenTarih}
                onChange={(e) => setSecilenTarih(e.target.value)}
                min={new Date().toISOString().split("T")[0]}
                max={`${new Date().getFullYear()}-12-31`}
                onKeyDown={(e) => e.preventDefault()}
                onPaste={(e) => e.preventDefault()}
                onDrop={(e) => e.preventDefault()}
              />
            </div>

            {secilenTarih && (
              <>
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
              </>
            )}

            {secilenMasa && (
              <>
                <h3>Saat Seçimi (Masa {secilenMasa})</h3>
                <div className="saat-secim">
                  {saatListesi.map((saat) => (
                    <button
                      key={saat}
                      className={doluSaatler.includes(saat) ? "saat-button dolu" : "saat-button bos"}
                      disabled={doluSaatler.includes(saat)}
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
            {secilenSehir && secilenMutfak ? (
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
            ) : (
              <>
                <h3>Restoran Rezervasyonlarım</h3>
                {rezervasyonlarim.length > 0 ? (
                  rezervasyonlarim.map((rez, index) => (
                    <div key={index} className="rezervasyon-karti">
                      <p><strong>Tarih:</strong> {new Date(rez.tarih).toLocaleDateString()}</p>
                      <p><strong>Restoran:</strong> {rez.restoran?.ad}</p>
                      <p><strong>Detay:</strong> {rez.aciklama}</p>

                      <div className="iptal-buton-sarici">
                        <button
                          className="iptal-buton"
                          onClick={() => rezervasyonIptalEt(rez.id)}
                        >
                          Rezervasyon İptali
                        </button>
                      </div>
                    </div>
                  ))
                ) : (
                  <p>Henüz restoran rezervasyonunuz bulunmuyor.</p>
                )}
              </>
            )}
          </>
        )}
      </div>

      <div className="geri-buton-sol-alt">
        <button onClick={() => window.history.back()}>← Geri</button>
      </div>
    </div>
  );
}

export default RestoranRezervasyon;
