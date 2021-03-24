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
          },
        }),
      [darkMode]);
  return [theme, darkMode, setDarkMode];
}

export default useThisTheme;
export { ThemeContext, useThisTheme };
