import { useEffect, useState } from "react"
import DatePicker from 'react-datepicker'
import { useLocation, useNavigate } from "react-router-dom"

import useAxiosPrivate from "../hooks/useAxiosPrivate"
import usePerson from "../hooks/usePerson"

function formatDate(date) {
  return date.toLocaleDateString().replaceAll("/", "-")
}

function translateError(err) {
  if (/document.+already in use/.test(err))
    return "Documento já está cadastrado"

  return "Os dados fornecidos são inválidos"
}

const Register = () => {
  const axios = useAxiosPrivate()

  const { setPerson } = usePerson()
  const location = useLocation()
  const navigate = useNavigate()

  const docTypes = ["RG", "CPF"]

  const [name, setName] = useState('')
  const [docType, setDocType] = useState('RG')
  const [doc, setDoc] = useState('')
  const [shotDate, setShotDate] = useState(new Date())

  const [errMsg, setErrMsg] = useState('')
  const [hasError, setHasError] = useState(false)

  const [disabled, setDisabled] = useState(true)
  const [isSubmitting, setIsSubmitting] = useState(false)

  useEffect(() => {
    setHasError(errMsg.length > 0)
  }, [errMsg])

  useEffect(() => {
    const emptyForm = name.length === 0 && doc.length === 0
    setDisabled(isSubmitting || emptyForm)
  }, [name, doc, isSubmitting])

  const handleRegistration = async () => {
    if (disabled) return

    const body = {
      name,
      documentType: docType,
      documentValue: doc,
      shotDate: formatDate(shotDate),
    }

    try {
      setIsSubmitting(true)
      await axios.post("/register", JSON.stringify(body))
      setPerson({
        id: 1,                    // TODO: hardcoded - remove
        ...body,
        role: "UserOrCompanion",  // TODO: hardcoded - remove
        services: ["APOIAR"]      // TODO: hardcoded - remove
      })
      setIsSubmitting(false)
      navigate("/perfil", { from: location })
    } catch (error) {
      setIsSubmitting(false)
      if (error.response) {
        switch (error.response.status / 100) {
          case 4:
            const err = translateError(error.response.data.error)
            setErrMsg(err)
            break
          case 5:
            setErrMsg("O servidor está indisponível, tente novamente mais tarde.")
            break
          default:
            setErrMsg("Algo inesperado aconteceu. Tente novamente mais tarde.")
        }
      } else if (error.request) {
        setErrMsg("O servidor está indisponível, tente novamente mais tarde.")
      } else {
        setErrMsg("Algo inesperado aconteceu. Tente novamente mais tarde.")
      }
    }
  }

  return (
    <section className="card -mt-10">
      <h2>Cadastro</h2>

      {
        hasError &&
        <p className="error">{errMsg}</p>
      }

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

      <button
        className={disabled ? "disabled" : ""}
        onClick={handleRegistration}
      >
        Cadastrar
      </button>
    </section>
  )
}

export default Register