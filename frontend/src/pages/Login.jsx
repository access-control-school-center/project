import { useEffect, useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"

import axios from '../api/axios'
import FormErrorMessage from "../components/FormErrorMessage"
import useAuth from '../hooks/useAuth'

const Login = () => {
  const { setAuth } = useAuth()

  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || "/"

  const [nusp, setNusp] = useState('')
  const [password, setPassword] = useState('')
  const [disabled, setDisabled] = useState(true)
  const [errMsg, setErrMsg] = useState('')

  const [isSubmitting, setIsSubmitting] = useState(false)

  useEffect(() => {
    const emptyForm = nusp.length === 0 || password.length === 0
    setDisabled(isSubmitting || emptyForm)
  }, [nusp, password, isSubmitting])

  const handleLogin = async () => {
    if (disabled) return

    try {
      setIsSubmitting(true)
      const response = await axios.post('/login', {
        nusp,
        password
      })
      const { access_token: accessToken, refresh_token: refreshToken } = response?.data

      setAuth({ accessToken, refreshToken })

      setNusp('')
      setPassword('')
      setIsSubmitting(false)
      navigate(from, { replace: true })
    } catch (err) {
      setIsSubmitting(false)
      if (!err?.response) {
        setErrMsg('Sem resposta do servidor, tente novamente mais tarde');
      } else if (err.response?.status === 400) {
        setErrMsg('Nº USP ou senha incorretos')
      } else {
        setErrMsg('Falha desconhecida no login');
      }
    }
  }

  return (
    <section className="card">

      <h2>Login</h2>

      <FormErrorMessage message={errMsg} />

      <input
        type="text"
        value={nusp}
        onChange={(e) => setNusp(e.target.value)}
        placeholder="Nº USP"
        autoComplete="off"
        required
      />

      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Senha"
        required
        className="last"
      />

      <button
        className={disabled ? "disabled" : ""}
        onClick={handleLogin}
      >
        Login
      </button>
    </section>
  )
}

export default Login