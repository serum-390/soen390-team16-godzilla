import { createMuiTheme } from '@material-ui/core/styles';
import React, { useMemo, useState } from 'react';

const ThemeContext = React.createContext({});

const darkPalette = {
  light: '#484848',
  main: '#212121',
  dark: '#000000',
  contrastText: '#fff',
};
const lightPalette = {
  light: '#9162e4',
  main: '#1a237e',
  dark: '#280680',
  contrastText: '#fff',
};

function useThisTheme() {
  const [darkMode, setDarkMode] = useState(false);

  const theme = useMemo(
    () =>
      createMuiTheme({
        palette: {
          type: darkMode ? 'dark' : 'light',
          primary: darkMode ? darkPalette : lightPalette,
        },
      }),
    [darkMode]);
  return [theme, darkMode, setDarkMode];
}

export default useThisTheme;
export { ThemeContext, useThisTheme };
