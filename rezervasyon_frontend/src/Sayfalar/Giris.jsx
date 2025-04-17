import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';  // { Link } sayfalar arası geçişi sağlar
import axios from 'axios';

function Giris() {
    // State tanımlamaları
    const [email, setEmail] = useState('');
    const [sifre, setSifre] = useState('');
    const [error, setError] = useState('');  // Hata mesajlarını tutmak için
    const navigate = useNavigate();  // Sayfalar arası yönlendirme için

    const handleSubmit = async (e) => {
        e.preventDefault();  // Sayfa yenilenmesini engelle
        try {
            // Backend'e veri gönderme
            const response = await axios.post('http://localhost:8080/api/kullanicilar/giris', {
                email,
                sifre,
            });

            // Başarılı giriş sonrası kullanıcıyı başka bir sayfaya yönlendirme
            console.log(response.data);  // Backend'den gelen cevabı kontrol et
            navigate('/anasayfa');  // Giriş başarılıysa dashboard sayfasına yönlendir

        } catch (error) {
            // Hata durumunda error state'ini güncelleme
            setError('Giriş hatası. E-posta veya şifre yanlış.');
            console.error('Giriş hatası:', error);
        }
    };

    return (
        <div className="login-box container mt-5">
            <h3 className="form-title">Giriş Yap</h3>

            {/* Hata mesajı varsa ekranda göster */}
            {error && <div className="alert alert-danger">{error}</div>}

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">E-posta</label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        placeholder="E-posta adresinizi girin"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}  // Input değiştiğinde state güncelleniyor
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="sifre" className="form-label">Şifre</label>
                    <input
                        type="password"
                        className="form-control"
                        id="sifre"
                        placeholder="Şifrenizi girin"
                        value={sifre}
                        onChange={(e) => setSifre(e.target.value)}  // Input değiştiğinde state güncelleniyor
                    />
                </div>

                <button type="submit" className="btn btn-primary w-100">Giriş Yap</button>

                <div className="form-footer">
                    <span>Hesabınız yok mu? </span>
                    <Link to="/kayit">Kayıt Ol</Link>
                </div>
            </form>
        </div>
    );
}

export default Giris;
