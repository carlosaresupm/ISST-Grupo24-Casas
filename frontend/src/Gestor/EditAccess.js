import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import './EditAccess.css';

const EditAccess = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [access, setAccess] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAccess = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/accesos/${id}`);
        if (response.ok) {
          const data = await response.json();
          setAccess(data);
        } else {
          setError("Acceso no encontrado.");
        }
      } catch (error) {
        setError("Error al cargar el acceso.");
      }
    };

    fetchAccess();
  }, [id]);

  const handleChange = (e) => {
    setAccess({ ...access, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/accesos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(access),
      });

      if (response.ok) {
        navigate("/ManagementPanel/Access"); 
      } else {
        setError("Error al actualizar el acceso.");
      }
    } catch (error) {
      setError("Error en la conexión.");
    }
  };

  if (error) return <p className="error-message">{error}</p>;
  if (!access) return <p>Cargando...</p>;

  return (
    <>
    <div className="edit-access-container">
      <form onSubmit={handleSubmit}>
      <h2>Editar Acceso</h2>
        <label>Fecha Inicio:</label>
        <input type="datetime-local" name="fechainicio" value={access.fechainicio} onChange={handleChange} required />

        <label>Fecha Fin:</label>
        <input type="datetime-local" name="fechafin" value={access.fechafin} onChange={handleChange} required />

        <label>Dirección:</label>
        <input type="text" name="direccion" value={access.direccion} onChange={handleChange} required />
        
        <label>Clave:</label>
        <input type="text" name="clave" value={access.clave} onChange={handleChange} required />

        <button type="submit" className="submit-button">Guardar Cambios</button>
            <button className="back-button" onClick={() => navigate(-1)}>
              ⬅ Volver
            </button>
      </form>
    </div>
    </>
  );
};

export default EditAccess;
