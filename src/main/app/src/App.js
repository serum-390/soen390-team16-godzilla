import { useMediaQuery, useTheme } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/core/styles';
import { Route, Switch } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import { Signup, Login } from './components/Authentication';
import NavBar from './components/NavBar';
import NavBarMobile from './components/NavBarMobile';
import { ThemeContext, useThisTheme } from './styles/themes';

const App = () => {
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
};

const AppRouter = () =>
  <BrowserRouter>
    <Switch>
      <Route path="/signup" component={Signup} />
      <Route path="/login" component={Login} />
      <Route path="/" component={App} />
    </Switch>
  </BrowserRouter>;

export default AppRouter;
