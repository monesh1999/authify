import { createContext, useState } from "react";
import { AppConstants } from "../util/constants";
import axios from "axios";
import { toast } from "react-toastify"; // ✅ fix

export const AppContext = createContext();

export const AppContextProvider = ({ children }) => {
  const backendUrl = AppConstants.BACKEND_URL;

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState(null);

  const getUserData = async () => {
    try {
      const response = await axios.get(backendUrl + "/profile", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`, // ✅ fix
        },
      });

      if (response.status === 200) {
        setUserData(response.data);
      } else {
        toast.error("Unable to retrieve profile");
      }
    } catch (error) {
      toast.error(error.response?.data?.message || error.message);
    }
  };

  const contextValue = {
    backendUrl,
    isLoggedIn,
    setIsLoggedIn,
    userData,
    setUserData,
    getUserData,
  };

  return (
    <AppContext.Provider value={contextValue}>
      {children}
    </AppContext.Provider>
  );
};
