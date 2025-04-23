// src/Login.jsx
import React, { useState } from 'react';
import './styles.css';

export default function Login({ onSwitch }) {
  const [form, setForm] = useState({ username: '', password: '' });
  const [errors, setErrors] = useState({});

  const validate = () => {
    const errs = {};
    if (!form.username.trim()) errs.username = 'Username is required';
    if (!form.password) errs.password = 'Password is required';
    return errs;
  };

  const handleChange = e => {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: value }));
  };

  const handleSubmit = e => {
    e.preventDefault();
    const errs = validate();
    setErrors(errs);
    if (Object.keys(errs).length === 0) {
      // TODO: replace this with your real login API call
      // if login succeeds:
      document.cookie = `user=${encodeURIComponent(form.username)}; path=/`;
      window.location.reload();
    }
  };

  return (
    <div className="app">
      <form className="form-container" onSubmit={handleSubmit}>
        <h1>Log In</h1>

        <div className="form-field">
          <label htmlFor="username">Username</label>
          <input
            className="form-input"
            id="username"
            name="username"
            type="text"
            value={form.username}
            onChange={handleChange}
            placeholder="your username"
          />
          {errors.username && <div className="form-error">{errors.username}</div>}
        </div>

        <div className="form-field">
          <label htmlFor="password">Password</label>
          <input
            className="form-input"
            id="password"
            name="password"
            type="password"
            value={form.password}
            onChange={handleChange}
            placeholder="••••••••"
          />
          {errors.password && <div className="form-error">{errors.password}</div>}
        </div>

        <button className="form-button" type="submit">
          Log In
        </button>

        <div className="form-toggle">
          Don’t have an account?{' '}
          <span onClick={onSwitch}>Sign up</span>
        </div>
      </form>
    </div>
  );
}
