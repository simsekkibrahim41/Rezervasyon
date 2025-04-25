import React from 'react'; 
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import './index.css';

//SAYFA GEÇİŞ YAPISI İMPORT EDİLİYOR

import AdminPanel from "./Sayfalar/AdminPanel";
import Giris from './Sayfalar/Giris';
import Kayit from './Sayfalar/Kayit';
import RezervasyonAnaSayfa from './Sayfalar/RezervasyonAnaSayfa';
import DoktorRandevuAl from './Sayfalar/DoktorRandevuAl';
import RestoranRezervasyon from "./Sayfalar/RestoranRezervasyon"; 



function App() {
  return (
    <Router>                 
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />       
        <Route path="/anasayfa" element={<RezervasyonAnaSayfa />} />
        <Route path="/login" element={<Giris />} />
        <Route path="/kayit" element={<Kayit />} />
        <Route path="/admin" element={<AdminPanel />} />
        <Route path="/doktor-randevu" element={<DoktorRandevuAl />} />
        <Route path="/restoran" element={<RestoranRezervasyon />} />
      </Routes>         
    </Router> // sAYFA GEÇİŞLERİ BU YAPIYLA YÖNETİLİR, Routes İSE ADRESLERİN LİSTELENDİĞİ BLOGU KAPSAR
  );
}


export default App;
