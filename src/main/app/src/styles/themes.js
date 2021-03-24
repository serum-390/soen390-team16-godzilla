import { useMemo } from 'react'
import { createMuiTheme } from '@material-ui/core/styles';
import React from 'react'

const darkTheme = createMuiTheme({
  palette: {
    type: 'dark',
  },
});

const lightTheme = createMuiTheme({
  palette: {
    type: 'light',
  },
});

const ThemeContext = React.createContext({}).Provider;

function useThisTheme() {

  let darkMode = false;

  function setDarkMode(on) {
    darkMode = on;
  }

  const theme = useMemo(() => darkMode ? darkTheme : lightTheme, [darkMode]);

  return [theme, darkMode, setDarkMode];
}

export { ThemeContext, useThisTheme };
export default useThisTheme;