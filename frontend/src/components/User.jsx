import { useState } from "react"
import useAxiosPrivate from "../hooks/useAxiosPrivate"

const User = () => {
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

  const [uuid, setUuid] = useState('')

  const [errMsg, setErrMsg] = useState('')

  const fetchUser = async () => {
    try {
      const response = await axios.get(`/users/${uuid}`)

      if (!response?.data?.user) {
        setErrMsg(`User ${uuid} not found`)
        return
      }

      const user = response.data.user

      setUser({
        id: uuid,
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

  return (
    <>
      <input
        type="text"
        value={uuid}
        onChange={(e) => setUuid(e.target.value)}
        placeholder="ID do usuário"
        className="m-4 px-2 py-1 bg-gray-100 rounded border border-gray-500"
      />

      <button
        className="px-4 py-1 rounded bg-green-600 text-white font-bold"
        onClick={fetchUser}
      >
        Buscar
      </button>

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