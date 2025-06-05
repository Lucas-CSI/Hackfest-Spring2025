import React from 'react';
import './ScorePage.css'; // You can place the new styles in here or merge with your main CSS

const ScorePage = ({ wpm, accuracy, onRestart }) => {
  accuracy = Math.trunc(accuracy * 10000) / 100;
  wpm = Math.trunc(wpm * 100) / 100;
  return (
    <div className="score-container">
      <h1 className="score-title">Your Score</h1>
      <div className="score-stats">
        <div className="score-item">
          <span className="label">WPM:</span>
          <span className="value">{wpm}</span>
        </div>
        <div className="score-item">
          <span className="label">Accuracy:</span>
          <span className="value">{accuracy}%</span>
        </div>
      </div>
      <button className="form-button" onClick={() => window.location.href = '/'}>Try Again</button>
    </div>
  );
};

export default ScorePage;
