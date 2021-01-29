import { AppBar, CssBaseline, Divider, Drawer, IconButton, List, ListItemIcon, Toolbar } from '@material-ui/core';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles, useTheme } from "@material-ui/core/styles";
import Typography from '@material-ui/core/Typography';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import EventIcon from '@material-ui/icons/Event';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import HelpIcon from '@material-ui/icons/Help';
import HomeIcon from '@material-ui/icons/Home';
import InfoIcon from '@material-ui/icons/Info';
import ListAltIcon from '@material-ui/icons/ListAlt';
import MenuIcon from '@material-ui/icons/Menu';
import MotorcycleIcon from '@material-ui/icons/Motorcycle';
import ReceiptIcon from '@material-ui/icons/Receipt';
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
import clsx from 'clsx';

const drawerWidth = 240;

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
    width: `calc(100% -${drawerWidth}px)`,
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
    transition: theme.transitions.create(['width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerClose: {
    transition: theme.transitions.create(['width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: theme.spacing(7) + 1,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9) + 1,
    }
  },
  toolbar: {
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
  }
}));

function NavBar() {

  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = useState(false);

  const handleDrawerOpen = () => setOpen(true);
  const handleDrawerClose = () => setOpen(false);

  return (
    <Router on>
      <div className={classes.root}>
        <CssBaseline />
        <AppBar
          position="fixed"
          className={
            // `clsx` is used to conditionally apply a className to an element in React
            clsx(classes.appBar, { [classes.appBarShift]: open, })
          }
        >
          <Toolbar>
            <IconButton onClick={handleDrawerOpen}>
              <MenuIcon />
            </IconButton>
            <Typography style={{ flexGrow: 1 }}>
              MUTO FrontEnd Application
                </Typography>
            <Link to="/UserAccount" className={classes.link}>
              <IconButton>
                <AccountCircleIcon />
              </IconButton>
            </Link>
            <IconButton>
              <ExitToAppIcon />
            </IconButton>
          </Toolbar>
        </AppBar>

        <Drawer
          anchor='left'
          open={open}
          onClose={() => setOpen(false)}
        >
          <div style={{ height: "100%", width: "250px" }}>
            <h2 style={{ padding: '0.75em' }}>Navigation Pane</h2>
            <List>
              <Divider />
              <Link to="/" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <HomeIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Home"} />
                </ListItem>
              </Link>
              <Link to="/Planning" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <EventIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Planning"} />
                </ListItem>
              </Link>
              <Link to="/Production" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <MotorcycleIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Production"} />
                </ListItem>
              </Link>
              <Link to="/Sales" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <AttachMoneyIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Sales"} />
                </ListItem>
              </Link>
              <Link to="/Purchase" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <ReceiptIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Purchasing"} />
                </ListItem>
              </Link>
              <Link to="/Inventory" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <ListAltIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Inventory"} />
                </ListItem>
              </Link >
              <Link to="/Accounting" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <AccountBalanceIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Accounting"} />
                </ListItem>
              </Link>
              <Link to="/Help" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <HelpIcon />
                  </ListItemIcon>
                  <ListItemText primary={"Help"} />
                </ListItem>
              </Link>
              <Link to="/About" className={classes.link}>
                <ListItem button>
                  <ListItemIcon>
                    <InfoIcon />
                  </ListItemIcon>
                  <ListItemText primary={"About Us"} />
                </ListItem>
              </Link>
            </List>
          </div>
        </Drawer>

        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/Planning" component={Planning} />
          <Route exact path="/Inventory" component={Inventory} />
          <Route exact path="/Production" component={Production} />
          <Route exact path="/Accounting" component={Accounting} />
          <Route exact path="/Sales" component={Sales} />
          <Route exact path="/Purchase" component={Purchase} />
          <Route exact path="/Help" component={Help} />
          <Route exact path="/About" component={About} />
          <Route exact path="/UserAccount" component={UserAccount} />
        </Switch>
      </div>
    </Router>
  );
}

export default NavBar;
