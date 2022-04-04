import { useLocation, useNavigate, Link } from 'react-router-dom'

import useAuth from '../hooks/useAuth'
import useAxiosPrivate from '../hooks/useAxiosPrivate'

const Navbar = () => {
  const location = useLocation()
  const navigate = useNavigate()

  const axios = useAxiosPrivate()
  const { auth, setAuth } = useAuth()
  const isLogged = !!auth?.accessToken

  const logout = async () => {
    try {
      await axios.put('/logout', {
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

      {isLogged &&
        <ul>
          {location.pathname !== "/" &&
            <li>
              <Link to="/">Início</Link>
            </li>
          }

          {location.pathname !== "/register" && location.pathname !== "/" &&
            <li>
              <Link to="/register">Cadastro</Link>
            </li>
          }

          {location.pathname !== "/search" && location.pathname !== "/" &&
            <li>
              <Link to="/search">Busca</Link>
            </li>
          }

          <li onClick={logout}>Sair</li>
        </ul>
      }

    </nav>
  )
}

export default Navbar