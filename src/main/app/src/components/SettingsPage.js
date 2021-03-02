import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import DisplaySettings from './settingMiniPages/DisplaySettings';
import UserManagement from './settingMiniPages/UserManagement';


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
}));


const SettingsMenu = () => {
    const classes = useStyles();
    return (
        <div className={classes.menu}>
            <Paper elevation={0}>
                <MenuList>
                    <MenuItem>User Management</MenuItem>
                    <MenuItem>Display Settings</MenuItem>
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
            <Paper elevation={0}>
            </Paper >
        </div>
    );
};


const ContentSwitch = () => {
    return (
        <Switch>
            <Route exact path="/settingMiniPages/DisplaySettings" component={DisplaySettings} />
            <Route exact path="/settingMiniPages/UserManagement" component={UserManagement} />
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
                    <Grid item>
                        <SettingsDisplay />
                    </Grid>
                </Grid>
                <ContentSwitch />
            </Router>
        </div>

    );
}

export default SettingsPage;