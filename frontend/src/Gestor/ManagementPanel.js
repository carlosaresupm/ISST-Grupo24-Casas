import React from "react";
import "./ManagementPanel.css";

// Componente para la pantalla de Panel de Gestión del Gestor
const ManagementPanel = () => {
  return (
    <div className="management-container">
      {/* Fondo translúcido para mejorar la visibilidad del contenido */}
      <div className="background-overlay">
        <div className="management-content">
          <h2>Panel de gestión</h2>

          {/* Lista de opciones del panel de gestión */}
          <ul className="management-options">
            <li>
              <a href="/ManagementPanel/AddLock">
                🔒 Agregar una cerradura
              </a>
            </li>
            <li>
              <a href="/ManagementPanel/Lock">
                🔒 Ver cerraduras
              </a>
            </li>
            <li>
              <a href="/ManagementPanel/AddAccess">
                🔑 Registrar un acceso en Google Calendar
              </a>
            </li>
            <li>
              <a href="/ManagementPanel/Access">
                🔑 Ver accesos
              </a>
            </li>
            <li>
              <a href="/ManagementPanel/Clients">
                👥 Clientes
              </a>
            </li>
            {/* <li>
              <a href="/google-calendar">
                📅 Google Calendar
              </a>
            </li> */}
            <li>
              <a href="/ManagementPanel/EventAccess">
                📜 Historial de accesos
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ManagementPanel;
