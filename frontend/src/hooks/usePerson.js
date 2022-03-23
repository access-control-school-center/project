import { useContext } from "react"
import PersonContext from "../contexts/Person"

const usePerson = () => {
  return useContext(PersonContext)
}

export default usePerson