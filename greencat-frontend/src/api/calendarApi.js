const BASE_URL = 'http://localhost:8080/api/calendar';

export async function fetchAllEvents() {
    const res = await fetch(BASE_URL);
    if (!res.ok) throw new Error('Failed to fetch events');
    return res.json();
}

export async function createEvent(event) {
    const res = await fetch(BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(event),
    });
    if (!res.ok) throw new Error('Failed to create event');
    return res.json();
}

export async function updateEvent(id, event) {
    const res = await fetch(`${BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(event),
    });
    if (!res.ok) throw new Error('Failed to update event');
    return res.json();
}
