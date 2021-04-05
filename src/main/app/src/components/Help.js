import React from 'react';
import { BrowserRouter as Router, Route, Link, Switch, useLocation } from 'react-router-dom';
import MenuItem from '@material-ui/core/MenuItem';
import MenuList from '@material-ui/core/MenuList';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import AccountingHelp from './helpMiniPages/AccountingHelp';
import InventoryHelp from './helpMiniPages/InventoryHelp';
import NavBarHelp from './helpMiniPages/NavBarHelp';
import PlanningHelp from './helpMiniPages/PlanningHelp';
import ProductionHelp from './helpMiniPages/ProductionHelp';
import PurchasingHelp from './helpMiniPages/PurchasingHelp';
import SalesHelp from './helpMiniPages/SalesHelp';
import UserManagementHelp from './helpMiniPages/UserManagementHelp';



const useStyles = makeStyles((theme) => ({
  menu: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(30),
      height: theme.spacing(40),
    },
  },
  mainDisplay: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      width: theme.spacing(120),
      height: theme.spacing(100),
      padding: theme.spacing(3),
    },
  },
  link: {
    textDecoration: 'none',
    color: theme.palette.text.primary,
  },
}));


const HelpMenu = () => {
  const classes = useStyles();
  const location = useLocation();
  return (
    <div className={classes.menu}>
      <Paper elevation={3} >
        <MenuList>
          <Link to='/help/Accounting'
            selected={location.pathname === '/help/Accounting'}
            className={classes.link}
          >
            <MenuItem>Accounting</MenuItem>
          </Link>
          <Link to='/help/Inventory'
            selected={location.pathname === '/help/Inventory'}
            className={classes.link}
          >
            <MenuItem>Inventory</MenuItem>
          </Link>
          <Link to='/help/Navbar'
            selected={location.pathname === '/help/Navbar'}
            className={classes.link}
          >
            <MenuItem>Navigation Bar</MenuItem>
          </Link>
          <Link to='/help/Planning'
            selected={location.pathname === '/help/Planning'}
            className={classes.link}
          >
            <MenuItem>Planning</MenuItem>
          </Link>
          <Link to='/help/Production'
            selected={location.pathname === '/help/Production'}
            className={classes.link}
          >
            <MenuItem>Production</MenuItem>
          </Link>
          <Link to='/help/Purchasing'
            selected={location.pathname === '/help/Purchasing'}
            className={classes.link}
          >
            <MenuItem>Purchasing</MenuItem>
          </Link>
          <Link to='/help/Sales'
            selected={location.pathname === '/help/Sales'}
            className={classes.link}
          >
            <MenuItem>Sales</MenuItem>
          </Link>
          <Link to='/help/UserManagement'
            selected={location.pathname === '/help/UserManagement'}
            className={classes.link}
          >
            <MenuItem>User Management</MenuItem>
          </Link>
        </MenuList>
      </Paper >
    </div>
  );
};

const HelpDisplay = () => {
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
      <Route exact path="/help/Accounting" component={AccountingHelp} />
      <Route exact path="/help/Inventory" component={InventoryHelp} />
      <Route exact path="/help/Navbar" component={NavBarHelp} />
      <Route exact path="/help/Planning" component={PlanningHelp} />
      <Route exact path="/help/Production" component={ProductionHelp} />
      <Route exact path="/help/Purchasing" component={PurchasingHelp} />
      <Route exact path="/help/Sales" component={SalesHelp} />
      <Route exact path="/help/UserManagement" component={UserManagementHelp} />
    </Switch>
  );
};


function Help() {
  return (
    <div>
      <Router on>
        <Grid container spacing={7}>
          <Grid item>
            <HelpMenu />
          </Grid>
          <Grid item>
            <HelpDisplay />
          </Grid>
        </Grid>
      </Router>
    </div>

  );
}

export default Help;