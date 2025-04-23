

import React, { useState, useEffect, useRef } from 'react';



export default function App() {
  const [words, setWords] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [input, setInput] = useState('');
  const [timer, setTimer] = useState(60);
  const [started, setStarted] = useState(false);
  const inputRef = useRef(null);

  useEffect(() => {
    // generate random words
    const arr = [];

    setWords(arr);
  }, []);

  useEffect(() => {
    let interval;
    if (started && timer > 0) {
      interval = setInterval(() => setTimer(t => t - 1), 1000);
    }
    return () => clearInterval(interval);
  }, [started, timer]);

  const handleChange = e => {
    if (!started) {
      setStarted(true);
      inputRef.current.focus();
    }
    const val = e.target.value;
    if (val.endsWith(' ')) {
      // on space, advance
      setInput('');
      setCurrentIndex(i => i + 1);
    } else {
      setInput(val);
    }
  };

  const minutes = String(Math.floor(timer/60)).padStart(2,'0');
  const seconds = String(timer % 60).padStart(2,'0');

  return (
    <div className="app">
      <div className="timer">{minutes}:{seconds}</div>
      <div className="words-container" onClick={() => inputRef.current.focus()}>
        {words.map((w,i) => {
          const isActive = i === currentIndex;
          const classNames = ['word', isActive ? 'active' : ''];
          // highlight correct letters
          let display = w;
          if (isActive) {
            const correct = w.slice(0, input.length);
            const rest = w.slice(input.length);
            display = (
              <>
                <span className="correct">{correct}</span>
                <span>{rest}</span>
              </>
            );
          }
          return <span key={i} className={classNames.join(' ')}>{display}</span>;
        })}
      </div>
      <input
        ref={inputRef}
        className="typing-input"
        type="text"
        value={input}
        onChange={handleChange}
        disabled={timer === 0}
        placeholder={timer === 0 ? 'Time up!' : ''}
      />
    </div>
  );
}