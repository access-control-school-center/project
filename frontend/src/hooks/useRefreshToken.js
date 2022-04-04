import axios from "../api/axios"
import useAuth from "./useAuth"

const useRefreshToken = () => {
  const { auth, setAuth } = useAuth()
  return async () => {
    const data = { token: auth.refreshToken }
    const config = { withCredentials: true }


    const response = await axios.post('/token', data, config)

    setAuth(prev => {
      return { ...prev, accessToken: response.data.access_token }
    })

    return response.data.access_token
  }
}

export default useRefreshToken