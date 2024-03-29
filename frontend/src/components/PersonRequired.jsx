import { Navigate, Outlet } from 'react-router-dom'

import usePerson from '../hooks/usePerson'

const PersonRequired = () => {
  const { person } = usePerson()

  return (
    person.id?.length > 0
      ? <Outlet />
      : <Navigate to="/" replace />
  )
}

export default PersonRequired