import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './App.css';
import Login from './components/Login'
import Home from './components/Home'
import Register from './components/Register';
import User from './components/User';
import AuthRequired from './components/AuthRequired';

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/login" element={<Login />} />

        <Route element={<AuthRequired />}>
          <Route path="/" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/user" element={<User />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;
