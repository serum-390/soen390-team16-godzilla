import { Button, Card, CardActionArea, CardActions, CardContent, CardMedia, Grid, makeStyles, Typography } from "@material-ui/core";
import DirectionsBikeIcon from '@material-ui/icons/DirectionsBike';
import BuildIcon from '@material-ui/icons/Build';
import { useState } from "react";

const useStyles = makeStyles({
  root: {
    width: 360
  },
  media: {
    height: 230,
  },
  typeIcon: {
    marginLeft: 'auto',
  },
})

const InventoryCard = props => {

  const classes = useStyles();
  const [name] = useState(props.name ? props.name : "No Name");
  const [description] = useState(props.description ? props.description : "No description...")

  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardMedia
          className={classes.media}
          image={props.image}
          title={props.name}
        />
        <CardContent>
          <Grid container>
            <Typography gutterBottom variant='h5' component='h2' className={classes.title}>{name}</Typography>
            <div className={classes.typeIcon}>
              {
                props.type === 'finished-product'
                  ? <DirectionsBikeIcon />
                  : <BuildIcon />
              }
            </div>
          </Grid>
          <Typography variant='body2' color='textSecondary' component='p'>
            {description}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size='small' color='primary'>Options</Button>
        <Button size='small' color='primary'>
          {props.type === 'finished-product' ? 'Sell' : 'Buy More'}
        </Button>
      </CardActions>
    </Card>
  )
};

export default InventoryCard;