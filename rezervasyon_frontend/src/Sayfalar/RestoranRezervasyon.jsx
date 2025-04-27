import React, { useEffect, useState } from "react";
import "../RestoranRezervasyon.css";
import axios from "axios";

function RestoranRezervasyon() {
  const [sehirler, setSehirler] = useState([]);
  const [mutfakTurleri, setMutfakTurleri] = useState([]);
  const [secilenSehir, setSecilenSehir] = useState("");
  const [secilenMutfak, setSecilenMutfak] = useState("");
  const [restoranlar, setRestoranlar] = useState([]);

  useEffect(() => {
    // Şehirleri çek
    axios.get("http://localhost:8080/api/restoranlar/sehirler")
      .then(res => setSehirler(res.data))
      .catch(err => console.error("Şehirler alınamadı", err));

    // Mutfak türlerini çek
    axios.get("http://localhost:8080/api/restoranlar/mutfakTurleri")
      .then(res => setMutfakTurleri(res.data))
      .catch(err => console.error("Mutfak türleri alınamadı", err));
  }, []);

  useEffect(() => {
    if (secilenSehir && secilenMutfak) {
      console.log("Seçilen Şehir:", secilenSehir);
      console.log("Seçilen Mutfak Türü:", secilenMutfak);
      axios.get(`http://localhost:8080/api/restoranlar/filtreli?sehir=${encodeURIComponent(secilenSehir)}&mutfakTuru=${encodeURIComponent(secilenMutfak)}`)
        .then(res => setRestoranlar(res.data))
        .catch(err => console.error("Restoranlar alınamadı", err));
    }
  }, [secilenSehir, secilenMutfak]);

  const formatMutfakTuru = (mutfak) => {
    return mutfak
      .replaceAll("_", " ")
      .replaceAll("I", "İ")
      .toLowerCase()
      .replace(/\b\w/g, (c) => c.toUpperCase());
  };

  return (
    <div className="restoran-rezervasyon-container">
      
      {/* Sol Panel */}
      <div className="restoran-sol-panel">
        <h3>Şehir Seç</h3>
        <select
          className="select-box"
          value={secilenSehir}
          onChange={(e) => setSecilenSehir(e.target.value)}
        >
          <option value="">-- Şehir Seçiniz --</option>
          {sehirler.map((sehir, index) => (
            <option key={index} value={sehir}>
              {sehir}
            </option>
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

      {/* Sağ Panel */}
      <div className="restoran-sag-panel">
        {secilenSehir && secilenMutfak ? (
          <>
            <h3>Restoranlar</h3>
            {restoranlar.length > 0 ? (
              restoranlar.map((restoran) => (
                <div key={restoran.id} className="restoran-karti">
                  <h4>{restoran.ad}</h4>
                  <p>{restoran.aciklama}</p>
                  <p><strong>Şehir:</strong> {restoran.sehir}</p>
                  <p><strong>Mutfak:</strong> {formatMutfakTuru(restoran.mutfakTuru)}</p>
                  <p><strong>Boş Masa Sayısı:</strong> {restoran.bosMasaSayisi}</p>
                </div>
              ))
            ) : (
              <p>Bu kriterlere uygun restoran bulunamadı.</p>
            )}
          </>
        ) : (
          <p>Şehir ve mutfak türü seçilmedi.</p>
        )}
      </div>

    </div>
  );
}

export default RestoranRezervasyon;
