import { useLocation, useNavigate, Link } from 'react-router-dom'

import useAuth from '../hooks/useAuth'
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import If from './If'

const Navbar = () => {
  const location = useLocation()
  const navigate = useNavigate()

  const axios = useAxiosPrivate()
  const { auth, setAuth } = useAuth()
  const isLogged = !!auth?.accessToken

  const logout = async () => {
    try {
      await axios.post('/logout', {
        token: auth?.refreshToken
      })

      setAuth({
        accessToken: undefined,
        refreshToken: undefined
      })
      navigate("/login", { from: location, replace: true })
    } catch (err) {
      console.log(err)
    }
  }

  return (
    <nav>

      <h2>CEIP - Acesso</h2>

      <If condition={isLogged}>
        <ul>
          <If condition={location.pathname !== "/"}>
            <li>
              <Link to="/">Início</Link>
            </li>
          </If>

          <If condition={location.pathname !== "/cadastro" && location.pathname !== "/"}>
            <li>
              <Link to="/cadastro">Cadastro</Link>
            </li>
          </If>

          <If condition={location.pathname !== "/busca" && location.pathname !== "/"}>
            <li>
              <Link to="/busca">Busca</Link>
            </li>
          </If>

          <li onClick={logout}>Sair</li>
        </ul>
      </If>

    </nav>
  )
}

export default Navbar