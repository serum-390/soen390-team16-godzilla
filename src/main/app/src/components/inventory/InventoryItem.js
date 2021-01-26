import { Container, Divider, makeStyles, Paper } from "@material-ui/core";
import PropTypes from 'prop-types';

const useStyles = makeStyles(theme => ({
  papier: {
    display: 'flex',
    flexDirection: "column",
    justifyContent: "center",
    margin: theme.spacing(3),
    padding: theme.spacing(1),
    width: theme.spacing(18),
    height: theme.spacing(21),
    '& > *': {
      textAlign: 'center',
    },
    '& img': {
      maxHeight: theme.spacing(19),
      maxWidth: theme.spacing(18),
      height: 'auto',
      width: 'auto',
    },
  },
  image: {
    height: theme.spacing(19),
    width: theme.spacing(18),
    padding: 0,
  },
  name: {
    padding: theme.spacing(1),
    height: theme.spacing(5),
  },
}));

const InventoryItem = props => {
  const classes = useStyles();
  return (
    <Paper className={classes.papier} elevation={3}>
      <Container className={classes.image}
      justify="center">
        <img src={props.image_url ? props.image_url : '/Inventory'}
             alt='NOT FOUND'
        />
      </Container>
      <Divider></Divider>
      <Container className={classes.name}>
        {props.name ? props.name : 'NO NAME'}
      </Container>
    </Paper>
  );
}

// Adding some requried properties to the component
InventoryItem.propTypes = {
  name: PropTypes.string,
  image_url: PropTypes.string
}

export default InventoryItem;
