import React, { useState } from 'react';
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import './AddAccess.css';
import GoogleLoginButton from "../SignUp/GoogleLoginButton";

const AddAccess = () => {
    // Estado para los datos del formulario
    const { user } = useAuth();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        clave: '',
        fechainicio: '',
        fechafinal: '',
        direccion: '',
        cerradura: '',
        gestor: { dni: user?.dni || "" },
        cliente: { dni: '' }
    });

    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState("");

    // Manejo de cambios en los inputs
    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "gestor.dni") {
            setFormData({ ...formData, gestor: { ...formData.gestor, dni: value } });
        } else if (name === "cliente.dni") {
            setFormData({ ...formData, cliente: { ...formData.cliente, dni: value } });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage("");

        const parseDateTimeLocal = (value) => new Date(value + ":00");

        // Validaciones
        const currentDate = new Date();
        const fechainicio = parseDateTimeLocal(formData.fechainicio);
        const fechafinal = parseDateTimeLocal(formData.fechafinal);

        console.log('fechainicio:', formData.fechainicio);
        console.log('fechafinal:', formData.fechafinal);


        // Validar que fechainicio sea mayor que la fecha actual
        if (fechainicio <= currentDate) {
            setError("La fecha de inicio debe ser mayor que la fecha actual.");
            return;
        }

        // Validar que fechafinal sea mayor que fechainicio
        if (fechafinal <= fechainicio) {
            setError("La fecha final debe ser mayor que la fecha de inicio.");
            return;
        }

        try {
            const googleToken = localStorage.getItem('googleToken'); // 🟡 Captura del token de Google

            const payload = {
                ...formData,
                fechainicio: fechainicio.toISOString(),
                fechafin: fechafinal.toISOString(),
                googleToken: googleToken // 🟢 Añadimos el token dentro del JSON que enviamos
            };

            const response = await fetch("http://localhost:8080/api/accesos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                setSuccessMessage("Acceso añadido.");
                setFormData({
                    clave: "",
                    fechainicio: "",
                    fechafinal: "",
                    direccion: "",
                    cerradura: "",
                    gestor: { dni: user?.dni || "" },
                    cliente: { dni: "" }
                });
            } else {
                setError("Error en el registro del acceso.");
            }

        } catch (error) {
            console.error('Error al registrar el acceso:', error);
            alert('Hubo un error al registrar el acceso');
        }
    };

    // Función que creará un evento en el Google Calendar del usuario
    const createCalendarEvent = async () => {

        // 1️⃣ Obtener el access_token guardado en el navegador (localStorage) después del login con Google
        const token = localStorage.getItem('googleToken');

        // 2️⃣ Verificar que tenemos el token disponible
        if (!token) {
            console.error("No se encontró el token de Google en el navegador");
            alert("❌ Primero inicia sesión con Google para poder crear eventos.");
            return;
        }

        try {
            // 3️⃣ Realizar una solicitud POST a la API de Google Calendar
            const response = await fetch('https://www.googleapis.com/calendar/v3/calendars/primary/events', {
                method: 'POST', // método HTTP POST para crear
                headers: {
                    'Authorization': `Bearer ${token}`, // usamos el token de autenticación
                    'Content-Type': 'application/json', // el cuerpo va en JSON
                },
                body: JSON.stringify({
                    // 4️⃣ Definir el contenido del evento que vamos a crear
                    summary: `🔑 Acceso: ${formData.direccion}`,
                    description: `Cerradura: ${formData.cerradura}\nDNI Cliente: ${formData.cliente.dni}`,
                    start: {
                        dateTime: new Date(formData.fechainicio).toISOString(), // fecha/hora inicio
                        timeZone: 'Europe/Madrid',       // zona horaria
                    },
                    end: {
                        dateTime: new Date(formData.fechafinal).toISOString(),  // fecha/hora fin
                        timeZone: 'Europe/Madrid',
                    },
                }),
            });

            // 5️⃣ Convertimos la respuesta a JSON para ver qué nos devuelve Google
            const data = await response.json();

            if (response.status === 200 || response.status === 201) {
                console.log("✅ Evento creado:", data);
                alert('✅ Evento creado correctamente en tu Google Calendar');
            } else {
                console.error('❌ Error creando evento:', data);
                alert(`❌ Error al crear el evento: ${data.error?.message || 'Error desconocido'}`);
            }

        } catch (error) {
            // 6️⃣ Captura de errores de red o de la API
            console.error('❌ Error conectando a Google Calendar:', error);
            alert('❌ Error al comunicar con Google Calendar.');
        }
    };

    return (
        <div className="add-access-container">
            <div className="background-overlay">
                <div className="add-access-content">
                    <h2>Registrar Acceso</h2>
                    <GoogleLoginButton />

                    {/* Muestra los mensajes de error o éxito */}
                    {error && <p className="error-message">{error}</p>}
                    {successMessage && <p className="success-message">{successMessage}</p>}


                    <form onSubmit={handleSubmit}>
                        <label>Clave</label>
                        <input type="text" name="clave" value={formData.clave} onChange={handleChange} required />

                        <label>Fecha Inicio:</label>
                        <input type="datetime-local" name="fechainicio" value={formData.fechainicio} onChange={handleChange} required />

                        <label>Fecha Fin:</label>
                        <input type="datetime-local" name="fechafinal" value={formData.fechafinal} onChange={handleChange} required />

                        <label>Dirección</label>
                        <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} required />

                        <label>ID Cerradura</label>
                        <input type="text" name="cerradura" value={formData.cerradura} onChange={handleChange} required />

                        <label>DNI del Cliente</label>
                        <input type="text" name="cliente.dni" value={formData.cliente.dni} onChange={handleChange} required />

                        <button onClick={createCalendarEvent} className="submit-button">
                            📅 Registrar acceso en Google Calendar
                        </button>
                        {/* <br />
                        <button type="submit" className="submit-button">Registrar Acceso</button> */}
                    </form>
                    <button className="back-button" onClick={() => navigate(-1)}>
                        ⬅ Volver
                    </button>
                                </div>
            </div>
        </div>
    );
};

export default AddAccess;