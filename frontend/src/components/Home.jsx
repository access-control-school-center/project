import { useState } from 'react'
import { Link } from 'react-router-dom'
import useAxiosPrivate from '../hooks/useAxiosPrivate'

const Home = () => {
  const axios = useAxiosPrivate()

  const [msg, setMsg] = useState('')

  const makeRequest = async () => {
    const response = await axios.get("/foo")
    setMsg(response?.data?.msg)
  }

  return (
    <section className='card'>
      <h2>Menu</h2>

      <button onClick={makeRequest}>
        Get message
      </button>

      <p>{msg}</p>

      <p>
        <Link to="/register">
          Cadastre alguém
        </Link>
      </p>

      <p>
        <Link to="/user">
          Encontre alguém
        </Link>
      </p>
    </section>
  )
}

export default Home