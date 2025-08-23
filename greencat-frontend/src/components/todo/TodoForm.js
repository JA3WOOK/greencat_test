import React, { useState } from 'react';
import { createTodo } from '../../api/todoApi';

function TodoForm({ onCreate }) {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            const newTodo = await createTodo({ title, description, completed: false });
            onCreate(newTodo);
            setTitle('');
            setDescription('');
        } catch (e) {
            alert('할일 생성 실패: ' + e.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="제목" value={title} onChange={e => setTitle(e.target.value)} required />
            <input placeholder="설명" value={description} onChange={e => setDescription(e.target.value)} />
            <button type="submit">추가</button>
        </form>
    );
}

export default TodoForm;
