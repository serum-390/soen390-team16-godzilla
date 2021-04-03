import { useMediaQuery, useTheme } from '@material-ui/core';
<<<<<<< HEAD
import { BrowserRouter as Router, Link, Route, Switch, useLocation } from 'react-router-dom';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import SignUp from './components/SignUp';

=======
import { ThemeProvider } from '@material-ui/core/styles';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import { ThemeContext, useThisTheme } from './styles/themes';
>>>>>>> b5701fdbb7efeb7407500c92c02cea71682dd5a2

function App() {

  const theme = useTheme();
  const desktop = useMediaQuery(theme.breakpoints.up('sm'));
  const [customTheme, darkMode, setDarkMode] = useThisTheme();

  const themeContextValue = {
    darkMode: darkMode,
    setDarkMode: setDarkMode
  };

  return (
<<<<<<< HEAD
    <div>
      {
        desktop ? <NavBar />
          : <NavBarMobile />
      }
    </div>
=======
    <ThemeContext.Provider value={themeContextValue}>
      <ThemeProvider theme={customTheme}>
        <div>{desktop ? <NavBar /> : <NavBarMobile />}</div>
      </ThemeProvider>
    </ThemeContext.Provider>
>>>>>>> b5701fdbb7efeb7407500c92c02cea71682dd5a2
  );
}

function AppRouter() {
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
