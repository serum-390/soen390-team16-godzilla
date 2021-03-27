import { createMuiTheme } from '@material-ui/core/styles';
import React, { useMemo, useState } from 'react';

const ThemeContext = React.createContext({});

function useThisTheme() {
  const [darkMode, setDarkMode] = useState(false);
  const theme = useMemo(
    () =>
      createMuiTheme({
        palette: {
          type: darkMode ? 'dark' : 'light',
          primary: darkMode ? {
            light: '#484848',
            main: '#212121',
            dark: '#000000',
            contrastText: '#fff',
          } :
            {
              light: '#9162e4',
              main: '#5e35b1',
              dark: '#280680',
              contrastText: '#fff',
            },
        },
      }),
    [darkMode]);
  return [theme, darkMode, setDarkMode];
}

export default useThisTheme;
export { ThemeContext, useThisTheme };
