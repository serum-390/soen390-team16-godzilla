import { AppBar, Drawer, IconButton, Toolbar } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import MenuIcon from '@material-ui/icons/Menu';
import React, { useState } from 'react';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { ContentSwitch, DrawerList, LogOutButton } from './NavBar';
import useNavBarStyles from '../styles/navBarSyles';



function NavBarMobile() {

  const classes = useNavBarStyles();
  const [open, setOpen] = useState(false)

  const handleDrawer = () => {
    setOpen(true)
  }

  return (
    <Router on>
      <div>
        <AppBar position="sticky">
          <Toolbar>
            <IconButton onClick={handleDrawer}>
              <MenuIcon />
            </IconButton>
            <Typography style={{ flexGrow: 1 }}>
              Godzilla ERP
            </Typography>
            <Link to="/UserAccount" className={classes.link}>
              <IconButton>
                <AccountCircleIcon />
              </IconButton>
            </Link>
            <LogOutButton />
          </Toolbar>
        </AppBar>
        <Drawer
          anchor='left'
          open={open}
          onClose={() => setOpen(false)}
        >
          <div style={{ height: "100%", width: "250px" }}>
            <DrawerList className={classes.link} />
          </div>
        </Drawer>
        <ContentSwitch />
      </div>
    </Router>
  );
}

export default NavBarMobile;
