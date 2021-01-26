import { makeStyles } from '@material-ui/core';
import React from 'react';
import InventoryItem from '../components/inventory/InventoryItem';

const useStyles = makeStyles(() => ({
  papierRoot: {
    display: 'flex',
    flexWrap: 'wrap',
  },
}));


const Inventory = () => {

  const classes = useStyles();


  return (
    <div className={classes.papierRoot}>
      <InventoryItem></InventoryItem>
    </div>
  );
}

export default Inventory;
