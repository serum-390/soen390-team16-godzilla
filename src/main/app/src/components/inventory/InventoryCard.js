import { Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Grid, makeStyles, Typography } from "@material-ui/core";
import { useState } from "react";

const useStyles = makeStyles(theme => ({
  root: {
    width: '98vw',
    [theme.breakpoints.up('sm')]: {
      width: 300,
    }
  },
  media: {
    height: 200,
    [theme.breakpoints.down('xs')]: {
      height: '30vh',
    },
  },
  typeIcon: {
    marginLeft: 'auto',
  },
}));

const InventoryCard = props => {

  const classes = useStyles();
  const [itemName] = useState(props.itemName ? props.itemName : "No Name");
  const [goodType] = useState(props.goodType ? props.goodType : "No type...");
  const [sellPrice] = useState(props.sellPrice ? props.sellPrice : "No price");
  const [buyPrice] = useState(props.buyPrice ? props.buyPrice : "No price...");
  const [location] = useState(props.location ? props.location : "No location");
  const [quantity] = useState(props.quantity ? props.quantity : "No quantity...");
  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardMedia
          className={classes.media}
          title={props.itemName}
        />
        <CardContent>
          <Grid container>
            <Typography
              gutterBottom
              variant='h5'
              component='h2'
              className={classes.title}
            >
              {itemName}
            </Typography>
          </Grid>
          <Typography variant='body2' color='textSecondary' component='p'>
            {sellPrice}
          </Typography>
          <Typography variant='body2' color='textSecondary' component='p'>
            {buyPrice}
          </Typography>
          <Typography variant='body2' color='textSecondary' component='p'>
            {quantity}
          </Typography>
          <Typography variant='body2' color='textSecondary' component='p'>
            {goodType}
          </Typography>
          <Typography variant='body2' color='textSecondary' component='p'>
            {location}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size='small' color='primary'>Options</Button>
      </CardActions>
    </Card>
  )
};

export default InventoryCard;