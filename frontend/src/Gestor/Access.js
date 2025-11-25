import React, { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext"; 
import { useNavigate } from "react-router-dom";
import './Access.css';

const Access = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [accessData, setAccessData] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAccess = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/accesos");
        if (response.ok) {
          const data = await response.json();
          setAccessData(data);
        } else {
          setError("No se pudieron cargar los accesos.");
        }
      } catch (error) {
        setError("Hubo un error al obtener los accesos.");
      }
    };

    fetchAccess();
  }, []);

  const filteredAccess = accessData.filter(
    (access) => access.gestor.dni === user?.dni || access.cliente.dni === user?.dni
  );

  const handleDelete = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar este acceso?")) {
      try {
        const response = await fetch(`http://localhost:8080/api/accesos/${id}`, {
          method: "DELETE",
        });

        if (response.ok) {
          setAccessData(accessData.filter(access => access.id !== id));
        } else {
          setError("Hubo un error al eliminar el acceso.");
        }
      } catch (error) {
        setError("Error en la conexión al eliminar el acceso.");
      }
    }
  };

  return (
    <div className="access-container">
      <div className="background-overlay">
        <div className="access-content">
          <h2>Accesos Registrados</h2>
          {error && <p className="error-message">{error}</p>}
          <ul className="access-list">
            {filteredAccess.map((access) => (
              <li key={access.id} className="access-item">
                <p><strong>Fecha Inicio:</strong> {new Date(access.fechainicio).toLocaleString()}</p>
                <p><strong>Fecha Fin:</strong> {new Date(access.fechafin).toLocaleString()}</p>
                <p><strong>Dirección:</strong> {access.direccion}</p>
                <p><strong>Cliente:</strong> +34 {access.cliente.telefono} - {access.cliente.nombre}</p>
                <button 
                  className="edit-button"
                  onClick={() => navigate(`/ManagementPanel/EditAccess/${access.id}`)}
                >
                  Editar
                </button>
                <button 
                  className="delete-button"
                  onClick={() => handleDelete(access.id)}
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

export default Access;
