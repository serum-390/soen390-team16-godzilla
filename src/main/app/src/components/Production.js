import {Box, makeStyles} from '@material-ui/core';
import React, {Fragment, useEffect, useState} from 'react';
import {DataGrid} from "@material-ui/data-grid";
import axios from "axios";
import { SpinBeforeLoading } from './inventory/Inventory';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    padding: theme.spacing(1),
  },
  sort: {
    margin: theme.spacing(1),
    textTransform: 'none',
  },
}));

const updateItem = async data => {
  try {
    const api = `/api/orders/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const inventoryCols = [
  {field: 'id', headerName: 'ID', width: 100},
  {field: 'CreatedDate', headerName: 'Created Date', width: 130},
  {field: 'DueDate', headerName: 'Due Date', width: 100},
  {field: 'DeliveryLocation', headerName: 'Delivery Location', width: 130},
  {field: 'Status', headerName: 'Status', width: 130},
  {field: 'Items', headerName: 'Items', width: 100},
];


const getSalesOrder = async () => {
  const api = '/api/orders/?type=sales';
  const got = await fetch(api);
  const json = await got.json();
  return json || [];
};

const FilledSalesOrderView = ({SalesOrders}) => {
  let orders = [];

  SalesOrders.map(order => (
    orders.push({
      id: order.id,
      createdDate: order.createdDate,
      dueDate: order.dueDate,
      deliveryLocation: order.deliveryLocation,
      items: order.items
    })));

  return (
    <DataGrid rows={orders} columns={inventoryCols} pageSize={9}/>
  );
};

const LoadedView = ({classes, orders}) => {
  return (
    <div>
      <h1 style={{textAlign: "center"}}>Production</h1>
      <div style={{height: 800, width: '85%', float: "left"}}>
        <FilledSalesOrderView
          SalesOrders={orders}
          classes={classes}
        />
      </div>
    </div>
  );
};

const Production = () => {

  const classes = useStyles();
  const [orders, setOrders] = useState([]);
  const waitForGetRequest = async () => getSalesOrder().then(orders => setOrders(orders));

  return (
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} orders={orders}/>
    </SpinBeforeLoading>
  );
};

export {Production, FilledSalesOrderView};
export default Production;
