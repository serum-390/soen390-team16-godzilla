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
      minWidth: theme.spacing(30),
      borderRadius: '1em',
    },
  },
  mainDisplay: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: theme.spacing(1),
      maxWidth: theme.spacing(120),
      padding: theme.spacing(3),
      borderRadius: '1em',
    },
  },
  link: {
    textDecoration: 'none',
    color: theme.palette.text.primary,
  },
  orderImg: {
    width: '13%',
    resizeMode: "contain"
  }
}));


const HelpMenu = () => {
  const classes = useStyles();
  const location = useLocation();
  return (
    <div className={classes.menu}>
      <Paper variant="outlined" >
        <MenuList>
          <Link to='/help/accounting'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/accounting' || location.pathname === '/help'}>
              Accounting
            </MenuItem>
          </Link>
          <Link to='/help/inventory'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/inventory'}>
              Inventory
            </MenuItem>
          </Link>
          <Link to='/help/navbar'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/navbar'}>
              Navigation Bar
            </MenuItem>
          </Link>
          <Link to='/help/planning'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/planning'}>
              Planning
            </MenuItem>
          </Link>
          <Link to='/help/production'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/production'}>
              Production
            </MenuItem>
          </Link>
          <Link to='/help/purchasing'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/purchasing'}>
              Purchasing
            </MenuItem>
          </Link>
          <Link to='/help/sales'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/sales'}>
              Sales
            </MenuItem>
          </Link>
          <Link to='/help/user-management'
            className={classes.link}
          >
            <MenuItem selected={location.pathname === '/help/user-management'}>
              User Management
            </MenuItem>
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
      <Paper variant="outlined">
        <ContentSwitch />
      </Paper >
    </div>
  );
};

const ContentSwitch = () => {
  return (
    <Switch>
      <Route exact path="/help" component={AccountingHelp} />
      <Route exact path="/help/accounting" component={AccountingHelp} />
      <Route exact path="/help/inventory" component={InventoryHelp} />
      <Route exact path="/help/navbar" component={NavBarHelp} />
      <Route exact path="/help/planning" component={PlanningHelp} />
      <Route exact path="/help/production" component={ProductionHelp} />
      <Route exact path="/help/purchasing" component={PurchasingHelp} />
      <Route exact path="/help/sales" component={SalesHelp} />
      <Route exact path="/help/user-management" component={UserManagementHelp} />
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
export { Help, useStyles };
export default Help;