import {Box, makeStyles} from '@material-ui/core';
import React, {Fragment, useEffect, useState} from 'react';
import AppLogo from '../../misc/logo.svg';
import '../../misc/React-Spinner.css';
import {spinnyBoi} from '../About';
import {DataGrid} from "@material-ui/data-grid";
import CustomToolbar from '../tables/CustomToolbar';
import axios from "axios";
import Button from "@material-ui/core/Button";
import ShippingForm from "../../Forms/ShippingForm";

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

const addShippingItem = async data => {
  try {
    const api = '/api/shipping-manager/validate/?orderID=' + data.orderID + '&shippingDate=' + data.shippingDate +
      '&method=' + data.shippingMethod;
    const inserted = await axios.post(api);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};


const cancelShipping = async id => {
  try {
    const api = `/api/shipping-manager/cancel/${id}`;
    const canceled = await axios.post(api);
    console.log(`STATUS CODE: ${canceled.status}`);
    console.log(`DATA: ${canceled.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};


const shippingCols = [
  {field: 'id', headerName: 'Item', width: 100},
  {field: 'OrderID', headerName: 'Order ID', width: 130},
  {field: 'Status', headerName: 'Status', width: 100},
  {field: 'DueDate', headerName: 'Due date', width: 130},
  {field: 'ShippingDate', headerName: 'Shipping date', width: 150},
  {field: 'ShippingMethod', headerName: 'Shipping method', width: 160},
  {field: 'ShippingPrice', headerName: 'Shipping price', width: 150},
  {
    field: 'cancel',
    headerName: 'Cancel',
    width: 130,
    renderCell: params => (
      <div style={{margin: 'auto'}}>
        <Button variant="contained" onClick={params.value} color="primary" style={{float: 'right'}}>
          Cancel
        </Button>
      </div>
    ),
  }

];


const getShippingItems = async () => {
  const api = '/api/shipping/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const FilledShippingView = ({shippingItems, classes}) => {
  let items = [];

  shippingItems.map(item => (
    items.push({
      id: item.id,
      OrderID: item.orderID,
      Status: item.status,
      DueDate: item.dueDate,
      ShippingDate: item.shippingDate,
      ShippingMethod: item.shippingMethod,
      ShippingPrice: item.shippingPrice,
      cancel: () => cancelShipping(item.id)
    })));

  return (
    <DataGrid rows={items} columns={shippingCols} pageSize={9} components={{Toolbar: CustomToolbar}}/>
  );
};

const Spinner = () => {
  return (
    <Box
      display='flex'
      flexDirection='column'
      flexGrow={1}
      style={{
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <img src={AppLogo} alt='React Logo' className='App-logo'/>
      <h1>Loading...</h1>
    </Box>
  );
};

const LoadedView = ({classes, shipping}) => {
  return (
    <div>
      <h1 style={{textAlign: "center"}}>Shipping</h1>
      <div style={{height: 720, width: '75%', display: 'table', margin: '0 auto'}}>
        <div style={{display: 'table-row', float: 'right'}}>
          <ShippingForm
            initialButton='schedule shipping'
            dialogTitle='Shipping Information '
            dialogContentText='Please enter shipping information:'
            submitButton='schedule'
            onSubmit={(data) => addShippingItem(data)}
          />
        </div>
        <div style={{height: '100%', width: '100%', display: 'table-row'}}>
          <FilledShippingView
            shippingItems={shipping}
            classes={classes}
          />
        </div>
      </div>
    </div>
  );
};

const SpinBeforeLoading = ({
                             awaiting = async () => {
                             },
                             minLoadingTime = 0,
                             ...props
                           }) => {

  const [loading, setLoading] = useState(true);
  const [loadingMin, setLoadingMin] = useState(true);

  useEffect(() => {
    setTimeout(() => setLoadingMin(false), minLoadingTime);
    awaiting().then(() => setLoading(false));
  }, [awaiting, minLoadingTime]);

  return loading || loadingMin ? spinnyBoi
    : <Fragment>{props.children}</Fragment>;
};

const Shipping = () => {

  const classes = useStyles();
  const [shipping, setShipping] = useState([]);
  const [loading, setDoneLoading] = useState(true);

  const waitForGetRequest = async () => {
    getShippingItems().then(shippingItem => setShipping(shippingItem));
    setDoneLoading(false);
  }

  return (
    (loading) ?
      <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
        <LoadedView classes={classes} shipping={shipping}/>
      </SpinBeforeLoading> :
      <LoadedView classes={classes} shipping={shipping}/>
  );
};

export {Shipping, FilledShippingView, Spinner, SpinBeforeLoading};
export default Shipping;
