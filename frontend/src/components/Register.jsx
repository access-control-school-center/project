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
    <section className="card">
      <h2>Cadastro</h2>

      <input
        type="text"
        placeholder="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        type="text"
        placeholder="Perfil"
        value={role}
        onChange={(e) => setRole(e.target.value)}
      />

      <input
        type="text"
        placeholder="Tipo de Documento"
        value={docType}
        onChange={(e) => setDocType(e.target.value)}
      />

      <input
        type="text"
        placeholder="Número de Documento"
        className="last"
        value={doc}
        onChange={(e) => setDoc(e.target.value)}
      />

      <button onClick={handleRegistration}>
        Cadastrar
      </button>
    </section>
  )
}

export default Register