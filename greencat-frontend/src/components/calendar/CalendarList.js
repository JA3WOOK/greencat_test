import React, { useEffect, useState } from 'react';
import { fetchAllEvents, updateEvent } from '../../api/calendarApi';
import {
    Box,
    Card,
    CardContent,
    Typography,
    Button,
    Grid,
    Pagination
} from '@mui/material';

const ITEMS_PER_PAGE = 6;
const statusLabels = {
    CANCELLED: '취소됨',
    COMPLETED: '완료',
    IN_PROGRESS: '진행중',
    SCHEDULED: '예정',
};

function CalendarList() {
    const [events, setEvents] = useState([]);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        loadEvents();
    }, []);

    const loadEvents = () => {
        fetchAllEvents()
            .then(setEvents)
            .catch((e) => setError(e.message));
    };

    const indexOfLastEvent = currentPage * ITEMS_PER_PAGE;
    const indexOfFirstEvent = indexOfLastEvent - ITEMS_PER_PAGE;
    const currentEvents = events.slice(indexOfFirstEvent, indexOfLastEvent);
    const totalPages = Math.ceil(events.length / ITEMS_PER_PAGE);

    const handleStatusToggle = async (event) => {
        try {
            const order = ['SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'];
            const currentIndex = order.indexOf(event.status);
            const nextIndex = (currentIndex + 1) % order.length;

            const updated = await updateEvent(event.id, {
                ...event,
                status: order[nextIndex],
            });
            setEvents(events.map((ev) => (ev.id === event.id ? updated : ev)));
        } catch (e) {
            alert('상태 변경 실패: ' + e.message);
        }
    };

    if (error) return <div>에러: {error}</div>;

    return (
        <Box sx={{ maxWidth: 800, m: 'auto', mt: 4 }}>
            <Typography variant="h5" gutterBottom>
                일정 목록
            </Typography>

            <Grid container spacing={2}>
                {currentEvents.map((ev) => (
                    <Grid item xs={12} key={ev.id}>
                        <Card variant="outlined" sx={{ borderRadius: 2, boxShadow: 2 }}>
                            <CardContent>
                                <Typography variant="h6" gutterBottom>
                                    {ev.title}
                                </Typography>
                                <Typography color="text.secondary" gutterBottom>
                                    {ev.description || '설명 없음'}
                                </Typography>
                                <Typography variant="body2" sx={{ mt: 1 }}>
                                    📅 {ev.date} ⏰ {ev.startTime} ~ {ev.endTime}
                                </Typography>
                                <Typography variant="body2" sx={{ mt: 0.5 }}>
                                    📍 {ev.address} {ev.city} {ev.state} {ev.country}
                                </Typography>
                                <Box sx={{ mt: 2, textAlign: 'right' }}>
                                    <Button
                                        variant="contained"
                                        color={
                                            ev.status === 'COMPLETED'
                                                ? 'success'
                                                : ev.status === 'IN_PROGRESS'
                                                    ? 'info'
                                                    : ev.status === 'CANCELLED'
                                                        ? 'error'
                                                        : 'primary'
                                        }
                                        onClick={() => handleStatusToggle(ev)}
                                    >
                                        {statusLabels[ev.status] || ev.status}
                                    </Button>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            {/* 페이지네이션 */}
            {totalPages > 1 && (
                <Box sx={{ display: 'flex', justifyContent: 'center', mt: 3 }}>
                    <Pagination
                        count={totalPages}
                        page={currentPage}
                        onChange={(e, page) => setCurrentPage(page)}
                        color="primary"
                        shape="rounded"
                    />
                </Box>
            )}
        </Box>
    );
}

export default CalendarList;
