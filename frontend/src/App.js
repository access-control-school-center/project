import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './App.css';
import Login from './components/Login'
import Home from './components/Home'
import Register from './components/Register';
import User from './components/User';
import AuthRequired from './components/AuthRequired';
import Layout from './components/Layout';
import Search from './components/Search';
import { PersonProvider } from './contexts/Person'
import PersonRequired from './components/PersonRequired';
import NotFound from './components/NotFound';

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
