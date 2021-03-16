import { Button, Grid, TextField } from '@material-ui/core';
import React from 'react';

//TODO Make Inline Styling

function SignUp() {
  return (
    <div>
      <Grid container style={{ minHeight: '100vh' }}>
        <Grid container item xs={12} sm={6} alignItems="center" direction="column" justify="space-between" style={{ padding: 10 }} >
          <div />
          <div style={{ display: 'flex', flexDirection: 'column', maxWidth: 400, minWidth: 300 }}>
            <Grid container justify="center">
              <img alt="Logo goes here" />
            </Grid>
            <TextField label="Username" margin="normal" />
            <TextField label="Password" margin="normal" type="password" />
            <TextField label="Confirm Password" margin="normal" type="password" />
            <div style={{ height: 20 }} />
            <Button color="primary" variant="contained">Sign Up</Button>
            <div style={{ height: 10 }} />
            <Button>Cancel</Button>
          </div>
          <div />
        </Grid>
        <Grid item xs={12} sm={6}>
          <img src="https://i.pinimg.com/originals/18/93/92/18939283816bc18797283b6b249f0401.jpg"
            style={{ width: '100%', height: '100%', objectFit: 'cover' }}
            alt="bikes"
          />
        </Grid>
      </Grid>
    </div>
  );
}

export default SignUp;