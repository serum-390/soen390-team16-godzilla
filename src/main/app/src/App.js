import { useMediaQuery, useTheme } from '@material-ui/core';
import { BrowserRouter as Router, Link, Route, Switch, useLocation } from 'react-router-dom';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import SignUp from './components/SignUp';


function App() {

  const theme = useTheme();
  const desktop = useMediaQuery(theme.breakpoints.up('sm'));

  return (
    <div>
      {
        desktop ? <NavBar />
                : <NavBarMobile />
      }
    </div>
  );
}

function AppRouter(){
  return (
    <Router on >
      <Switch>
       <Route exact path="/" component={App} />
      <Route exact path="/signup" component={SignUp} />
      </Switch>
    </Router>
  )


}

export default AppRouter;
