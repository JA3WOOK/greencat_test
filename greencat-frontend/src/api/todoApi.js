const BASE_URL = 'http://localhost:8080/api/todos';

export async function fetchAllTodos() {
    const res = await fetch(BASE_URL);
    if (!res.ok) throw new Error('Failed to fetch todos');
    return res.json();
}

export async function createTodo(todo) {
    const res = await fetch(BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo),
    });
    if (!res.ok) throw new Error('Failed to create todo');
    return res.json();
}

export async function updateTodoCompleted(id, completed) {
    const res = await fetch(`${BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ completed }),
    });
    if (!res.ok) throw new Error('Failed to update todo');
    return res.json();
}

export async function updateTodo(id, todo) {
    const res = await fetch(`${BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo),
    });
    if (!res.ok) throw new Error('Failed to update todo');
    return res.json();
}

// 필요에 따라 getTodoById, updateTodo, deleteTodo 함수도 추가
