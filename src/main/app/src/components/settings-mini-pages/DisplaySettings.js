import { Grid, MenuItem, MenuList, Switch, useTheme } from "@material-ui/core";
import React, { useContext } from 'react';
import { ThemeContext } from "../../styles/themes";

function DisplaySettings() {

  const ctx = useContext(ThemeContext);
  const handleDarkMode = () => ctx.setDarkMode(!ctx.darkMode);

  return (
    <div>
      <MenuList>
        <MenuItem  >
          <p>Dark Mode</p>
          <Grid container spacing={0} justify='flex-end' >
            <Grid item key={0} style={{ padding: useTheme().spacing(1, 0) }}>
              <Switch onChange={handleDarkMode} value={ctx.darkMode} />
            </Grid>
          </Grid>
        </MenuItem>
      </MenuList>
    </div >
  );
}

export default DisplaySettings;