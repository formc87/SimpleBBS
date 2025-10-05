import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './styles.css';

// React 18 방식으로 루트 DOM 노드를 찾아 애플리케이션을 렌더링합니다.
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    {/* StrictMode는 개발 중 잠재적인 문제를 조기에 발견할 수 있게 도와줍니다. */}
    <App />
  </React.StrictMode>
);
