import React, { useEffect, useState } from "react";
import axios from "axios";

function Haberler() {
  const [haberler, setHaberler] = useState([]);
  const [hata, setHata] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/haberler")
      .then(res => {
        setHaberler(res.data);
      })
      .catch(err => {
        console.error("Haberler alınamadı:", err);
        setHata("Haberler yüklenemedi.");
      });
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>Kocaeli Belediyesi Haberler</h2>

      {hata && <p style={{ color: "red" }}>{hata}</p>}

      {haberler.length > 0 ? (
        haberler.map((haber) => (
          <div key={haber.ID} style={{ border: "1px solid #ccc", padding: "15px", marginBottom: "10px" }}>
            <h3>{haber.Title}</h3>
            <p><strong>Tarih:</strong> {haber.Date}</p>
            <p>{haber.Description}</p>
            <a href={haber.WebUrl} target="_blank" rel="noopener noreferrer">Haberi Görüntüle</a>
          </div>
        ))
      ) : (
        !hata && <p>Yükleniyor...</p>
      )}
    </div>
  );
}

export default Haberler;
