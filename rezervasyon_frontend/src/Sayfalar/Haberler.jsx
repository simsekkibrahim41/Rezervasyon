import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../Haberler.css";

function Haberler() {
  const navigate = useNavigate();               // ← geri için
  const [haberler, setHaberler] = useState([]); // Haber listesini tutacak state.
  const [hata, setHata] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios
      .post("http://localhost:8080/api/haberler", {
        PageIndex: 1,
        isHttps: true
      })
      .then(res => { // İstek başarılıysa gelen yanıt (res) içindeki res.data.Result.Items dizisini alınır
        const items = res.data.Result?.Items || [];
        setHaberler(items); //Haber listesini state’e kaydeder,
      })
      .catch(err => {
        console.error("Haberler alınamadı:", err);
        setHata("Haberler yüklenemedi.");
      })
      .finally(() => {
        setLoading(false);  // loading stati her halükarda kapatılır
      });
  }, []);


  if (loading || hata) {        //loading veya hata varsa erken return ile sadece ilgili mesajı gösterilir
    return (
      <div className="haber-container">
        <button className="haber-back" onClick={() => navigate(-1)}>
          ← Geri
        </button>
        <h2 className="haber-heading">Kocaeli Belediyesi Haberler</h2>
        {loading && <p className="haber-loading">Yükleniyor...</p>}
        {hata && <p className="haber-error">{hata}</p>}
      </div>
    );
  }

  return (
    <div className="haber-container">

      <button className="haber-back" onClick={() => navigate(-1)}>
        ← Geri
      </button>

      <h2 className="haber-heading">Kocaeli Belediyesi Haberler</h2>

      <div className="haber-list">
        {haberler.length === 0 ? (
          <p className="haber-empty">Henüz gösterilecek haber bulunamadı.</p>
        ) : (
          haberler.map(h => (    // Her öğe için tek tek “kart” oluşturulur
            <div className="haber-item" key={h.ID}>
              <img className="haber-img" src={h.Image} alt={h.Title} />
              <div className="haber-content">
                <h3 className="haber-title">{h.Title}</h3>
                <p className="haber-date">{h.Date}</p>
                <p className="haber-desc">{h.Description}</p>
                <a
                  className="haber-link"
                  href={h.WebUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Haberi Görüntüle →
                </a>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default Haberler;
