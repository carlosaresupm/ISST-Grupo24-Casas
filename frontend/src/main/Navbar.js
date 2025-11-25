import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(null); // Estado para el rol del usuario
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = JSON.parse(localStorage.getItem('user'));

    if (token && userData) {
      setIsLoggedIn(true);
      setRole(userData.role); // Extrae el rol desde localStorage
    } else {
      setIsLoggedIn(false);
      setRole(null);
    }
  }, [location]); // Se ejecuta cuando cambia la ruta

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setIsLoggedIn(false);
    setRole(null);
    navigate('/Login');
  };

  return (
    <nav className="navbar">
      <ul className="navbar-list">
        <li>
          <Link to="/" className="navbar-link">
            <a className="navbar-logo" />
          </Link>
        </li>
        <li className="navbar-item">
          <Link to="/" className="navbar-link">Home</Link>
        </li>

        {!isLoggedIn ? (
          <>
            <li className="navbar-item">
              <Link to="/Gestor/Login" className="navbar-link">Login como Gestor</Link>
            </li>
            <li className="navbar-item">
              <Link to="/Cliente/Login" className="navbar-link">Login como Cliente</Link>
            </li>
            <li className="navbar-item">
              <Link to="/SignUp" className="navbar-link">Sign Up</Link>
            </li>
          </>
        ) : (
          <>
            {role === 'GESTOR' && (
              <li className="navbar-item">
                <Link to="/ManagementPanel" className="navbar-link">Panel de Gestor</Link>
              </li>
            )}
            {role === 'CLIENTE' && (
              <li className="navbar-item">
                <Link to="/HomeUser" className="navbar-link">Panel de Usuario</Link>
              </li>
            )}
            <li className="navbar-item-logout">
              <button className="navbar-link logout-button" onClick={handleLogout}>
                Logout
              </button>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
