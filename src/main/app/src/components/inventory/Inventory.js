import {Box, makeStyles} from '@material-ui/core';
import React, {Fragment, useEffect, useState} from 'react';
import AppLogo from '../../misc/logo.svg';
import '../../misc/React-Spinner.css';
import {spinnyBoi} from '../About';
import {DataGrid} from "@material-ui/data-grid";
import CustomToolbar from '../tables/CustomToolbar';
import InventoryForm from "../../Forms/InventoryForm";
import QualityForm from "../../Forms/QualityForm";
import axios from "axios";
import Button from "@material-ui/core/Button";

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

const updateItem = async (data, reload) => {
  try {
    const api = `/api/inventory/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
  reload();
};

const insertItem = async (data, reload) => {
  if (data.itemName !== "" && data.buyPrice !== "" && data.sellPrice !== "" && data.goodType !== ""
    && data.quantity !== "" && data.location !== "") {
    if (data.billOfMaterial === "") {
      data.billOfMaterial = {};
    }
    try {
      const api = `/api/inventory/`;
      const inserted = await axios.post(api, data);
      console.log(`STATUS CODE: ${inserted.status}`);
      console.log(`DATA: ${inserted.data || "Nothing"}`);
    } catch (err) {
      console.log(err);
      return err;
    }
    reload();
  } else {
    alert("Please complete all the missing fields in the item form");
  }

};


const deleteItem = async (id, reload) => {
  try {
    const api = `/api/inventory/${id}`;
    const inserted = await axios.delete(api);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
  reload();
};


const inventoryCols = [
  {field: 'id', headerName: 'Item', width: 100},
  {field: 'ItemName', headerName: 'Item Name', width: 130},
  {field: 'GoodType', headerName: 'Type', width: 100},
  {field: 'Quantity', headerName: 'Quantity', width: 130},
  {field: 'SellPrice', headerName: 'Sell Price', width: 130},
  {field: 'BuyPrice', headerName: 'Buy Price', width: 130},
  {field: 'Location', headerName: 'Location', width: 130},
  {
    field: 'modify',
    headerName: 'Modify',
    width: 100,
    renderCell: params => (
      <div style={{margin: 'auto'}}>
        {
          <InventoryForm
            initialButton='Edit'
            dialogTitle='Inventory Information '
            dialogContentText='Please enter any information you would like to modify: '
            submitButton='Update'
            onSubmit={params.value.submit}
            item={params.value.item}
          />
        }
      </div>
    ),
  },
  {
    field: 'delete',
    headerName: 'Delete',
    width: 130,
    renderCell: params => (
      <div style={{margin: 'auto'}}>
        <Button variant="contained" onClick={params.value} color="primary" style={{float: 'right'}}>
          Delete
        </Button>
      </div>
    ),
  },
  {
    field: 'Quality', headerName: 'Quality', width: 150,
    renderCell: params => (
      <div style={{margin: 'auto'}}>
        {
          <QualityForm
            initialButton='Quality Management'
            dialogTitle='Quality Management'
            submitButton='Save'
            onSubmit={params.value}
          />
        }
      </div>
    ),
  },


];


const getInventory = async () => {
  const api = '/api/inventory/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const FilledInventoryView = (props) => {
  let items = [];

  let updateRow = (item, updatedItem) => {
    updateItem({
      id: item.id,
      itemName: updatedItem.itemName === "" ? item.itemName : updatedItem.itemName,
      goodType: updatedItem.goodType === "" ? item.goodType : updatedItem.goodType,
      quantity: updatedItem.quantity === "" ? item.quantity : updatedItem.quantity,
      sellPrice: updatedItem.sellPrice === "" ? item.sellPrice : updatedItem.sellPrice,
      buyPrice: updatedItem.buyPrice === "" ? item.buyPrice : updatedItem.buyPrice,
      location: updatedItem.location === "" ? item.location : updatedItem.location,
      billOfMaterial: updatedItem.billOfMaterial === "" ? item.billOfMaterial : updatedItem.billOfMaterial
    }, props.reload);
    return item;
  };
  props.inventoryItems.map(item => (
    items.push({
      id: item.id,
      ItemName: item.itemName,
      GoodType: item.goodType,
      Quantity: item.quantity,
      SellPrice: item.sellPrice,
      BuyPrice: item.buyPrice,
      Location: item.location,
      modify: {
        submit: (updatedItem) => updateRow(item, updatedItem),
        item: item
      },
      delete: () => deleteItem(item.id, props.reload)
    })));

  return (
    <DataGrid rows={items} columns={inventoryCols} pageSize={9} components={{Toolbar: CustomToolbar}}/>
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

const LoadedView = ({classes, inventory, reload}) => {
  return (
    <div>
      <h1 style={{textAlign: "center"}}>Inventory</h1>
      <div style={{height: 720, width: '78%', display: 'table', margin: '0 auto'}}>
        <div style={{display: 'table-row', float: 'right'}}>
          <InventoryForm
            initialButton='Insert'
            dialogTitle='Inventory Information '
            dialogContentText='Please enter information of the new item:'
            submitButton='Insert'
            onSubmit={(data) => insertItem(data, reload)}
          />
        </div>
        <div style={{height: '100%', width: '100%', display: 'table-row'}}>
          <FilledInventoryView
            inventoryItems={inventory}
            classes={classes}
            reload={reload}
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

const Inventory = () => {

  const classes = useStyles();
  const [inventory, setInventory] = useState([]);
  const [loading, setDoneLoading] = useState(true);

  const waitForGetRequest = async () => {
    getInventory().then(inv => setInventory(inv));
    setDoneLoading(false);
  }

  function reload() {
    setDoneLoading(true);
  }

  return (
    (loading) ?
      <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
        <LoadedView classes={classes} inventory={inventory} reload={reload}/>
      </SpinBeforeLoading> :
      <LoadedView classes={classes} inventory={inventory} reload={reload}/>
  );
};

export {Inventory, FilledInventoryView, Spinner, SpinBeforeLoading};
export default Inventory;
