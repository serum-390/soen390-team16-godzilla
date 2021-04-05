import { AppBar, Button, CssBaseline, Dialog, DialogContentText, Drawer, Fade, Grid, IconButton, List, ListItemIcon, Toolbar } from '@material-ui/core';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import InputBase from '@material-ui/core/InputBase';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { useTheme } from "@material-ui/core/styles";
import Tooltip from '@material-ui/core/Tooltip';
import { ChevronLeft } from '@material-ui/icons';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import Brightness4OutlinedIcon from '@material-ui/icons/Brightness4Outlined';
import Brightness7OutlinedIcon from '@material-ui/icons/Brightness7Outlined';
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
import SettingsIcon from '@material-ui/icons/Settings';
import clsx from 'clsx';
import React, { useContext } from 'react';
import { BrowserRouter as Router, Link, Route, Switch, useLocation } from 'react-router-dom';
import useNavBarStyles from '../styles/navBarSyles';
import { ThemeContext } from '../styles/themes';
import useBooleanState from '../util/hooks';
import About from './About';
import Accounting from './Accounting';
import Help from './Help';
import Home from './Home';
import { Inventory } from './inventory/Inventory';
import Shipping from './shipping/Shipping';
import Production from './Production';
import Purchase from './Purchasing';
import Sales from './Sales';
import SettingsPage from './SettingsPage';
import UserAccount from './UserAccount';

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
          [EventIcon, '/shipping', 'Shipping'],
          [MotorcycleIcon, '/production', 'Production'],
          [AttachMoneyIcon, '/sales', 'Sales'],
          [ReceiptIcon, '/purchasing', 'Purchasing'],
          [ListAltIcon, '/inventory', 'Inventory'],
          [AccountBalanceIcon, '/accounting', 'Accounting'],
          [HelpIcon, '/help', 'Help'],
          [InfoIcon, '/about', 'About'],
          [SettingsIcon, '/settings', 'Settings']
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
      <Route exact path="/settings" component={SettingsPage} />
    </Switch>
  );
};

const LogOutButton = () => {

  const [open, handleOpen, handleClose] = useBooleanState(false);

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
          <Button
            color="primary"
            onClick={() => {
              handleClose();
              Logout();
            }}
          >
            Confirm Log Out
        </Button>
        </DialogActions>
      </Dialog>
    );
  };

  return (
    <div style={{ color: 'inherit', }}>
      <DelayedTooltip title="Logout">
        <IconButton
          color='inherit'
          aria-label='logout'
          onClick={handleOpen}
        >
          <ExitToAppIcon />
        </IconButton>
      </DelayedTooltip>
      <LogOutDialogBox />
    </div >
  );
};

const UserAccountButton = ({ className, ...props }) => {
  return (
    <Link to="/useraccount" className={className} style={{ color: 'inherit' }}>
      <DelayedTooltip title="User Account">
        <IconButton
          color='inherit'
          aria-label='user account'
          {...props}
        >
          <AccountCircleIcon />
        </IconButton>
      </DelayedTooltip>
    </Link>
  );
};

const SearchBar = ({ classes, searchBarFocused, focusSearchBar, unfocusSearchBar }) => {
  return (
    <div
      className={clsx(classes.search, {
        [classes.searchBarFocused]: searchBarFocused,
      })}
      onFocus={focusSearchBar}
      onBlur={unfocusSearchBar}
    >
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
  );
};

function DarkModeToggle({ ...props }) {
  const ctx = useContext(ThemeContext);
  const toggleDarkMode = () => ctx.setDarkMode(!ctx.darkMode);
  return (
    <Grid item style={{ marginLeft: '1em', }} {...props} >
      <DelayedTooltip title="Toggle Dark Mode">
        <IconButton
          color='inherit'
          aria-label='toggle darkmode'
          onClick={toggleDarkMode}
        >
          {ctx.darkMode
            ? <Brightness4OutlinedIcon />
            : <Brightness7OutlinedIcon />}
        </IconButton>
      </DelayedTooltip>
    </Grid>
  );
}

function NavBar() {

  const classes = useNavBarStyles();
  const theme = useTheme();
  const [open, handleDrawerOpen, handleDrawerClose] = useBooleanState(false);
  const [searchBarFocused, focusSearchBar, unfocusSearchBar] = useBooleanState(false);

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
              className={clsx(classes.menuButton, { [classes.hide]: open })}
            >
              <MenuIcon />
            </IconButton>
            <img src="/resources/images/name-logo-min.png"
              className={classes.nameLogo}
              alt="Godzilla ERP"
            />
            <Grid container spacing={0} justify='flex-end' >
              <Grid item key={0} style={{ padding: useTheme().spacing(1, 0) }}>
                <SearchBar
                  classes={classes}
                  searchBarFocused={searchBarFocused}
                  focusSearchBar={focusSearchBar}
                  unfocusSearchBar={unfocusSearchBar}
                />
              </Grid>
              <DarkModeToggle key={1} />
              <Grid item key={2}>
                <UserAccountButton className={classes.link} />
              </Grid>
              <Grid item key={3}>
                <LogOutButton />
              </Grid>
            </Grid>
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

function Logout() {
  alert('Logged out');
}

export { DrawerList, NavBar, ContentSwitch, LogOutButton };
export default NavBar;
