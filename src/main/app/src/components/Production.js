import { Button, makeStyles} from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import CustomToolbar from './tables/CustomToolbar';
import React, { useState } from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import NewProductionForm from "../Forms/NewProductionForm";
import axios from "axios";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    padding: theme.spacing(2),
  },
  sort: {
    margin: theme.spacing(1),
    textTransform: 'none',
  },
}));

const getMaterials = async () => {
  const api = '/api/materials/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];

  if (!json.materials) return [];

  return json.materials.map(materials => {
    materials.allMaterials = {
      onClick: () => {
      },
    };
    return materials;
  });
};

const getProductions = async () => {
  const api = '/api/planning/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const cancelProduction = async (id, reload) => {
  try {
    const api = `/api/production-manager/cancel/${id}`;
    const inserted = await axios.post(api, id);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};

const insertProduction = async (data, reload) => {
  try {
    const api = `/api/production-manager/validate`;
    const inserted = await axios.post(api, data);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};


const getInventory = async () => {
  const api = '/api/inventory/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json || [];
};

const materialColumns = [
  { field: 'id', headerName: '', width: 0, hide: true },
  { field: 'material', headerName: 'Material', width: 230 },
  { field: 'mID', headerName: 'Material ID', width: 180 },
  { field: 'status', headerName: 'Status', width: 180 }
];

const productionColumns = [
  { field: 'id', headerName: 'ID', width: 70},
  { field: 'orderID', headerName: 'Order ID', width: 110 },
  { field: 'productionDate', headerName: 'Production Date', width: 160},
  { field: 'status', headerName: 'Status', width: 120},
  { field: 'usedItems', headerName: 'Used Items', width: 500 },
  {
    field: 'cancel',
    headerName: 'Cancel',
    width: 120,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          params.getValue("status") === 'CANCELLED' || params.getValue("status") === 'COMPLETED'
          ? <Button
              disabled
              variant='contained'
              color='primary'
            >
              Cancel
            </Button>
          : <Button
              variant='contained'
              color='primary'
              onClick={params.value}
            >
              Cancel
            </Button>
        }
      </div>
    )
  }
];

const FilledProductionView = (props) => {
  let prod = [];

  props.productions.map(item => (
    prod.push({
      id: item.id,
      orderID: item.orderID,
      productionDate: item.productionDate,
      status: item.status,
      usedItems: ShowItems(item.usedItems, props.inventory),
      cancel: () => cancelProduction(item.id, props.reload)
    })));

  return (
    <DataGrid rows={prod} columns={productionColumns} pageSize={9} components={{ Toolbar: CustomToolbar}}/>
  );
};

function ShowItems(items, inventory){
  let allItems = "";
  let count = 0;

  for(let i = 0; i < inventory.length; i++){
    let id = inventory[i]["id"];
    if(items[id] !== undefined){
      if(count > 0)
        allItems += ", ";

      allItems += inventory[i]["itemName"] + " ("+items[id]+")";

      count++;
    }
  }

  return allItems;
}

const LoadedView = (props) => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Production Department</h1>
      <div style={{ height: 720, width: '35%', float: 'left', display: 'table'}}>
        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Materials</h2>
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <DataGrid rows={props.materialRows} columns={materialColumns} pageSize={9} components={{ Toolbar: CustomToolbar}} />
        </div>
      </div>
      <div style={{ height: 720, width: '62%', float: 'right', display: 'table' }}>
        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Productions</h2>
          <div style={{ float: 'right'}}>
            <NewProductionForm
              onSubmit={(data, insert) => (insert) ? insertProduction(data, props.reload) : ''}
              initialButton='Add New Production'
              dialogTitle='Add New Production'
              dialogContentText='Please enter any information you would like to add: '
              submitButton='Confirm'
            />
          </div>
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
        <FilledProductionView
            productions={props.productions}
            inventory={props.inventory}
            reload={props.reload}
          />
        </div>
      </div>
    </div>
  );
}

function Production() {
  const classes = useStyles();
  const [materialRows, setMaterialRows] = useState([]);
  const [productions, setProductions] = useState([]);
  const [inventory, setInventory] = useState([]);
  const [loading, setLoading] = useState(true);

  const waitForGetRequest = async () => {
    getMaterials().then(materials => setMaterialRows(materials));
    getProductions().then(prods => setProductions(prods));
    getInventory().then(inv => setInventory(inv));
    setLoading(false);
  }

  function reload(){
    setLoading(true);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} materialRows={materialRows} productions={productions} inventory={inventory} reload={reload}/>
    </SpinBeforeLoading> :
    <LoadedView classes={classes} materialRows={materialRows} productions={productions} inventory={inventory} reload={reload}/>
  );
}

export default Production;