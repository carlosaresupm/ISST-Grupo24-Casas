import React, { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import './AddLock.css';

const AddLock = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    direccion: '',
    nombre: '',
    abierto: false,
    clave: '',
    deviceId: '', 
    gestor: { dni: user?.dni || "" }
  });

  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    if (type === "checkbox") {
      setFormData({ ...formData, [name]: checked });
    } else if (name === "gestor.dni") {
      setFormData({ ...formData, gestor: { ...formData.gestor, dni: value } });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccessMessage("");

    try {
      const payload = { ...formData };

      const response = await fetch("http://localhost:8080/api/cerraduras", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        setSuccessMessage("Cerradura añadida.");
        setFormData({
          direccion: '',
          nombre: '',
          abierto: false,
          clave: '',
          deviceId: '', 
          gestor: { dni: user?.dni || "" }
        });
      } else {
        setError("Error en el registro de la cerradura.");
      }
    } catch (error) {
      console.error('Error al registrar la cerradura:', error);
      alert('Hubo un error al registrar la cerradura');
    }
  };

  return (
    <div className="add-access-container">
      <div className="background-overlay">
        <div className="add-access-content">
          <h2>Registrar Cerradura</h2>

          {error && <p className="error-message">{error}</p>}
          {successMessage && <p className="success-message">{successMessage}</p>}

          <form onSubmit={handleSubmit}>
            <label>ID Cerradura</label>
            <input type="text" name="nombre" value={formData.nombre} onChange={handleChange} required />

            <label>Dirección</label>
            <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} required />

            <label>Clave</label>
            <input type="text" name="clave" value={formData.clave} onChange={handleChange} required />

            <label>Device ID (Seam)</label>
            <input type="text" name="deviceId" value={formData.deviceId} onChange={handleChange} required />

            <button type="submit" className="submit-button">Registrar Cerradura</button>
          </form>
            <button className="back-button" onClick={() => navigate(-1)}>
              ⬅ Volver
            </button>
        </div>
      </div>
    </div>
  );
};

export default AddLock;