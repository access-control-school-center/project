import { Navigate, Outlet, useLocation } from 'react-router-dom'

import usePerson from '../hooks/usePerson'

const PersonRequired = () => {
  const { person } = usePerson()
  const location = useLocation()

  return (
    person.id
      ? <Outlet />
      : <Navigate to="/" replace />
  )
}

export default PersonRequired