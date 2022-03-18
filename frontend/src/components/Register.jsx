import { useState } from "react"
import useAxiosPrivate from "../hooks/useAxiosPrivate"

const Register = () => {
  const axios = useAxiosPrivate()

  const [name, setName] = useState('')
  const [role, setRole] = useState('')
  const [docType, setDocType] = useState('')
  const [doc, setDoc] = useState('')

  const handleRegistration = async () => {
    const body = {
      name,
      document: {
        type: docType,
        value: doc,
      },
      role,
      lastShotDate: { day: '11', month: '11', year: '2021' },
      credentials: {
        username: name,
        password: doc,
      }
    }

    const response = await axios.post("/users", JSON.stringify(body))

    console.log(response)
  }

  return (
    <>
      <input
        type="text"
        placeholder="Nome"
        className="m-4 px-2 py-1 bg-gray-100 rounded border border-gray-500"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        type="text"
        placeholder="Perfil"
        className="m-4 px-2 py-1 bg-gray-100 rounded border border-gray-500"
        value={role}
        onChange={(e) => setRole(e.target.value)}
      />

      <input
        type="text"
        placeholder="Tipo de Documento"
        className="m-4 px-2 py-1 bg-gray-100 rounded border border-gray-500"
        value={docType}
        onChange={(e) => setDocType(e.target.value)}
      />

      <input
        type="text"
        placeholder="Número de Documento"
        className="m-4 px-2 py-1 bg-gray-100 rounded border border-gray-500"
        value={doc}
        onChange={(e) => setDoc(e.target.value)}
      />

      <button
        onClick={handleRegistration}
        className="rounded bg-green-600 px-4 py-1 text-white font-bold"
      >
        Cadastrar
      </button>
    </>
  )
}

export default Register