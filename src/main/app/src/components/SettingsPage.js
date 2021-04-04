import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import React from 'react';
import { BrowserRouter as Router, Link, Route, Switch, useLocation } from 'react-router-dom';
import DisplaySettings from './settings-mini-pages/DisplaySettings';
import { DemoGrid } from './settings-mini-pages/usermanagement/UserGrid';
import UserManagement from './settings-mini-pages/usermanagement/UserManagement';

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
      <Paper
        elevation={0}
        variant='outlined'
        style={{
          borderRadius: '1em',
        }}
      >
        <MenuList>
          <Link to='/settings/users' className={classes.link}>
            <MenuItem selected={location.pathname === '/settings/users'}>
              User Management
            </MenuItem>
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
      <ContentSwitch />
    </div>
  );
};

const ContentSwitch = () => {
  return (
    <Switch>
      <Route exact path="/settings/displaySettings" component={DisplaySettings} />
      <Route exact path="/settings/userManagement" component={UserManagement} />
      <Route exact path="/settings/users" component={DemoGrid} />
    </Switch>
  );
};

function SettingsPage() {
  return (
    <div>
      <Router on>
        <Grid container spacing={7}>
          <Grid item>
            <SettingsMenu />
          </Grid>
          <Grid item style={{ width: '80%', margin: 'auto', }}>
            <SettingsDisplay />
          </Grid>
        </Grid>
      </Router>
    </div>
  );
}

export default SettingsPage;
