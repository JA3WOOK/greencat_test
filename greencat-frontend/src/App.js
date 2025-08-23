// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CalendarPage from './pages/CalendarPage';
import TodoPage from './pages/TodoPage';

function App() {
  return (
      <Router>
        <nav>
          <Link to="/calendar">캘린더</Link> | <Link to="/todos">할일</Link>
        </nav>
        <Routes>
          <Route path="/calendar" element={<CalendarPage />} />
          <Route path="/todos" element={<TodoPage />} />
        </Routes>
      </Router>
  );
}

export default App;
