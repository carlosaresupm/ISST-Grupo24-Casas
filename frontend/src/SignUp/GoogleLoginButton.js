import React from 'react';
import { GoogleOAuthProvider, useGoogleLogin } from '@react-oauth/google';

function GoogleLoginButton() {
  const login = useGoogleLogin({
    scope: 'https://www.googleapis.com/auth/calendar.events',
    onSuccess: async (tokenResponse) => {
      console.log("✅ Login Exitoso:", tokenResponse);

      // Guardar access_token real en localStorage
      localStorage.setItem('googleToken', tokenResponse.access_token);
    },
    onError: (error) => {
      console.log('❌ Error en login con Google:', error);
    }
  });

  return (
    <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID}>
      <button onClick={() => login()}>
        Iniciar sesión con Google
      </button>
    </GoogleOAuthProvider>
  );
}

export default GoogleLoginButton;