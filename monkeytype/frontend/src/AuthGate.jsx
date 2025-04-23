// src/AuthGate.jsx
import React, { useState } from 'react';
import App from './App.jsx';
import Login from './Login.jsx';
import CreateAccount from './CreateAccount.jsx';

function getCookie(name) {
  const m = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  return m ? m[2] : null;
}

export default function AuthGate() {
  const user = getCookie('user');
  const [mode, setMode] = useState('login'); // or 'signup'

  if (user) {
    return <App />;
  }

  return mode === 'login' ? (
    <Login onSwitch={() => setMode('signup')} />
  ) : (
    <CreateAccount onSwitch={() => setMode('login')} />
  );
}