import React from 'react';
import { BrowserRouter as Router, Route, Link, Switch, useLocation } from 'react-router-dom';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';

const useStyles = makeStyles((theme) => ({
  menu: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(30),
      height: theme.spacing(25),
    },
  },
  mainDisplay: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(120),
      height: theme.spacing(50),
    },
  },
  link: {
    textDecoration: 'none',
    color: theme.palette.text.primary,
  },
}));


const SettingsMenu = () => {
  const classes = useStyles();
  const location = useLocation();
  return (
    <div className={classes.menu}>
      <Paper elevation={3}>
        <MenuList>
          <Link to='/settings/userManagement'
            selected={location.pathname === '/settings/userManagement'}
            className={classes.link}
          >
            <MenuItem>
              User Management
            </MenuItem>
          </Link>
          <Link to='/settings/displaySettings'
            selected={location.pathname === '/settings/displaySettings'}
            className={classes.link}
          >
            <MenuItem>Display Settings</MenuItem>
          </Link>
          <MenuItem>Authentication Settings</MenuItem>
          <MenuItem>Notification Settings</MenuItem>
          <MenuItem>File Upload Settings</MenuItem>
        </MenuList>
      </Paper >
    </div>
  );
};

const SettingsDisplay = () => {
  const classes = useStyles();
  return (
    <div className={classes.mainDisplay}>
      <Paper elevation={3}>
        <ContentSwitch />
      </Paper >
    </div>
  );
};

const ContentSwitch = () => {
  return (
    <Switch>
      <Route exact path="/settings/displaySettings" component={DisplaySettings} />
      <Route exact path="/settings/userManagement" component={UserManagement} />
    </Switch>
  );
};


function Help() {
  return (
    <div>
      <h1>This is the  Help page</h1>
    </div>
  );
}

export default Help;