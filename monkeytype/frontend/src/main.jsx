import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.jsx';
import CreateAccount from './CreateAccount.jsx';
import './styles.css';

// simple helper to read a cookie by name
function getCookie(name) {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  return match ? match[2] : null;
}

const container = document.getElementById('root');
const root = createRoot(container);

const user = getCookie('user');

root.render(
  <React.StrictMode>
    {user
      ? <App />
      : <CreateAccount />
    }
  </React.StrictMode>
);