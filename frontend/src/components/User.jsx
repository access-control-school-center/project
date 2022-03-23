import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

import useAxiosPrivate from "../hooks/useAxiosPrivate"
import PersonInfo from "./PersonInfo"

const User = () => {
  const { id } = useParams()

  const axios = useAxiosPrivate()

  const [user, setUser] = useState({
    id: '',
    name: '',
    document: {},
    role: '',
    lastShotDate: {},
  })

  const [errMsg, setErrMsg] = useState('')

  const fetchUser = async () => {
    try {
      const response = await axios.get(`/users/${id}`)
      setUser({ id, ...response.data })
    } catch (err) {
      setErrMsg('User not found or system not available')
    }
  }

  useEffect(() => fetchUser(), [])

  return (
    <section className="card">
      {
        errMsg
          ? (<p>{errMsg}</p>)
          : (
            <PersonInfo
              id={user.id}
              name={user.name}
              doc={user.document}
              lastShotDate={user.lastShotDate}
              role={user.role}
              credential={user.credential}
              responsible={user.responsible}
              services={user.services}
            />
          )
      }
    </section>
  )
}

export default User