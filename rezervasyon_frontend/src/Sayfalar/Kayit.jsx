import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';  // React Router ile sayfa yönlendirmeleri için
import axios from 'axios';  // API ile iletişim için Axios kullanıyoruz

function Kayit() {
    // State tanımlamaları: Kullanıcıdan alınacak bilgiler
    const [ad, setAd] = useState('');  // Kullanıcının adı
    const [soyad, setSoyad] = useState('');  // Kullanıcının soyadı
    const [email, setEmail] = useState('');  // Kullanıcının e-posta adresi
    const [sifre, setSifre] = useState('');  // Kullanıcının şifresi
    const [error, setError] = useState('');  // Hata mesajlarını tutacak state

    const [showPopup, setShowPopup] = useState(false);
    const [popupMesaji, setPopupMesaji] = useState("");

    const navigate = useNavigate();  // Başarılı kayıt sonrası yönlendirme için

    // Form gönderildiğinde çalışacak fonksiyon
    const handleSubmit = async (e) => {
        e.preventDefault();  // Sayfa yenilenmesini engelle

        // Form doğrulama (eğer herhangi bir alan boşsa hata mesajı göster)
        if (!ad || !soyad || !email || !sifre) {
            setError('Lütfen tüm alanları doldurun!');
            return;
        }

        // Şifre uzunluğu kontrolü (en az 6 karakter)
        if (sifre.length < 6) {
            setError('Şifre en az 6 karakter olmalıdır.');
            return;
        }

        try {
            // Backend'e POST isteği gönderiyoruz
            const response = await axios.post('http://localhost:8080/api/kullanicilar/kayit', {

                ad,    // Kullanıcının adı
                soyad, // Kullanıcının soyadı
                email, // Kullanıcının e-posta adresi
                sifre, // Kullanıcının şifresi
                rol: 'KULLANICI', // Rol kısmını sabit "KULLANICI" olarak belirliyoruz
            });

            console.log(response.data);  // Backend'den gelen cevabı konsola yazdır

            // "Doğrulama bağlantısı mailinize gönderildi: ..." mesajı
            setPopupMesaji(response.data); // Backend'den gelen mesajı al
            setShowPopup(true);
        } catch (error) {
            // Hata durumunda error state'ini güncelle
            setError('Kayıt işlemi sırasında bir hata oluştu');
            console.error('Kayıt hatası:', error);
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="glass-box p-4" style={{ width: '100%', maxWidth: '400px' }}>
                <h3 className="form-title">Kayıt Ol</h3>

                {/* Hata mesajı varsa ekranda göster */}
                {error && <div className="alert alert-danger">{error}</div>}

                <form onSubmit={handleSubmit}>
                    {/* Ad Input */}
                    <div className="mb-3">
                        <label htmlFor="ad" className="form-label">Ad</label>
                        <input
                            type="text"
                            className="form-control"
                            id="ad"
                            placeholder="Adınızı girin"
                            value={ad}
                            onChange={(e) => setAd(e.target.value)} // State güncelleniyor
                        />
                    </div>

                    {/* Soyad Input */}
                    <div className="mb-3">
                        <label htmlFor="soyad" className="form-label">Soyad</label>
                        <input
                            type="text"
                            className="form-control"
                            id="soyad"
                            placeholder="Soyadınızı girin"
                            value={soyad}
                            onChange={(e) => setSoyad(e.target.value)} // State güncelleniyor
                        />
                    </div>

                    {/* E-posta Input */}
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">E-posta</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            placeholder="E-posta adresinizi girin"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)} // State güncelleniyor
                        />
                    </div>

                    {/* Şifre Input */}
                    <div className="mb-3">
                        <label htmlFor="sifre" className="form-label">Şifre</label>
                        <input
                            type="password"
                            className="form-control"
                            id="sifre"
                            placeholder="Şifrenizi girin"
                            value={sifre}
                            onChange={(e) => setSifre(e.target.value)} // State güncelleniyor
                        />
                    </div>

                    {/* Kayıt ol butonu */}
                    <button type="submit" className="btn btn-success w-100">Kayıt Ol</button>

                    {/* Kayıt olmuş bir kullanıcı varsa, giriş yap linki */}
                    <div className="form-footer">
                        <span>Zaten bir hesabınız var mı? </span>
                        <Link to="/" className="text-info">Giriş Yap</Link>
                    </div>
                </form>
            </div>
            {showPopup && (
                <div className="popup-overlay">
                    <div className="popup-content">
                        <p>{popupMesaji}</p>
                        <button onClick={() => {
                            setShowPopup(false);
                            navigate("/login");
                        }}>
                            Tamam
                        </button>
                    </div>
                </div>
            )}





        </div>
    );
}

export default Kayit;
