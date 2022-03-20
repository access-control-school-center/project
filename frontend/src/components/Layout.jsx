import { Outlet } from 'react-router-dom'

import Navbar from './Navbar'

const Layout = () => {
  return (
    <main className="page">
      <Navbar />

      <Outlet />

    </main>
  )
}

export default Layout