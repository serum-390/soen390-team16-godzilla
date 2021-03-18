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
      margin: theme.spacing(1),
      width: theme.spacing(120),
      height: theme.spacing(105),
      padding: theme.spacing(3),
      textAlign: 'left'
    },
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
        <Paper p={2} m={2} pt={3} >
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