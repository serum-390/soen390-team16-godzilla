import { AppBar, IconButton, Toolbar,Drawer, List, Divider, ListItemIcon } from '@material-ui/core';
import {BrowserRouter as Router, Switch, Route, Link} from 'react-router-dom';
import React, { useState } from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles } from "@material-ui/core/styles"

import MenuIcon from '@material-ui/icons/Menu';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import EventIcon from '@material-ui/icons/Event';
import ReceiptIcon from '@material-ui/icons/Receipt';
import Typography from '@material-ui/core/Typography';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import MotorcycleIcon from '@material-ui/icons/Motorcycle';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import InfoIcon from '@material-ui/icons/Info';
import HelpIcon from '@material-ui/icons/Help';
import HomeIcon from '@material-ui/icons/Home';
import ListAltIcon from '@material-ui/icons/ListAlt';

import Planning from '../pages/Planning';
import Home from '../pages/Home'
import Inventory from '../pages/Inventory';
import Production from '../pages/Production';
import Accounting from '../pages/Accounting';
import Sales from '../pages/Sales';
import Purchase from '../pages/Purchase';
import Help from '../pages/Help';
import About from '../pages/About';


const useStyles = makeStyles((theme) => ({
    link: {
      textDecoration: 'none',
      color: theme.palette.text.primary
    }
  }))

function NavBar() {

    const [open, setOpen] = useState(false)

    const handleDrawer = () => {
        setOpen(true)
    }

    

      const classes = useStyles();
    return(
        <Router>
        <div>
            <AppBar position="static">
             <Toolbar>
                <IconButton onClick={handleDrawer}>
                    <MenuIcon />
                </IconButton>
                <Typography style = {{flexGrow : 1}}>
                MUTO FrontEnd Application
                </Typography>
                <IconButton>
                    <AccountCircleIcon />
                </IconButton>
                <IconButton>
                    <ExitToAppIcon />
                </IconButton>
            </Toolbar>
            </AppBar>

        <Drawer
        anchor='left'
        open ={open}
        onClose ={() => setOpen(false)}
        >
            <div style={{height : "100%" , width : "250px"}}>
                <h2>Navigation Pane</h2>
                <List>
                    <Divider/>
                    <Link to="/" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <HomeIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Home"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Planning" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <EventIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Planning"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Production" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <MotorcycleIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Production"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Sales" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <AttachMoneyIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Sales Department"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Purchase" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <ReceiptIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Purchase Department"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Inventory" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <ListAltIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Inventory"} />
                    </ListItem>
                    </Link >
                    <Divider/>
                    <Link to="/Accounting" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <AccountBalanceIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Accounting"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Help" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <HelpIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Help"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/About" className={classes.link}>
                    <ListItem button>
                        <ListItemIcon>
                            <InfoIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"About Us"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                </List>
            </div>
        </Drawer>

        <Switch>
            <Route exact path="/" component={Home}/>Inventory
            <Route exact path="/Planning" component={Planning}/>
            <Route exact path="/Inventory" component={Inventory}/>
            <Route exact path="/Production" component={Production}/>
            <Route exact path="/Accounting" component={Accounting}/>
            <Route exact path="/Sales" component={Sales}/>
            <Route exact path="/Purchase" component={Purchase}/>
            <Route exact path="/Help" component={Help}/>
            <Route exact path="/About" component={About}/>
        </Switch>
        </div>
        </Router>
    );
}

export default NavBar;