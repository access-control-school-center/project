import { useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"

import axios from '../api/axios'
import useAuth from '../hooks/useAuth'

const Login = () => {
  const { setAuth } = useAuth()

  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || "/"

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const handleLogin = async () => {
    try {
      const response = await axios.post('/login', {
        username,
        password
      })
      const { access_token: accessToken, refresh_token: refreshToken } = response?.data

      setAuth({ accessToken, refreshToken })

      setUsername('')
      setPassword('')
      navigate(from, { replace: true })
    } catch (err) {
      if (!err?.response) {
        console.log('No Server Response');
      } else if (err.response?.status === 400) {
        console.log('Missing Username or Password');
      } else if (err.response?.status === 401) {
        console.log('Unauthorized');
      } else {
        console.log('Login Failed');
      }
    }
  }

  return (
    <section className="card">

      <h2>Login</h2>

      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Username"
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

      <button onClick={handleLogin}>
        Login
      </button>
    </section>
  )
}

export default Login