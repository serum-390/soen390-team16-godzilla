import { Button, Grid, TextField } from '@material-ui/core';
import React from 'react';

//TODO Make Inline Styling

function Login() {
    return (
        <div>
            <Grid container style={{ minHeight: '100vh' }}>
                <Grid item xs={12} sm={6}>
                    <img src="https://www.wallpapertip.com/wmimgs/11-116117_electric-bike-by-audi-modern-design-e-bike.jpg"
                        style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                        alt="brand"
                    />
                </Grid>
                <Grid container item xs={12} sm={6} alignItems="center" direction="column" justify="space-between" style={{ padding: 10 }} >
                    <div />
                    <div style={{ display: 'flex', flexDirection: 'column', maxWidth: 400, minWidth: 300 }}>
                        <Grid container justify="center">
                            <img alt="Logo goes here" />
                        </Grid>
                        <TextField label="Username" margin="normal" />
                        <TextField label="Password" margin="normal" />
                        <div style={{ height: 20 }} />
                        <Button color="secondary" variant="contained">LOGIN</Button>
                        <div style={{ height: 10 }} />
                        <Button>Anonymous Login</Button>
                    </div>
                    <div />
                </Grid>
            </Grid>
        </div>
    );
}

export default Login;