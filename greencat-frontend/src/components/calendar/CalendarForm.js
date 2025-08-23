import React, { useState } from 'react';
import {
    TextField,
    Select,
    MenuItem,
    Button,
    Grid,
    InputLabel,
    FormControl,
    Box,
    Typography,
} from '@mui/material';
import { createEvent } from '../../api/calendarApi';

const statusOptions = ['CANCELLED', 'COMPLETED', 'IN_PROGRESS', 'SCHEDULED'];

function CalendarForm({ onCreate }) {
    const [form, setForm] = useState({
        date: '',
        title: '',
        description: '',
        status: 'SCHEDULED',
        startTime: '',
        endTime: '',
    });

    const [location, setLocation] = useState({
        address: '',
        city: '',
        country: '',
        state: '',
        venue: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleLocationChange = (e) => {
        const { name, value } = e.target;
        setLocation((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newEvent = {
                ...form,
                start_time: form.startTime,
                end_time: form.endTime,
                location,
            };
            const created = await createEvent(newEvent);
            onCreate(created);
            setForm({
                date: '',
                title: '',
                description: '',
                status: 'SCHEDULED',
                startTime: '',
                endTime: '',
            });
            setLocation({
                address: '',
                city: '',
                country: '',
                state: '',
                venue: '',
            });
        } catch (err) {
            alert('일정 생성 실패: ' + err.message);
        }
    };

    return (
        <Box sx={{ maxWidth: 600, p: 3, m: 'auto', boxShadow: 3, borderRadius: 2 }}>
            <Typography variant="h5" gutterBottom>
                일정 생성
            </Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    {/* 기본 정보 */}
                    <Grid item xs={12} sm={6}>
                        <TextField
                            label="날짜"
                            type="date"
                            name="date"
                            value={form.date}
                            onChange={handleChange}
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            required
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            label="제목"
                            name="title"
                            value={form.title}
                            onChange={handleChange}
                            fullWidth
                            required
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="설명"
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                            fullWidth
                            multiline
                            rows={3}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="status-label">상태</InputLabel>
                            <Select
                                labelId="status-label"
                                label="상태"
                                name="status"
                                value={form.status}
                                onChange={handleChange}
                            >
                                {statusOptions.map((option) => (
                                    <MenuItem key={option} value={option}>
                                        {option}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={6} sm={3}>
                        <TextField
                            label="시작 시간"
                            type="time"
                            name="startTime"
                            value={form.startTime}
                            onChange={handleChange}
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            inputProps={{ step: 300 }} // 5분 단위
                            required
                        />
                    </Grid>
                    <Grid item xs={6} sm={3}>
                        <TextField
                            label="종료 시간"
                            type="time"
                            name="endTime"
                            value={form.endTime}
                            onChange={handleChange}
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            inputProps={{ step: 300 }}
                            required
                        />
                    </Grid>

                    {/* 주소 정보 */}
                    <Grid item xs={12}>
                        <Typography variant="subtitle1" gutterBottom>
                            주소 정보
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="장소명"
                            name="venue"
                            value={location.venue}
                            onChange={handleLocationChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="주소"
                            name="address"
                            value={location.address}
                            onChange={handleLocationChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={6} sm={4}>
                        <TextField
                            label="도시"
                            name="city"
                            value={location.city}
                            onChange={handleLocationChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={6} sm={4}>
                        <TextField
                            label="주/도"
                            name="state"
                            value={location.state}
                            onChange={handleLocationChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={6} sm={4}>
                        <TextField
                            label="국가"
                            name="country"
                            value={location.country}
                            onChange={handleLocationChange}
                            fullWidth
                        />
                    </Grid>

                    {/* 제출 버튼 */}
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" fullWidth type="submit">
                            일정 추가
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </Box>
    );
}

export default CalendarForm;
