import React, { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import './EventAccess.css';


const EventAccess = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [eventos, setEventos] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEventos = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/eventos");
        if (response.ok) {
          const data = await response.json();
          setEventos(data);
        } else {
          setError("No se pudieron cargar los eventos de acceso.");
        }
      } catch (error) {
        setError("Error al obtener los eventos.");
      }
    };

    fetchEventos();
  }, []);

  // Filtrar por DNI del usuario
  const eventosFiltrados = eventos.filter(
    (evento) => evento.dniCliente === user?.dni
  );

  return (
    <div className="access-container">
      <div className="background-overlay">
        <div className="access-content">
          <h2>Eventos de Acceso</h2>
          {error && <p className="error-message">{error}</p>}

          <table className="access-table">
            <thead>
              <tr>
                <th>Fecha</th>
                <th>ID Cerradura</th>
                <th>Acción</th>
                <th>Éxito</th>
              </tr>
            </thead>
            <tbody>
              {eventosFiltrados.map((evento) => (
                <tr key={evento.id}>
                  <td>{new Date(evento.fechaHora).toLocaleString()}</td>
                  <td>{evento.idCerradura}</td>
                  <td>{evento.accion}</td>
                  <td>{evento.exito ? "✅ Sí" : "❌ No"}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <button className="back-button" onClick={() => navigate(-1)}>
            ⬅ Volver
          </button>
        </div>
      </div>
    </div>
  );
};

export default EventAccess;
