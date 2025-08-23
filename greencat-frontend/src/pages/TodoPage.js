// src/pages/TodoPage.js
import React, { useState } from 'react';
import TodoList from '../components/todo/TodoList';
import TodoForm from '../components/todo/TodoForm';
import { updateTodoCompleted, fetchAllTodos } from '../api/todoApi';

function TodoPage() {
    const [todos, setTodos] = useState([]);

    function refreshList() {
        fetchAllTodos().then(setTodos);
    }

    async function handleToggleCompleted(id, completed) {
        await updateTodoCompleted(id, completed);
        refreshList(); // 수정 후 목록 새로고침
    }

    function handleCreate(newTodo) {
        setTodos(prev => [...prev, newTodo]);
        refreshList();
    }

    return (
        <div>
            <h1>할일 관리</h1>
            <TodoForm onCreate={handleCreate} />
            <TodoList todos={todos} onToggleCompleted={handleToggleCompleted} />
        </div>
    );
}

export default TodoPage;
