import React, { useState, useEffect, useRef } from 'react';
import ScorePage from './ScorePage';

const TOTAL_WORDS = 30;
const API_URL = "http://localhost:8080";

export default function App() {
  const [words, setWords] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [input, setInput] = useState('');
  const [started, setStarted] = useState(false);
  const [inputCache, setInputCache] = useState('');
  const [showScore, setShowScore] = useState(false);
  const [score, setScore] = useState({ wpm: 0, accuracy: 0 });
  const [flag, setFlag] = useState('');

  const inputRef = useRef(null);

  useEffect(() => {
    if (!showScore) fetchWords();
  }, [showScore]);

  const fetchWords = async () => {
    try {
      const res = await fetch(`${API_URL}/api/game/start`, {
        method: 'POST',
        credentials: 'include',
      });
      const wordList = await res.json();
      if(wordList[0] === "setflag")
	    location.reload();
      const res2 = await fetch(`${API_URL}/api/flag/get`, {
        method: 'GET',
        credentials: 'include',
      });
      setFlag(await res2.text());
      setWords(wordList);
    } catch (err) {
      console.error('Error fetching words:', err);
    }
  };

  const handleSubmit = async () => {
    const typedWords = inputCache.trim().split(' ');
    const query = typedWords.map(w => `words=${encodeURIComponent(w)}`).join('&');

    try {
      const response = await fetch(`${API_URL}/api/game/submit?${query}`, {
        method: 'POST',
        credentials: 'include',
      });
      const result = await response.json();

      setScore({ wpm: result.wpm, accuracy: result.accuracy });
      setShowScore(true);
    } catch (error) {
      console.error('Submit error:', error);
    }
  };

  useEffect(() => {
    if (currentIndex === TOTAL_WORDS) handleSubmit();
  }, [inputCache])

  const handleChange = async(e) => {
    const val = e.target.value;
    if (!started) {
      const res = await fetch(`${API_URL}/api/game/typing`, {
        method: 'POST',
        credentials: 'include',
      });
      setStarted(true);
      inputRef.current?.focus();
    }


    if (val.endsWith(' ')) {
      const trimmed = input.trim();
      setInputCache(prev => prev ? `${prev} ${trimmed}` : trimmed);
      setCurrentIndex(currentIndex + 1);
      setInput('');
    } else {
      setInput(val);
    }
  };

  const handleRestart = () => {
    setWords([]);
    setCurrentIndex(0);
    setInput('');
    setStarted(false);
    setInputCache('');
    setShowScore(false);
    setScore({ wpm: 0, accuracy: 0 });
  };

  if (showScore) {
    return <ScorePage wpm={score.wpm} accuracy={score.accuracy} onRestart={handleRestart} />;
  }

  return (
    <div className="app">
      <img src="/image.png" alt="Baylor Bear 1" className="logo" />
      <div className="title">BaylorType</div>
      <h2>Can you type at 200+ WPM with 99.99%+ accuracy???</h2>
      <h2>Current Flag: {flag}</h2>
      <div className="timer">{`Progress: ${currentIndex}/${TOTAL_WORDS}`}</div>

      <div className="words-container" onClick={() => inputRef.current?.focus()}>
        {words.map((w, i) => {
          const isActive = i === currentIndex;
          const classNames = ['word', isActive ? 'active' : ''].join(' ');
          let display;

          if (isActive) {
            display = w.split('').map((char, idx) => {
              const cls = idx < input.length ? (char === input[idx] ? 'correct' : 'incorrect') : '';
              return <span key={idx} className={cls}>{char}</span>;
            });
          } else if (i < currentIndex) {
            const cachedWord = inputCache.split(' ')[i] || '';
            display = w.split('').map((char, idx) => {
              const cls = idx < cachedWord.length ? (char === cachedWord[idx] ? 'correct' : 'incorrect') : '';
              return <span key={idx} className={cls}>{char}</span>;
            });
          } else {
            display = w;
          }

          return <span key={i} className={classNames}>{display}</span>;
        })}
      </div>

      <input
        ref={inputRef}
        className="typing-input"
        type="text"
        value={input}
        onChange={handleChange}
        disabled={currentIndex >= TOTAL_WORDS}
        placeholder=""
      />
    </div>
  );
}
