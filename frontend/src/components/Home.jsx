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
    <>
      <h1>Home</h1>

      <button
        onClick={makeRequest}
        className="px-4 py-1 rounded bg-green-600 text-white font-bold"
      >
        Get message
      </button>

      <p>{msg}</p>

      <p>
        <Link
          to="/register"
          className="text-blue-600 underline"
        >
          Cadastre alguém
        </Link>
      </p>

      <p>
        <Link
          to="/user"
          className="text-blue-600 underline"
        >
          Encontre alguém
        </Link>
      </p>
    </>
  )
}

export default Home