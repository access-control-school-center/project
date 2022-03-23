import { createContext, useState } from "react";
import { Outlet } from "react-router-dom";

const PersonContext = createContext()

export const PersonProvider = () => {
  const [person, setPerson] = useState({
    id: undefined,
    name: undefined,
    document: undefined,
    lastShotDate: undefined,
    role: undefined,
    services: undefined,
    credential: undefined,
    responsible: undefined,
  })

  return (
    <PersonContext.Provider value={{ person, setPerson }}>
      <Outlet />
    </PersonContext.Provider>
  )
}

export default PersonContext