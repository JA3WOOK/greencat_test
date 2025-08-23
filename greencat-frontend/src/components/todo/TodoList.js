import React, { useEffect, useState } from 'react';
import { fetchAllTodos, updateTodo } from '../../api/todoApi';

const ITEMS_PER_PAGE = 10;

function TodoList() {
    const [todos, setTodos] = useState([]);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        fetchAllTodos()
            .then(setTodos)
            .catch(e => setError(e.message));
    }, []);

    // 현재 페이지에 보여줄 todo slice 계산
    const indexOfLastTodo = currentPage * ITEMS_PER_PAGE;
    const indexOfFirstTodo = indexOfLastTodo - ITEMS_PER_PAGE;
    const currentTodos = todos.slice(indexOfFirstTodo, indexOfLastTodo);
    const totalPages = Math.ceil(todos.length / ITEMS_PER_PAGE);

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const handleToggle = async (todo) => {
        try {
            const updated = await updateTodo(todo.id, {
                ...todo,
                completed: !todo.completed
            });
            setTodos(todos.map(t => t.id === todo.id ? updated : t));
        } catch (e) {
            alert('상태 변경 실패: ' + e.message);
        }
    };

    if (error) return <div>에러: {error}</div>;

    return (
        <div>
            <h2>할일 목록</h2>
            <ul>
                {currentTodos.map(todo => (
                    <li key={todo.id}>
                        <b>{todo.title}</b> - {todo.description}
                        <button onClick={() => handleToggle(todo)} style={{ marginLeft: "1em" }}>
                            {todo.completed ? "✅ 완료" : "❌ 미완료"}
                        </button>
                    </li>
                ))}
            </ul>
            <div style={{ marginTop: "1em" }}>
                {Array.from({ length: totalPages }, (_, i) => i + 1).map(page =>
                    <button
                        key={page}
                        onClick={() => handlePageChange(page)}
                        style={{
                            margin: '2px',
                            fontWeight: currentPage === page ? 'bold' : '',
                            background: currentPage === page ? '#eef' : '#fff'
                        }}
                    >
                        {page}
                    </button>
                )}
            </div>
        </div>
    );
}

export default TodoList;
