import { Outlet } from 'react-router-dom'

const Layout = () => {
  return (
    <main className="page">
      <h1>CEIP - Acesso</h1>

      <Outlet />

    </main>
  )
}

export default Layout