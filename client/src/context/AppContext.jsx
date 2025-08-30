
// //  ✅ Fetch user profile
//   const getUserData = async () => {
//     try {
//       const response = await axios.get(`${backendUrl}/profile`, {
//         headers: { "Content-Type": "application/json" }
//       });

//       if (response.status === 200) {
//         setUserData(response.data);
//       } else {
//         toast.error("Unable to retrieve profile");
//       }
//     } catch (error) {
//       // Better error handling
//       toast.error(error.response?.data?.message || error.message);
//     }
//   };


import { createContext, useState } from "react";
import { AppConstants } from "../util/constants";

// Create the context
export const AppContext = createContext();

// Provider component
export const AppContextProvider = ({ children }) => {
  const backendUrl = AppConstants.BACKEND_URL; // make sure constant key is BACKEND_URL
  

  const [isLoggedIn, setIsLogged] = useState(false);
  const [userData, setUserData] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || null);

    // Fetch user profile
  const getUserData = async () => {
    try {
      const response = await axios.get(`${backendUrl}/profile`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: token ? `Bearer ${token}` : "",
        },
        // withCredentials: true  // <-- if backend uses cookies instead of JWT
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

  // Save token + mark logged in
  const loginUser = (jwtToken) => {
    setToken(jwtToken);
    localStorage.setItem("token", jwtToken);
    setIsLoggedIn(true);
  };

  // Logout
  const logoutUser = () => {
    setToken(null);
    localStorage.removeItem("token");
    setUserData(null);
    setIsLoggedIn(false);
  };

  const contextValue = {
    backendUrl,
    isLoggedIn,
    setIsLogged,
    userData,
    setUserData,
    getUserData,
    loginUser,
    logoutUser
  };

  return (
    <AppContext.Provider value={contextValue}>
      {children}
    </AppContext.Provider>
  );
};
