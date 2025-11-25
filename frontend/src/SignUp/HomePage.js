import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <div className="message-container">
      <div className="message-container">
        <h2>Bienvenido a SmartAccess</h2>
        <p>¡Disfruta de tus vacaciones con un solo click!</p>
      </div>

      <div className="access-container">
        <div className="access-link" onClick={() => navigate('/Gestor/login')}>
          🔐 Acceder como Gestor
        </div>
        
        <div className="access-link" onClick={() => navigate('/Cliente/login')}>
          🔐 Acceder como Cliente
        </div>

        <div className="signup-link" onClick={() => navigate('/signup')}>
          ¿No estás registrado?
        </div>
      </div>
    </div>
  );
};

export default HomePage;