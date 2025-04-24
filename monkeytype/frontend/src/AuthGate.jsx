// src/AuthGate.jsx
import React, { useState } from 'react';
import App from './App.jsx';
import Login from './Login.jsx';
import CreateAccount from './CreateAccount.jsx';
import FlagSubmissionPage from './FlagSubmission.jsx';

function getCookie(name) {
  const m = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  return m ? m[2] : null;
}

export default function AuthGate() {
  const user = getCookie('user');
  const setFlag = getCookie('setFlag');
  const [mode, setMode] = useState('login'); 

  if(setFlag){
    return <FlagSubmissionPage />
  }

  if (user) {
    return <App />;
  }

  return mode === 'login' ? (
    <Login onSwitch={() => setMode('signup')} />
  ) : (
    <CreateAccount onSwitch={() => setMode('login')} />
  );
}