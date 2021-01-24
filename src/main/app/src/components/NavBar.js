import { AppBar, IconButton, Toolbar,Drawer, Button } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import Typography from '@material-ui/core/Typography';
import React, { useState } from 'react';


function NavBar() {

    const [open, setOpen] = useState(false)

    const handleDrawer = () => {
        setOpen(true)
    }

    return(
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
                <Button>Planning</Button>
            </div>
        </Drawer>

        </div>
    );
    
}

export default NavBar;