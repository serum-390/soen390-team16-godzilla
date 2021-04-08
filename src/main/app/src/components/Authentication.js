import { Button, makeStyles, TextField, Typography, useMediaQuery, useTheme } from '@material-ui/core';
import axios from 'axios';
import React, { Fragment, useState } from 'react';

const borderWidth = '2em';
const maxDimensions = x => ({ height: '100%', width: '100%', ...x });

const useStyles = (small = false, errorShown = false) => makeStyles(theme => ({
  root: {
    height: '100vh',
    width: '100vw',
    overflow: 'hidden',
    '& a': {
      width: 'fit-content',
      height: 'fit-content',
      marginBottom: '2em',
    },
  },

  bg: maxDimensions({
    position: 'absolute',
    zIndex: -2,
    opacity: 0.7,
  }),

  desktopRoot: maxDimensions({
    display: 'grid',
    gridTemplateRows: 'auto',
    gridTemplateColumns: '1fr 1fr',
    '& img': maxDimensions({
      objectFit: 'cover',
    }),
  }),

  "mobileRoot": maxDimensions({
    display: 'flex',
    placeItems: 'center',
  }),

  "signupForm": {
    "display": 'flex',
    "placeItems": 'center',
    "width": '90%',
    margin: 'auto',
    maxWidth: '30em',
    position: 'relative',
    padding: '3em',
    boxSizing: 'border-box',
    background: 'white',
    backgroundClip: 'padding-box !important',
    border: `${borderWidth} solid transparent !important`,
    borderRadius: '3em',
    boxShadow: '10px 10px 10px grey',

    '&::before': {
      content: '""',
      position: 'absolute',
      top: '-3em', right: 0, left: '-10em', bottom: 0,
      zIndex: -2,
      margin: `-${borderWidth} !important`,
      borderRadius: '30em',
      background: 'linear-gradient(red, yellow)',
      width: '50em',
      height: '50em',
      backgroundSize: '100% 100%',
      animation: '$Background-shift 3s linear infinite',
    },

    '& > div': {
      display: 'flex',
      flexDirection: 'column',
      margin: 'auto',
      '& img': {
        alignSelf: 'center',
      },
    },

    '&  button': {
      margin: '0.5em',
    },
  },

  shadowForm: {
    margin: 'auto',
    marginLeft: 'calc(auto + 1em)',
    position: 'absolute',
    padding: '3em',
    width: '24.1em',
    height: small ? errorShown ? '36.1em' : '35.1em' : '39em',
    right: '-66.95em',
    zIndex: -1,
    background: 'transparent',
    backgroundClip: 'padding-box !important',
    border: `65em solid white !important`,
    borderRadius: '68em',
    transform: 'translate(0.1em)',
  },

  nameLogo: {
    width: 'auto',
    height: 'auto',
    maxHeight: '3em',
    transition: theme.transitions.create(['transform'], {
      duration: theme.transitions.duration.shorter,
      easing: theme.transitions.easing.sharp,
    }),
    '&:hover': {
      transform: 'scale(1.2)',
    },
  },

  appLogoContainer: {
    alignSelf: 'center',
    maxHeight: '7em',
    maxWidth: '6em',
    marginBottom: '2em',
    transition: theme.transitions.create(['transform'], {
      duration: theme.transitions.duration.shorter,
      easing: theme.transitions.easing.sharp,
    }),
    '&:hover, &:target, &:focus': {
      transform: 'scale(1.2)',
    },
  },

  appLogo: maxDimensions({
    objectFit: 'contain',
    animation: '$App-logo-spin infinite 20s linear',
  }),

  signupLink: {
    display: 'flex',
    placeContent: 'center',
    placeItems: 'center',
    alignSelf: 'center',
    textAlign: 'center',
    marginBottom: '0px !important',
    transition: theme.transitions.create(['all'], {
      duration: theme.transitions.duration.shorter,
      easing: theme.transitions.easing.sharp,
    }),
    '&:hover': {
      transform: 'scale(1.1)',
    },
    '&:link': {
      textDecoration: 'none',
    },
  },

  '@keyframes App-logo-spin': {
    '0%': { transform: 'rotate(0deg)' },
    '100%': { transform: 'rotate(360deg)' },
  },

  '@keyframes Background-shift': {
    '0%': { transform: 'rotate(0deg) scale(1.7)' },
    '100%': { transform: 'rotate(360deg) scale(1.7)' },
  },
}))();

const sendSignupData = async (data, onError = e => { }) => {
  try {
    const api = '/api/users/signup/';
    const posted = await axios.post(api, data);
    console.log(`STATUS CODE: ${posted.status}`);
    console.log(`DATA: ${posted.data || "Nothing"}`);
    return posted.status === 200 ? posted.data || {} : {};
  } catch (err) {
    console.log(err);
    onError(err);
    return err;
  }
};

const login = async (username, password) => {
  const api = '/login'
  const data = new URLSearchParams();
  data.append("username", username);
  data.append("password", password);
  const posted = await axios.post(api, data);
  console.log(`STATUS CODE: ${posted.status}`);
  if (posted.headers && posted.request.responseURL.match(/.*?error$/)) {
    throw new Error("Auth failed...");
  }
  return posted && posted.data;
};

const EmailField = ({ excludeEmailField, setEmail }) => excludeEmailField
  ? <Fragment />
  : <TextField
    color='secondary'
    label='Email'
    margin='normal'
    variant='outlined'
    onChange={e => setEmail(e.target.value)}
  />;

const SpinningAppLogo = props => {
  const classes = useStyles(false);
  return (
    <a href="/" className={classes.appLogoContainer}>
      <img alt='logo'
        src='/resources/images/logo-min.png'
        className={classes.appLogo}
      />
    </a>
  );
};

const SignupLink = props => {
  const classes = useStyles();
  return (
    <a href="/signup" className={classes.signupLink} {...props}>
      <Typography>Don't have an account? Signup</Typography>
    </a>
  );
};

const SignupForm = ({ excludeEmailField, setErrorStyling, loginForm, ...props }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [badCreds, setBadCreds] = useState(false);
  const [email, setEmail] = useState("");

  const submitSignup = () => {
    const sendData = new URLSearchParams();
    sendData.append("username", userName)
    sendData.append("password", password)
    sendData.append("email", email)
    sendSignupData(sendData)
  };

  const turnOffErrorStyling = () => {
    setBadCreds(false);
    setErrorStyling(false);
  };

  const submitLogin = () => {
    login(userName, password)
      .then(() => document.location.href = "/")
      .catch(e => {
        console.log(e);
        setBadCreds(true);
        setErrorStyling(true);
      });
  };

  return (
    <Fragment>
      <TextField
        error={badCreds}
        color='secondary'
        label='Username'
        margin='normal'
        variant='outlined'
        onChange={e => { setUserName(e.target.value); turnOffErrorStyling(); }}
        helperText={badCreds ? "Incorrect username or password" : null}
      />
      <TextField
        color='secondary'
        label='Password'
        margin='normal'
        type='password'
        variant='outlined'
        onChange={e => { setPassword(e.target.value); turnOffErrorStyling(); }}
      />
      <EmailField excludeEmailField={excludeEmailField} setEmail={e => { setEmail(e); turnOffErrorStyling(); }} />
      <Button color='secondary' variant='contained' onClick={loginForm ? submitLogin : submitSignup}>
        {loginForm ? "Login" : "Sign Up"}
      </Button>
      <Button color='default' variant='outlined'>Back</Button>
      {loginForm ? <SignupLink /> : null}
    </Fragment>
  );
};

const Root = ({ children, mobile, ...props }) => {
  const classes = useStyles();
  return (
    <div className={classes.root} {...props}>
      <div className={classes.bg} />
      <div className={mobile ? classes.mobileRoot : classes.desktopRoot}>
        {children}
      </div>
    </div>
  );
};

const DesktopSignup = props => {
  const classes = useStyles();
  return (
    <Root {...props}>
      <div className={classes.signupForm} {...props}>
        <div className={classes.shadowForm} />
        <div>
          <SpinningAppLogo />
          <a href="/" style={{ marginBottom: '1em', }}>
            <img
              src='/resources/images/name-logo-min.png'
              alt='Godzilla ERP'
              className={classes.nameLogo}
            />
          </a>
          <SignupForm />
        </div>
      </div>
      <img alt="brand"
        src="https://i.pinimg.com/originals/18/93/92/18939283816bc18797283b6b249f0401.jpg"
      />
    </Root>
  );
};

const MobileSignup = props => {
  const classes = useStyles();
  return (
    <Root mobile {...props}>
      <div className={classes.signupForm} {...props}>
        <div className={classes.shadowForm} />
        <div>
          <SpinningAppLogo />
          <a href="/" style={{ marginBottom: '1em', }}>
            <img
              src='/resources/images/name-logo-min.png'
              alt='Godzilla ERP'
              className={classes.nameLogo}
            />
          </a>
          <SignupForm excludeEmailField={false} />
        </div>
      </div>
    </Root>
  );
};

const Signup = props =>
  useMediaQuery(useTheme().breakpoints.up('md'))
    ? <DesktopSignup {...props} />
    : <MobileSignup {...props} />;

const DesktopLogin = props => {
  const [errorStyling, setErrorStyling] = useState(false);
  const classes = useStyles(true, errorStyling);
  return (
    <Root {...props}>
      <img alt="brand"
        src="https://www.wallpapertip.com/wmimgs/11-116117_electric-bike-by-audi-modern-design-e-bike.jpg"
      />
      <div className={classes.signupForm} {...props}>
        <div className={classes.shadowForm} />
        <div>
          <SpinningAppLogo />
          <a href="/" style={{ marginBottom: '1em', }}>
            <img
              src='/resources/images/name-logo-min.png'
              alt='Godzilla ERP'
              className={classes.nameLogo}
            />
          </a>
          <SignupForm excludeEmailField loginForm setErrorStyling={setErrorStyling} />
        </div>
      </div>
    </Root>
  );
};

const MobileLogin = props => {
  const [errorStyling, setErrorStyling] = useState(false);
  const classes = useStyles(true, errorStyling);
  return (
    <Root mobile {...props}>
      <div className={classes.signupForm} {...props}>
        <div className={classes.shadowForm} />
        <div>
          <SpinningAppLogo />
          <a href="/" style={{ marginBottom: '1em', }}>
            <img
              src='/resources/images/name-logo-min.png'
              alt='Godzilla ERP'
              className={classes.nameLogo}
            />
          </a>
          <SignupForm excludeEmailField loginForm setErrorStyling={setErrorStyling} />
        </div>
      </div>
    </Root>
  );
};

const Login = props =>
  useMediaQuery(useTheme().breakpoints.up('md'))
    ? <DesktopLogin />
    : <MobileLogin />;

export default Signup;
export { Signup, Login };
