import { useState } from 'react';

const API_URL = "http://localhost:8080";

const FlagSubmissionPage = () => {
  const [flag, setFlag] = useState('');
  const [error, setError] = useState('');
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = async(e) => {
    e.preventDefault();

    if (!flag.trim()) {
      setError('Please enter a flag.');
      return;
    }

    const response = await fetch(`${API_URL}/api/flag/plant` + "?flag=" + form.flag, {
        method: 'POST',
        credentials: 'include'
      });
    setSubmitted(true);
    setError('');
    document.cookie = "setFlag=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  };

  return (
    <div className="app">
      <div className="form-container">
        <h1>Submit Flag</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-field">
            <label htmlFor="flag">Flag</label>
            <input
              id="flag"
              className="form-input"
              type="text"
              placeholder="Enter your flag here"
              value={flag}
              onChange={(e) => setFlag(e.target.value)}
            />
            {error && <div className="form-error">{error}</div>}
          </div>
          <button className="form-button" type="submit" disabled={submitted}>
            {submitted ? 'Submitted' : 'Submit'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default FlagSubmissionPage;
