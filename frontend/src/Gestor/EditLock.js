import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom"; 
import './EditLock.css';

const EditLock = () => {
  const { id } = useParams(); // Obtener el ID de la cerradura desde la URL
  const navigate = useNavigate();
  const [lock, setLock] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLock = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/cerraduras/${id}`);
        if (response.ok) {
          const data = await response.json();
          setLock(data);
        } else {
          setError("No se pudo cargar la cerradura.");
        }
      } catch (error) {
        setError("Hubo un error al obtener la cerradura.");
      }
    };

    fetchLock();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/cerraduras/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(lock),
      });

      if (response.ok) {
        navigate("/ManagementPanel/Lock"); // Redirigir a la lista de cerraduras
      } else {
        setError("Error al actualizar la cerradura.");
      }
    } catch (error) {
      setError("Error en la conexión al actualizar la cerradura.");
    }
  };

  if (!lock) return <p>Cargando...</p>;

  return (
    <>
    <div className="edit-lock-container">
      {error && <p className="error-message">{error}</p>}
      <form onSubmit={handleSubmit}>
      <h2>Editar Cerradura</h2>
        <div className="form-group">
          <label htmlFor="nombre">Nombre</label>
          <input 
            type="text" 
            id="nombre" 
            value={lock.nombre} 
            onChange={(e) => setLock({ ...lock, nombre: e.target.value })} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="deviceId">Device Id</label>
          <input 
            type="text" 
            id="deviceId" 
            value={lock.deviceId} 
            onChange={(e) => setLock({ ...lock, deviceId: e.target.value })} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="direccion">Dirección</label>
          <input 
            type="text" 
            id="direccion" 
            value={lock.direccion} 
            onChange={(e) => setLock({ ...lock, direccion: e.target.value })} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="clave">Clave</label>
          <input 
            type="text" 
            id="clave" 
            value={lock.clave} 
            onChange={(e) => setLock({ ...lock, clave: e.target.value })} 
            required 
          />
        </div>
        <div className="form-group">
          <label htmlFor="abierto">Estado</label>
          <select 
            id="abierto" 
            value={lock.abierto ? "abierta" : "cerrada"} 
            onChange={(e) => setLock({ ...lock, abierto: e.target.value === "abierta" })}
            required
          >
            <option value="abierta">Abierta</option>
            <option value="cerrada">Cerrada</option>
          </select>
        </div>
        <button type="submit">Guardar cambios</button>
            <button className="back-button" onClick={() => navigate(-1)}>
              ⬅ Volver
            </button>
      </form>
    </div>
    </>
  );
};

export default EditLock;
