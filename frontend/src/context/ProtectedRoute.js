import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./AuthContext";

const ProtectedRoute = ({ allowedRoles = [] }) => {
  const { user, loading } = useAuth();

  if (loading) return null; // Opcional: un spinner

  // Validar si el usuario y el rol están definidos antes de usar `includes`
  if (!user || !user.role) {
    return <Navigate to="/login" replace />;
  }

  return allowedRoles.includes(user.role) ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;