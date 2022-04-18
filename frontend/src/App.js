import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './App.css';

import { PersonProvider } from './contexts/Person'

import Layout from './components/Layout';
import AuthRequired from './components/AuthRequired';
import PersonRequired from './components/PersonRequired';

import Login from './pages/Login'
import Home from './pages/Home'
import Register from './pages/Register';
import Search from './pages/Search';
import User from './pages/User';
import NotFound from './pages/NotFound';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />} >

          <Route path="/login" element={<Login />} />

          <Route element={<AuthRequired />}>
            <Route path="/" element={<Home />} />

            <Route element={<PersonProvider />}>
              <Route path="/cadastro" element={<Register />} />
              <Route path="/busca" element={<Search />} />

              <Route element={<PersonRequired />} >
                <Route path="/perfil" element={<User />} />
              </Route>

            </Route>

            <Route path="*" element={<NotFound />} />

          </Route>

        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
