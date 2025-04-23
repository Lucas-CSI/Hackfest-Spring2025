import React, { useState, useEffect, useRef } from 'react';

const WORD_LIST = [
  'example','clone','typing','test','javascript','react','keyboard','speed','accuracy','timer',
  'random','words','display','challenge','practice','monkeytype','interface','cursor','highlight','layout',
  'function','component','state','effect','hook','ux','ui','design','style','darkmode',
  'performance','metrics','dashboard','feedback','typingtest','application','frontend','project','code','open',
  'source','editor','browser','node','npm','yarn','module','bundle','build','deploy'
];
const TOTAL_WORDS = 30;

export default function App() {
  const [words, setWords] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [input, setInput] = useState('');
  const [timer, setTimer] = useState(30);
  const [started, setStarted] = useState(false);
  const inputRef = useRef(null);
  const [inputCache, setInputCache] = useState('');

  useEffect(() => {
    const arr = [];
    for (let i = 0; i < TOTAL_WORDS; i++) {
      const w = WORD_LIST[Math.floor(Math.random() * WORD_LIST.length)];
      arr.push(w);
    }
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
      if(inputCache.length == 0)
        setInputCache(input);
      else
        setInputCache(inputCache + " " + input);
      setInput('');
      setCurrentIndex(i => i + 1);
    } else {
      setInput(val);
    }
  };

  const minutes = String(Math.floor(timer / 60)).padStart(2, '0');
  const seconds = String(timer % 60).padStart(2, '0');

  return (

    <div className="app">
      <img src={"/image.png"} alt="Baylor Bear 1" className="logo"></img>
      <div className="title">Baylor type</div>
      <div className="timer">{minutes}:{seconds}</div>
      <div className="words-container" onClick={() => inputRef.current.focus()}>
        {words.map((w, i) => {
          const isActive = i === currentIndex;
          const classNames = ['word', isActive ? 'active' : ''].join(' ');
          let display;
          if (isActive) {
            display = w.split('').map((char, idx) => {
              let cls = '';
              if (idx < input.length) {
                cls = char === input[idx] ? 'correct' : 'incorrect';
              }
              return (
                <span key={idx} className={cls}>
                  {char}
                </span>
              );
            });
          } else if(i < currentIndex) {
            let cachedWord = inputCache.split(" ")[i]; 
            display = w.split('').map((char, idx) => {
              let cls = '';
              if (idx < cachedWord.length) {
                cls = char === cachedWord[idx] ? 'correct' : 'incorrect';
              }
              return (
                <span key={idx} className={cls}>
                  {char}
                </span>
              );
            });
          } else {
            display = w;
          }
          return (
            <span key={i} className={classNames}>
              {display}
            </span>
          );
        })}
      </div>
      <input
        ref={inputRef}
        className="typing-input"
        type="text"
        value={input}
        onChange={handleChange}
        disabled={timer === 0}
        placeholder={timer === 0 ? 'Times up!' : ''}
      />

    </div>
  );
}