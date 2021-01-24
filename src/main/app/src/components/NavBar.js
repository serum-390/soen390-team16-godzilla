import { AppBar, IconButton, Toolbar,Drawer, List, Divider, ListItemIcon } from '@material-ui/core';
import {BrowserRouter as Router, Switch, Route, Link} from 'react-router-dom';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
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
import React, { useState } from 'react';
import Planning from '../pages/Planning';
import Home from '../pages/Home'


function NavBar() {

    const [open, setOpen] = useState(false)

    const handleDrawer = () => {
        setOpen(true)
    }

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
                    <Link to="/" >
                    <ListItem button>
                        <ListItemIcon>
                            <HomeIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Home"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <Link to="/Planning" >
                    <ListItem button>
                        <ListItemIcon>
                            <EventIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Planning"} />
                    </ListItem>
                    </Link>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <MotorcycleIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Production"} />
                    </ListItem>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <AttachMoneyIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Sales Department"} />
                    </ListItem>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <ReceiptIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Purchase Department"} />
                    </ListItem>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <AccountBalanceIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Accounting"} />
                    </ListItem>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <HelpIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"Help"} />
                    </ListItem>
                    <Divider/>
                    <ListItem button>
                        <ListItemIcon>
                            <InfoIcon/>
                        </ListItemIcon>
                        <ListItemText primary={"About Us"} />
                    </ListItem>
                    <Divider/>
                </List>
            </div>
        </Drawer>

        <Switch>
            <Route exact path="/" component={Home}/>
            <Route exact path="/Planning" component={Planning}/>
        </Switch>
        </div>
        </Router>
    );
}

export default NavBar;