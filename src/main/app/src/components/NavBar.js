import { AppBar, Button, CssBaseline, Dialog, DialogContentText, Drawer, Fade, IconButton, List, ListItemIcon, Toolbar } from '@material-ui/core';
import InputBase from '@material-ui/core/InputBase';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { useTheme } from "@material-ui/core/styles";
import Typography from '@material-ui/core/Typography';
import { ChevronLeft } from '@material-ui/icons';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import DoubleArrowRoundedIcon from '@material-ui/icons/DoubleArrowRounded';
import EventIcon from '@material-ui/icons/Event';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import HelpIcon from '@material-ui/icons/Help';
import HomeIcon from '@material-ui/icons/Home';
import InfoIcon from '@material-ui/icons/Info';
import ListAltIcon from '@material-ui/icons/ListAlt';
import MenuIcon from '@material-ui/icons/Menu';
import MotorcycleIcon from '@material-ui/icons/Motorcycle';
import ReceiptIcon from '@material-ui/icons/Receipt';
import SearchIcon from '@material-ui/icons/Search';
import clsx from 'clsx';
import React, { useState } from 'react';
import { BrowserRouter as Router, Link, Route, Switch, useLocation } from 'react-router-dom';
import useNavBarStyles from '../styles/navBarSyles';
import About from './About';
import Accounting from './Accounting';
import Help from './Help';
import Home from './Home';
import { Inventory } from './inventory/Inventory';
import Planning from './Planning';
import Production from './Production';
import Purchase from './Purchasing';
import Sales from './Sales';
import UserAccount from './UserAccount';
import Tooltip from '@material-ui/core/Tooltip';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

const DelayedTooltip = ({ title, placement, ...props }) => {
  return (
    <Tooltip
      {...props}
      title={title}
      placement={placement}
      arrow
      enterDelay={650}
      TransitionComponent={Fade}
      TransitionProps={{ timeout: 250 }}
      disableFocusListener
      disableTouchListener
    >
      {props.children}
    </Tooltip>
  );
};

const DrawerItem = ({ className, Icon, link, text, selected, onClick }) => {
  const classes = useNavBarStyles();
  return (
    <Link to={link} className={className}>
      <DelayedTooltip title={text} placement='right'>
        <ListItem
          button
          selected={selected}
          onClick={onClick}
          className={clsx({ [classes.selected]: selected })}
        >
          <ListItemIcon>
            <Icon />
          </ListItemIcon>
          <ListItemText primary={text} />
        </ListItem>
      </DelayedTooltip>
    </Link>
  );
};

const DrawerList = ({ className }) => {
  const location = useLocation();
  return (
    <List>
      {
        [
          [HomeIcon, '/', 'Home'],
          [EventIcon, '/planning', 'Planning'],
          [MotorcycleIcon, '/production', 'Production'],
          [AttachMoneyIcon, '/sales', 'Sales'],
          [ReceiptIcon, '/purchasing', 'Purchasing'],
          [ListAltIcon, '/inventory', 'Inventory'],
          [AccountBalanceIcon, '/accounting', 'Accounting'],
          [HelpIcon, '/help', 'Help'],
          [InfoIcon, '/about', 'About']
        ].map(([icon, path, text], index) => (
          <DrawerItem
            Icon={icon}
            className={className}
            link={path}
            text={text}
            key={index}
            selected={location.pathname === path}
          />
        ))
      }
    </List>
  );
};

const ContentSwitch = () => {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/planning" component={Planning} />
      <Route exact path="/inventory" component={Inventory} />
      <Route exact path="/production" component={Production} />
      <Route exact path="/accounting" component={Accounting} />
      <Route exact path="/sales" component={Sales} />
      <Route exact path="/purchasing" component={Purchase} />
      <Route exact path="/help" component={Help} />
      <Route exact path="/about" component={About} />
      <Route exact path="/useraccount" component={UserAccount} />
    </Switch>
  );
};

const LogOutButton = () => {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };


  const LogOutDialogBox = () => {
    return (
      <Dialog open={open} onClose={handleClose} fullWidth maxWidth='sm'>
        <DialogTitle>Log Out</DialogTitle>
        <DialogContent>
          <DialogContentText>Do you want to log out of your account?</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
        </Button>
          <Button onClick={() => {
            handleClose();
          }} color="primary">
            Confirm Log Out
        </Button>
        </DialogActions>
      </Dialog>
    );
  };

  return (
    <div>
      <DelayedTooltip title="Logout">
        <IconButton onClick={handleClickOpen}>
          <ExitToAppIcon />
        </IconButton>
      </DelayedTooltip>
      <LogOutDialogBox />

    </div>
  );

};



function NavBar() {

  const classes = useNavBarStyles();
  const theme = useTheme();
  const [open, setOpen] = useState(false);

  const handleDrawerOpen = () => setOpen(true);
  const handleDrawerClose = () => setOpen(false);


  return (
    <div className={classes.root}>
      <Router on>
        <CssBaseline />
        <AppBar
          position="fixed"
          className={
            clsx(classes.appBar, { [classes.appBarShift]: open, })
          }
        >
          <Toolbar>
            <IconButton
              color='inherit'
              aria-label='open drawer'
              onClick={handleDrawerOpen}
              edge='start'
              className={
                clsx(classes.menuButton, { [classes.hide]: open, })
              }
            >
              <MenuIcon />
            </IconButton>
            <Typography variant='h6' noWrap>Godzilla ERP</Typography>
            <div className={classes.search}>
              <div className={classes.searchIcon}>
                <SearchIcon />
              </div>
              <InputBase
                placeholder="Search…"
                classes={{
                  root: classes.inputRoot,
                  input: classes.inputInput,
                }}
                inputProps={{ 'aria-label': 'search' }}
              />
            </div>
            <div style={{ marginLeft: 'auto' }}>
              <Link to="/useraccount" className={classes.link}>
                <DelayedTooltip title="User Account">
                  <IconButton>
                    <AccountCircleIcon />
                  </IconButton>
                </DelayedTooltip>
              </Link>
            </div>
            <LogOutButton />
          </Toolbar>
        </AppBar>
        <Drawer
          variant='permanent'
          className={clsx(classes.drawer, {
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open,
          })}
          classes={{
            paper: clsx({
              [classes.drawerOpen]: open,
              [classes.drawerClose]: !open,
            }),
          }}
        >
          <div className={classes.toolbar}>
            <IconButton onClick={handleDrawerClose}>
              {theme.direction === 'rtl' ? <DoubleArrowRoundedIcon /> : <ChevronLeft />}
            </IconButton>
          </div>
          <DrawerList className={classes.link} />
        </Drawer>
        <main className={classes.content} >
          <div className={classes.toolbar} />
          <ContentSwitch />
        </main>
      </Router>
    </div>
  );
}

export { DrawerList, NavBar, ContentSwitch, LogOutButton };
export default NavBar;
