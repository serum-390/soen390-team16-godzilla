import { AppBar, CssBaseline, Drawer, IconButton, List, ListItemIcon, Toolbar } from '@material-ui/core';
import InputBase from '@material-ui/core/InputBase';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { fade, makeStyles, useTheme } from "@material-ui/core/styles";
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
import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import About from '../pages/About';
import Accounting from '../pages/Accounting';
import Help from '../pages/Help';
import Home from '../pages/Home';
import { Inventory } from '../pages/Inventory';
import Planning from '../pages/Planning';
import Production from '../pages/Production';
import Purchase from '../pages/Purchase';
import Sales from '../pages/Sales';
import UserAccount from '../pages/UserAccount';

const drawerWidth = 200;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
  },
  drawerOpen: {
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing(6) + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(7) + 3,
    }
  },
  toolbar: {
    backgroundColor: theme.palette.primary,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  link: {
    textDecoration: 'none',
    color: theme.palette.text.primary
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    margin: 'auto',
    width: '44%',
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '20ch',
    },
  },
}));


const DrawerItem = ({ cls, Icon, link, text }) => {
  return (
    <Link to={link} className={cls}>
      <ListItem button>
        <ListItemIcon>
          <Icon />
        </ListItemIcon>
        <ListItemText primary={text} />
      </ListItem>
    </Link>
  );
};

const DrawerList = ({ drawerItemClass }) => {
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
        ].map(([Icon, path, text]) => (
          <DrawerItem
            Icon={Icon}
            cls={drawerItemClass}
            link={path}
            text={text}
          />
        ))
      }
    </List>
  );
};

function NavBar() {

  const classes = useStyles();
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
            <Typography variant='h6' noWrap>Muto ERP</Typography>
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
                <IconButton>
                  <AccountCircleIcon />
                </IconButton>
              </Link>
              <IconButton>
                <ExitToAppIcon />
              </IconButton>
            </div>
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
          <DrawerList drawerItemClass={classes.link} />
        </Drawer>
        <main className={classes.content} >
          <div className={classes.toolbar} />
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
        </main>
      </Router>
    </div>
  );
}

export default NavBar;
