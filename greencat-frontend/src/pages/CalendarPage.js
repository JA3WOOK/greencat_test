import React, { useState } from 'react';
import CalendarList from '../components/calendar/CalendarList';
import CalendarForm from '../components/calendar/CalendarForm';
import { Grid, Box, Typography } from '@mui/material';

function CalendarPage() {
    const [events, setEvents] = useState([]);

    function handleCreate(newEvent) {
        setEvents((prev) => [...prev, newEvent]);
    }

    return (
        <Box sx={{ flexGrow: 1, p: 4 }}>
            <Typography variant="h4" gutterBottom>
                일정 관리
            </Typography>
            <Grid
                container
                spacing={4}
                alignItems="stretch" //
                sx={{ minHeight: '650px' }}
            >
                {/* 왼쪽: 일정 생성 */}
                <Grid item xs={12} md={6} sx={{ display: 'flex', flexDirection: 'column' }}>
                    <Box sx={{ height: '100%' }}>
                        <CalendarForm onCreate={handleCreate} />
                    </Box>
                </Grid>
                {/* 오른쪽: 일정 목록 */}
                <Grid item xs={12} md={6} sx={{ display: 'flex', flexDirection: 'column' }}>
                    <Box sx={{ height: '100%' }}>
                        <CalendarList events={events} />
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
}
export default CalendarPage;
