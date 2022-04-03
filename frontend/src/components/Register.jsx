import { useState } from "react"
import DatePicker from 'react-datepicker'

import useAxiosPrivate from "../hooks/useAxiosPrivate"

function formatDate(date) {
  return date.toLocaleDateString().replaceAll("/", "-")
}

const Register = () => {
  const axios = useAxiosPrivate()

  const docTypes = ["RG", "CPF"]

  const [name, setName] = useState('')
  const [docType, setDocType] = useState('RG')
  const [doc, setDoc] = useState('')
  const [shotDate, setShotDate] = useState(new Date())

  const handleRegistration = async () => {
    const body = {
      name,
      documentType: docType,
      documentValue: doc,
      shotDate: formatDate(shotDate),
    }

    const response = await axios.post("/register", JSON.stringify(body))

    console.log(response)
  }

  return (
    <section className="card -mt-10">
      <h2>Cadastro</h2>

      <input
        type="text"
        placeholder="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <select
        value={docType}
        onChange={(e) => setDocType(e.target.value)}
      >
        {docTypes.map((type) => (
          <option key={type} value={type}>{type}</option>
        ))}
      </select>

      <input
        type="text"
        placeholder={"Número do " + (docType.length === 0 ? "Documento" : docType)}
        value={doc}
        onChange={(e) => setDoc(e.target.value)}
      />

      <DatePicker
        title="Data da última dose"
        selected={shotDate}
        onChange={(date) => setShotDate(date)}
        className="my-6 w-full"
      />

      <button onClick={handleRegistration}>
        Cadastrar
      </button>
    </section>
  )
}

export default Register