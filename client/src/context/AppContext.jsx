import { createContext, useEffect, useState } from "react";
import { AppConstants } from "../util/constants";
import axios from "axios";
import { toast } from "react-toastify"; // ✅ fix

export const AppContext = createContext();

export const AppContextProvider = ({ children }) => {
  axios.defaults.withCredentials =true;
  const backendUrl = AppConstants.BACKEND_URL;

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userData, setUserData] = useState(null);

  const getUserData = async () => {
    try {
      const response = await axios.get(backendUrl + "/profile");

      if (response.status === 200) {
        setUserData(response.data);
      } else {
        toast.error("Unable to retrieve profile");
      }
    } catch (error) {
      toast.error(error.response?.data?.message || error.message);
    }
  };

  const getAuthState = async()=>{
    try{
      const response = await axios.get(backendUrl+"/is-authenticated");
      if(response.status ===200 && response.data===true){
        setIsLoggedIn(true);
        await getUserData();
      }else{
        setIsLoggedIn(false);
      }
    }catch(error){

      console.error(error);
    }
  }


  useEffect(() =>{
    getAuthState();
  },[])

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
