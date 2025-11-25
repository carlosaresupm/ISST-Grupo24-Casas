import React, { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext"; 
import { useNavigate } from "react-router-dom"; 
import './Lock.css';

const Lock = () => {
  const { user } = useAuth(); // Obtener el usuario autenticado
  const navigate = useNavigate();
  const [locksData, setLocksData] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLocks = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/cerraduras");
        if (response.ok) {
          const data = await response.json();
          setLocksData(data);
        } else {
          setError("No se pudieron cargar las cerraduras.");
        }
      } catch (error) {
        setError("Hubo un error al obtener las cerraduras.");
      }
    };

    fetchLocks();
  }, []);

  // Filtrar cerraduras que coincidan con el DNI del usuario autenticado
  const filteredLocks = locksData.filter(lock => lock.gestor.dni === user?.dni);

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar esta cerradura?")) {
      try {
        const response = await fetch(`http://localhost:8080/api/cerraduras/${id}`, {
          method: "DELETE",
        });

        if (response.ok) {
          setLocksData(locksData.filter(lock => lock.id !== id)); // Filtrar la cerradura eliminada
        } else {
          setError("Hubo un error al eliminar la cerradura.");
        }
      } catch (error) {
        setError("Error en la conexión al eliminar la cerradura.");
      }
    }
  };

  return (
    <div className="lock-container">
      <div className="background-overlay">
        <div className="lock-content">
          <h2>Cerraduras Registradas</h2>
          {error && <p className="error-message">{error}</p>}
          <ul className="lock-list">
            {filteredLocks.map((lock) => (
              <li key={lock.id} className="lock-item">
                <p><strong>Nombre:</strong> {lock.nombre}</p>
                <p><strong>Device Id:</strong> {lock.deviceId}</p>
                <p><strong>Dirección:</strong> {lock.direccion}</p>
                <p><strong>Estado:</strong> {lock.abierto ? "Abierta" : "Cerrada"}</p>
                <button 
                  className="edit-button"
                  onClick={() => navigate(`/ManagementPanel/EditLock/${lock.id}`)} // Redirigir a la pantalla de edición
                >
                  Editar
                </button>
                <button 
                  className="delete-button"
                  onClick={() => handleDelete(lock.id)} // Eliminar la cerradura
                >
                  Eliminar
                </button>
              </li>
            ))}
            <button className="back-button" onClick={() => navigate(-1)}>
              ⬅ Volver
            </button>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Lock;
