import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './App.css';
import Login from './components/Login'
import Home from './components/Home'
import Register from './components/Register';
import User from './components/User';
import AuthRequired from './components/AuthRequired';
import Layout from './components/Layout';
import Search from './components/Search';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />} >

          <Route path="/login" element={<Login />} />

          <Route element={<AuthRequired />}>
            <Route path="/" element={<Home />} />
            <Route path="/register" element={<Register />} />
            <Route path="/user" element={<User />} />
            <Route path="/search" element={<Search />} />
          </Route>

        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
