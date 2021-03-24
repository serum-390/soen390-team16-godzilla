import { Grid, MenuItem, MenuList, useTheme } from "@material-ui/core";
import Switch from '@material-ui/core/Switch';
import React, { useContext } from 'react';
import ThemeContext from '../../styles/themes'



function DisplaySettings() {

  const ctx = useContext(ThemeContext);

  console.log(ctx);

  const handleDarkMode = () => {
    ctx.setDarkMode(!ctx.darkMode);
  }

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