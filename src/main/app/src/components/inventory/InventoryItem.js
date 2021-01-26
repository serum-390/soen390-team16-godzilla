import { Container, Divider, makeStyles, Paper } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  papier: {
    margin: theme.spacing(3),
    width: theme.spacing(18),
    height: theme.spacing(23),
    '& > *': {
      textAlign: 'center'
    },
  },
  image: {
    height: theme.spacing(19)
  },
  name: {
    height: theme.spacing(3)
  },
}));

const InventoryItem = () => {
  const classes = useStyles();
  return (
    <Paper className={classes.papier} elevation={3}>
      <Container className={classes.image}>
        TOP
      </Container>
      <Divider></Divider>
      <Container className={classes.name}>
        ITEM #1
      </Container>
    </Paper>
  );
}


export default InventoryItem;
