import React from "react";
import "./HomeUser.css"; // Archivo CSS para los estilos
import { useNavigate } from "react-router-dom";
// import GoogleLoginButton from "../SignUp/GoogleLoginButton";

const HomeUser = ({ nombre }) => {
  const navigate = useNavigate();

  const handleViewReservations = () => {
    navigate("/reservas"); // Redirige a la pantalla de reservas
  };

  // // Función que creará un evento en el Google Calendar del usuario
  // const createCalendarEvent = async () => {

  //   // 1️⃣ Obtener el access_token guardado en el navegador (localStorage) después del login con Google
  //   const token = localStorage.getItem('googleToken');

  //   // 2️⃣ Verificar que tenemos el token disponible
  //   if (!token) {
  //     console.error("No se encontró el token de Google en el navegador");
  //     alert("❌ Primero inicia sesión con Google para poder crear eventos.");
  //     return;
  //   }

  //   try {
  //     // 3️⃣ Realizar una solicitud POST a la API de Google Calendar
  //     const response = await fetch('https://www.googleapis.com/calendar/v3/calendars/primary/events', {
  //       method: 'POST', // método HTTP POST para crear
  //       headers: {
  //         'Authorization': `Bearer ${token}`, // usamos el token de autenticación
  //         'Content-Type': 'application/json', // el cuerpo va en JSON
  //       },
  //       body: JSON.stringify({
  //         // 4️⃣ Definir el contenido del evento que vamos a crear
  //         summary: '🔑 Acceso Casa Reservado',
  //         description: 'Acceso generado automáticamente por SmartAccess',
  //         start: {
  //           dateTime: '2025-04-10T10:00:00', // fecha/hora inicio
  //           timeZone: 'Europe/Madrid',       // zona horaria
  //         },
  //         end: {
  //           dateTime: '2025-04-10T11:00:00',  // fecha/hora fin
  //           timeZone: 'Europe/Madrid',
  //         },
  //       }),
  //     });

  //     // 5️⃣ Convertimos la respuesta a JSON para ver qué nos devuelve Google
  //     const data = await response.json();

  //     if (response.status === 200 || response.status === 201) {
  //       console.log("✅ Evento creado:", data);
  //       alert('✅ Evento creado correctamente en tu Google Calendar');
  //     } else {
  //       console.error('❌ Error creando evento:', data);
  //       alert(`❌ Error al crear el evento: ${data.error?.message || 'Error desconocido'}`);
  //     }

  //   } catch (error) {
  //     // 6️⃣ Captura de errores de red o de la API
  //     console.error('❌ Error conectando a Google Calendar:', error);
  //     alert('❌ Error al comunicar con Google Calendar.');
  //   }
  // };

  return (
    <div className="home-user-container">
      <div className="overlay">
        <div className="welcome-box">
        {/* <GoogleLoginButton />
        <br />
        <br />
        <button onClick={createCalendarEvent}>
              📅 Crear Evento de Prueba en Google Calendar
        </button> */}
          <p>
            Buenas <strong>{nombre}</strong>, ¿quieres acceder a <strong>tus reservas</strong>?
          </p>
          <a href="/HomeUser/ClientAccess">
            <button className="back-button" onclick={() => navigate(`/HomeUser/ClientAccess`)}>
              🔑 Ver accesos
            </button>
          </a>
        </div>
      </div>
    </div>
  );
};

export default HomeUser;

