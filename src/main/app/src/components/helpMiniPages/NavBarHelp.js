import navBarImage from "../../misc/helpPageImages/navBar.png";
import Brightness4OutlinedIcon from '@material-ui/icons/Brightness4Outlined';
import { IconButton } from "@material-ui/core";

function NavBarHelp() {
  return (
    <div>
      <h1>Navigation Bar</h1>
      <h4>The Navigational Toolbar, aka the Navbar, is used to navigate between the primary pages of the application. To access a page , simply select the desired icon in the drop down bar on the left side of the screen. Hovering the cursor over an icon will provide the name of the linked page </h4>
      <img src={navBarImage} alt="img" style={{ width: '15%', height: '15%', resizeMode: 'contain' }} />
      <h4>Selecting the drawer Icon on the top left corner also provides a slightly detailed view of the navbar componenets</h4>
      <h2>Dark Mode</h2>
      <IconButton>
        <Brightness4OutlinedIcon />
      </IconButton>
      <h4>A dark and light theme combination is available for the application, and can be toggled by selecting the dark mode icon on the top right of the navbar </h4>


    </div>
  );
}

export default NavBarHelp;