import React from 'react'; 
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import './index.css';

//SAYFA GEÇİŞ YAPISI İMPORT EDİLİYOR

import Giris from './Sayfalar/Giris';
import Kayit from './Sayfalar/Kayit';
import RezervasyonAnaSayfa from './Sayfalar/RezervasyonAnaSayfa';


function App() {
  return (
    <Router>                 
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />       
        <Route path="/anasayfa" element={<RezervasyonAnaSayfa />} />
        <Route path="/login" element={<Giris />} />
        <Route path="/kayit" element={<Kayit />} />
      </Routes>         
    </Router> // sAYFA GEÇİŞLERİ BU YAPIYLA YÖNETİLİR, Routes İSE ADRESLERİN LİSTELENDİĞİ BLOGU KAPSAR
  );
}


export default App;
