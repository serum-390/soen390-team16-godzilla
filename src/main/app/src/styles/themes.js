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
            main: '#757575',
            dark: '#000000',
            contrastText: '#fff',
          } :
            {
              light: '#ffffff',
              main: '#fafafa',
              dark: '#c7c7c7',
              contrastText: '#000',
            },
        },
      }),
    [darkMode]);
  return [theme, darkMode, setDarkMode];
}

export default useThisTheme;
export { ThemeContext, useThisTheme };
