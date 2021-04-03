import { useMediaQuery, useTheme } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/core/styles';
import { Route, Router, Switch } from 'react-router';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import SignUp from './components/SignUp';
import { ThemeContext, useThisTheme } from './styles/themes';

function App() {

  const theme = useTheme();
  const desktop = useMediaQuery(theme.breakpoints.up('sm'));
  const [customTheme, darkMode, setDarkMode] = useThisTheme();

  const themeContextValue = {
    darkMode: darkMode,
    setDarkMode: setDarkMode
  };

  return (
    <ThemeContext.Provider value={themeContextValue}>
      <ThemeProvider theme={customTheme}>
        <div>{desktop ? <NavBar /> : <NavBarMobile />}</div>
      </ThemeProvider>
    </ThemeContext.Provider>
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
