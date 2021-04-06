import { Box, CircularProgress, useMediaQuery, useTheme } from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import AboutUsPara from "./markDown/AboutUsPara"

const spinnyBoi = (
  <Box
    display='flex'
    justifyContent='center'
    style={{ paddingTop: '38vh' }}
  >
    <CircularProgress color='secondary' />
  </Box>
);

const useStyles = makeStyles((theme) => ({
  menu: {
    display: 'flex',
    flexWrap: 'wrap',
    '& > *': {
      margin: 'auto',
      maxWidth: theme.spacing(120),
      padding: theme.spacing(3),
      textAlign: 'left',
      borderRadius: '1em',

    },
    justifyItems: 'center',
  },
  div: {
    textAlign: 'left'
  }
}));

function About() {
  const classes = useStyles();

  const theme = useTheme();
  const desktop = useMediaQuery(theme.breakpoints.up('sm'));

  const DesktopMarkDown = () => { //Desktop Version of the About Us Markdown Page
    return (
      <div className={classes.menu}>
        <Paper variant="outlined" >
          <div className={classes.div} >
            <AboutUsPara />
          </div>
        </Paper>
      </div>
    );
  }

  return (
    <div>
      {
        desktop ? <DesktopMarkDown />
          : <AboutUsPara />
      }
    </div>
  );
}

export { About, spinnyBoi };
export default About;