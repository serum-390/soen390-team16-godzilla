import { Button, makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import React, { useState } from 'react';

function Home() {
  const [someState, setSomeState] = useState(false);
  const classes = makeStyles(theme => ({
    baseClase: {
      border: '5px grey solid',
      width: '30%',
      height: '20em',
      margin: 'auto',
    },
    class1: {
      width: '50%',
      height: '5em',
      position: 'fixed',
      top: '10em',
      left: '20em',
      backgroundColor: 'yellow',
      transition: theme.transitions.create(['all'], {
        duration: '2s',
        easing: theme.transitions.easing.easeInOut,
      }),
      transform: 'translate(-80%)',
    },
    class2: {
      width: '30%',
      display: 'block',
      top: '50em',
      left: '80em',
      height: '20em',
      backgroundColor: 'grey',
      transition: theme.transitions.create(['all'], {
        duration: '2s',
        easing: theme.transitions.easing.easeInOut,
      }),
      transform: 'scale(1.1) translate(80%)'
    },
  }))();

  const doSomething = () => {
    setSomeState(!someState);
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignContent: 'center',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <div
        className={clsx(classes.baseClase, {
          [classes.class1]: !someState,
          [classes.class2]: someState,
        })}
      >
      </div>
      <Button
        variant='contained'
        onClick={doSomething}
        style={{
          width: '10em',
          height: '3em',
          position: 'fixed',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
        }}
      >
        Click me
      </Button>
    </div>
  );
}

export default Home;
