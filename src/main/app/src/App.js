import { useMediaQuery, useTheme } from '@material-ui/core';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import SignUp from './components/SignUp';


function App() {

  const theme = useTheme();
  const desktop = useMediaQuery(theme.breakpoints.up('sm'));

  return (
    <div>
      {/* {
        desktop ? <NavBar />
                : <NavBarMobile />
      } */
        <SignUp />
      }
    </div>
  );
}

export default App;
