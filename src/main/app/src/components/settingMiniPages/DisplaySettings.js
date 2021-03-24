import { CircularProgress, Grid, MenuItem, MenuList, useMediaQuery, useTheme } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';
import Switch from '@material-ui/core/Switch';
import useNavBarStyles from '../../styles/navBarSyles';
import ThemeContext from '..//../App'
import React, { useContext } from 'react';



function DisplaySettings() {

  const theme = useContext(ThemeContext);

  const toggleTheme = () => {

  };


  return (
    <div>
      <MenuList>
        <MenuItem  >
          <p>{JSON.stringify(theme)}</p>
          <Grid container spacing={0} justify='flex-end' >
            <Grid item key={0} style={{ padding: useTheme().spacing(1, 0) }}>
              <Switch />
            </Grid>
          </Grid>
        </MenuItem>
      </MenuList>
    </div >
  );
}

export default DisplaySettings;