import { useEffect, useState } from "react"
import DatePicker from 'react-datepicker'
import { useLocation, useNavigate } from "react-router-dom"

import { RG, UNDOC, DOC_TYPES } from '../utils/documents'
import { ROLES, ROLES_LIST, ROLES_REPRESENTATION } from '../utils/roles'
import useAxiosPrivate from "../hooks/useAxiosPrivate"
import usePerson from "../hooks/usePerson"
import FormErrorMessage from "../components/FormErrorMessage"
import If from "../components/If"

function formatDate(date) {
  return date.toLocaleDateString().replaceAll("/", "-")
}

function translateError(err) {
  if (/document.+already in use/.test(err))
    return "Documento já está cadastrado"

  if (/invalid document value/.test(err))
    return "Número de Documento inválido"

  if (/invalid date/.test(err))
    return "Data de vacinação inválida"

  if (/malformed password/.test(err))
    return "Senha entre 8 e 20 caractéres entre letras (A-Z, a-z), números e caractéres especiais ! @ # $ % & - _"

  if (/nusp.+already in use/.test(err))
    return "Nº USP já está cadastrado"

  return "Os dados fornecidos são inválidos"
}

const Register = () => {
  const axios = useAxiosPrivate()

  const { setPerson } = usePerson()
  const location = useLocation()
  const navigate = useNavigate()

  const [role, setRole] = useState(ROLES.USER)
  const [name, setName] = useState('')
  const [docType, setDocType] = useState(RG)
  const [doc, setDoc] = useState('')
  const [shotDate, setShotDate] = useState(new Date())
  const [nusp, setNusp] = useState('')
  const [password, setPassword] = useState('')

  const [errMsg, setErrMsg] = useState('')

  const [disabled, setDisabled] = useState(true)
  const [isSubmitting, setIsSubmitting] = useState(false)

  useEffect(() => {
    let emptyForm = true

    if (role === ROLES.USER) {
      emptyForm = name.length === 0 || doc.length === 0
    } else if (role === ROLES.EMPLOYEE) {
      emptyForm = name.length === 0 || doc.length === 0 || nusp.length === 0 || password.length === 0
    }

    setDisabled(isSubmitting || emptyForm)
  }, [role, name, doc, nusp, password, isSubmitting])

  const getUri = () => {
    if (role === ROLES.EMPLOYEE) return "/employees"
    return "/users"
  }

  const buildBody = () => {
    const base = {
      name,
      documentType: docType,
      documentValue: docType === UNDOC ? '' : doc,
      shotDate: formatDate(shotDate),
    }

    if (role === ROLES.EMPLOYEE) {
      base.credential = {
        nusp, password
      }
    }

    return base
  }

  const handleRegistration = async () => {
    if (disabled) return

    const body = buildBody()

    try {
      setIsSubmitting(true)
      const uri = getUri()
      const { data: { user } } = await axios.post(uri, JSON.stringify(body))
      setPerson({
        ...user,
        role: ROLES_REPRESENTATION[role]
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
    <section className="card -mt-10 max-h-3/4 overflow-hidden overflow-y-auto">
      <h2>Cadastro</h2>

      <FormErrorMessage message={errMsg} />

      <select
        value={role}
        onChange={(e) => setRole(e.target.value)}
      >
        {ROLES_LIST.map((type) => (
          <option key={type} value={type}>{type}</option>
        ))}
      </select>

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
        {DOC_TYPES.map((type) => (
          <option key={type} value={type}>{type}</option>
        ))}
      </select>

      <If condition={docType !== UNDOC}>
        <input
          type="text"
          placeholder={"Número do " + (docType.length === 0 ? "Documento" : docType)}
          value={doc}
          onChange={(e) => setDoc(e.target.value)}
        />
      </If>


      <DatePicker
        title="Data da última dose"
        selected={shotDate}
        onChange={(date) => setShotDate(date)}
        className="my-6 w-full"
      />

      <If condition={role === ROLES.EMPLOYEE}>
        <h3>Credenciais de Acesso</h3>

        <p className="text-sm bg-green-300 px-2 py-1">
          Permita o(a) funcionário(a) digitar suas próprias credenciais abaixo
        </p>

        <input
          type="text"
          placeholder="Nº USP"
          value={nusp}
          onChange={(e) => setNusp(e.target.value)}
        />

        <input
          type="password"
          placeholder="senha"
          className="mb-8"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </If>

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