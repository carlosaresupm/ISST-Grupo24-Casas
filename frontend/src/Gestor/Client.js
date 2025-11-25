import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import './Client.css';

const Client = () => {
  const [clients, setClients] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchClients = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/clientes");
        
        if (response.ok) {
          const data = await response.json();
          console.log("Datos obtenidos:", data);  // Agregar un log para verificar la respuesta
          setClients(data);
        } else {
          setError("Error al obtener los clientes.");
        }
      } catch (error) {
        setError("Hubo un error al obtener los clientes.");
        console.error("Error:", error);
      }
    };

    fetchClients();
  }, []);

  return (
    <div className="client-container">

      {error && <p className="error-message">{error}</p>}

      {/* Verifica si hay clientes para mostrar */}
      {clients.length === 0 ? (
        <p>No hay clientes disponibles.</p>
      ) : (
        <ul className="client-list">
          <h2>Lista de Clientes</h2>
          {clients.map((client) => (
            <li key={client.dni} className="client-item">
              <p><strong>DNI:</strong> {client.dni}</p>
              <p><strong>Nombre:</strong> {client.nombre} {client.apellido}</p>
              <p><strong>Fecha de Nacimiento:</strong> {new Date(client.fechaNacimiento).toLocaleDateString()}</p>
              <p><strong>Email:</strong> {client.email}</p>
              <p><strong>Teléfono:</strong> {client.telefono}</p>
              <p><strong>Dirección:</strong> {client.direccion}</p>
            </li>
          ))}
          <button className="back-button" onClick={() => navigate(-1)}>
            ⬅ Volver
          </button>
        </ul>
      )}
    </div>
  );
};

export default Client;
