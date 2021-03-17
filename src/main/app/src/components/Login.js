import { Button, Grid, TextField, makeStyles, Paper, useMediaQuery, useTheme } from '@material-ui/core';
import React from 'react';


//TODO Make Inline Styling

const useStyles = makeStyles(theme => ({
    divStyle: {
        alignContent: 'centre',
        display: 'flex',
        flexDirection: 'column',
        maxWidth: 400,
        minWidth: 30,
        borderRadius: 20,
        border: '30px solid',
        borderImage: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        padding: 50,
        borderImageSlice: 1
    }
}));

const LoginForm = () => {
    const classes = useStyles();
    return (
        <Paper className={classes.divStyle} square="false" variant="outlined" >
            <Grid container justify="center">
                <img alt="Logo goes here" />
            </Grid>
            <TextField label="Username" margin="normal" variant="outlined" />
            <TextField label="Password" margin="normal" type="password" variant="outlined" helperText="Forgot your password?" />
            <div style={{ height: 20 }} />
            <Button color="secondary" variant="contained">LOGIN</Button>
            <div style={{ height: 10 }} />
            <Button>Sign Up</Button>
        </Paper>
    );
}

const DesktopLogin = () => {
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
                    <LoginForm />
                    <div />
                </Grid>
            </Grid>
        </div>
    );
}


function Login() {
    const theme = useTheme();
    const desktop = useMediaQuery(theme.breakpoints.up('sm'));

    return (
        <div>
            {
                desktop ? <DesktopLogin />
                    : <LoginForm />
            }
        </div>
    );
}

export default Login;