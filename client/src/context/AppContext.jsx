import { createContext, useState } from "react";
import { AppConstants } from "../util/constants";

// Create the context
export const AppContext = createContext();

// Provider component
export const AppContextProvider = ({ children }) => {
  const backendUrl = AppConstants.BACKEND_URL; // make sure constant key is BACKEND_URL

  const [isLoggedIn, setIsLogged] = useState(false);
  const [userData, setUserData] = useState(null);

  const contextValue = {
    backendUrl,
    isLoggedIn,
    setIsLogged,
    userData,
    setUserData,
  };

  return (
    <AppContext.Provider value={contextValue}>
      {children}
    </AppContext.Provider>
  );
};
