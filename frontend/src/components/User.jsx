import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

import useAxiosPrivate from "../hooks/useAxiosPrivate"

const User = () => {
  const { id } = useParams()

  const parseDate = ({ day, month, year }) => {
    return `${day}/${month}/${year}`
  }

  const axios = useAxiosPrivate()

  const [user, setUser] = useState({
    id: '',
    name: '',
    docType: '',
    doc: '',
    role: '',
    lastShotDate: '',
    username: '',
  })

  const [errMsg, setErrMsg] = useState('')

  const fetchUser = async () => {
    try {
      const response = await axios.get(`/users/${id}`)

      if (!response?.data?.user) {
        setErrMsg(`User ${id} not found`)
        return
      }

      const user = response.data.user

      setUser({
        id,
        name: user.name,
        docType: user.document.type,
        doc: user.document.value,
        role: user.role,
        lastShotDate: parseDate(user.lastShotDate),
        username: user.credentials.username,
      })
    } catch (err) {
      setErrMsg('Unauthorized')
    }
  }

  useEffect(() => fetchUser(), [])

  return (
    <>
      {user.id && Object.keys(user).map((attr) => {
        return (<p key={attr}>
          <span className="font-bold">{attr}</span> - {user[attr]}
        </p>)
      })}

      {errMsg && <p>{errMsg}</p>}
    </>
  )
}

export default User