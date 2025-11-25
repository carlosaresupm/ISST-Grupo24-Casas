import React, { useState } from "react";
import "./SignUp.css";

const SignUp = () => {
  // Estado para almacenar si el usuario es "gestor" o "cliente"
  const [userType, setUserType] = useState("gestor");

  // Estado para manejar los datos del formulario
  const [formData, setFormData] = useState({
    dni: "",
    nombre: "",
    apellido: "",
    fechaNacimiento: "",
    email: "",
    telefono: "",
    password: "",
    direccion: "" // Solo se usa si el usuario es cliente
  });

  // Estado para manejar mensajes de error y éxito
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");

  // Función para actualizar el estado cuando el usuario escribe en los inputs
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Función para manejar el envío del formulario
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccessMessage("");

    // Validación básica: verifica que los campos obligatorios no estén vacíos
    if (!formData.dni || !formData.nombre || !formData.email || !formData.password) {
      setError("Todos los campos obligatorios deben ser completados.");
      return;
    }

    try {
      // Define la ruta del endpoint dependiendo del tipo de usuario
      const endpoint = userType === "gestor" ? "gestores" : "clientes";

      // Realiza la solicitud al backend para registrar al usuario
      const response = await fetch(`http://localhost:8080/api/${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      // Convierte la respuesta a JSON
      const data = await response.json();

      if (response.ok) {
        // Si el registro es exitoso, muestra un mensaje de éxito y resetea el formulario
        setSuccessMessage("Registro exitoso.");
        setFormData({
          dni: "",
          nombre: "",
          apellido: "",
          fechaNacimiento: "",
          email: "",
          telefono: "",
          password: "",
          direccion: ""
        });
      } else {
        // Si hay un error, muestra el mensaje del backend o uno por defecto
        setError(data.message || "Error en el registro. Inténtalo nuevamente.");
      }
    } catch (error) {
      console.error("Error en la solicitud", error);
      setError("Hubo un problema con la solicitud. Revisa tu conexión.");
    }
  };

  return (
    <div className="page-wrapper">
    <div className="signup-container">
      <h2>Registro</h2>

      {/* Selector de tipo de usuario (Gestor o Cliente) */}
      <select onChange={(e) => setUserType(e.target.value)} value={userType}>
        <option value="gestor">Gestor</option>
        <option value="cliente">Cliente</option>
      </select>

      {/* Muestra los mensajes de error o éxito */}
      {error && <p className="error-message">{error}</p>}
      {successMessage && <p className="success-message">{successMessage}</p>}

      {/* Formulario de registro */}
      <form onSubmit={handleSubmit}>
        <input type="text" name="dni" placeholder="DNI" value={formData.dni} onChange={handleChange} required />
        <input type="text" name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required />
        <input type="text" name="apellido" placeholder="Apellido" value={formData.apellido} onChange={handleChange} required />
        <input type="date" name="fechaNacimiento" value={formData.fechaNacimiento} onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
        <input type="tel" name="telefono" placeholder="Teléfono" value={formData.telefono} onChange={handleChange} required />
        <input type="password" name="password" placeholder="Contraseña" value={formData.password} onChange={handleChange} required />

        {/* El campo de dirección solo se muestra si el usuario es "cliente" */}
        {userType === "cliente" && (
          <input type="text" name="direccion" placeholder="Dirección" value={formData.direccion} onChange={handleChange} required />
        )}

        {/* Botón para enviar el formulario */}
        <button type="submit">Registrarse</button>
      </form>
    </div>
    </div>
  );
};

export default SignUp;