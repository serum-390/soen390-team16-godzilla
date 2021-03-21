import { Button, makeStyles, TextField } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React, { useState } from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';

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
  const json = await got.json();

  if (!json.materials) return [];

  return json.materials.map(materials => {
    materials.allMaterials = {
      onClick: () => {
      },
    };
    return materials;
  });
};

const getProducts = async () => {
  const api = '/api/products/';
  const got = await fetch(api);
  const json = await got.json();

  if (!json.products) return [];

  return json.products.map(products => {
    products.allProducts = {
      onClick: () => {
      },
    };
    return products;
  });
};

const materialColumns = [
  { field: 'id', headerName: '', width: 0, hide: true },
  { field: 'material', headerName: 'Material', width: 230 },
  { field: 'mID', headerName: 'Material ID', width: 180 },
  { field: 'status', headerName: 'Status', width: 180 }
];

const productColumns = [
  { field: 'id', headerName: '', width: 0, hide: true },
  { field: 'product', headerName: 'Product', width: 230 },
  { field: 'requiredMaterials', headerName: 'Required Materials (Material ID)', width: 560 },
  { field: 'production', headerName: '', width: 0, hide: true },
  {
    field: 'productionButton', headerName: 'Production', width: 130, renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          params.getValue("production") === 0
          ? <Button
              variant='contained'
              color='primary'
              onClick={(newSelection) =>
                toggleProduction(
                    params.getValue("id"),
                    params.getValue("production"))
              }
            >
              Start
            </Button>
          : <Button
              variant='contained'
              color='primary'
              onClick={newSelection =>
                toggleProduction(
                    params.getValue("id"),
                    params.getValue("production"))
              }
            >
              Stop
            </Button>
        }
      </div>
    )
  }
];

function toggleProduction(id, prod) {
  if (prod === 0) {
    alert("Now starting production for Product #" + id + "...");
  } else {
    alert("Now stopping production for Product #" + id + "...");
  }
}

const LoadedView = ({ classes, materialRows, productRows }) => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Production Department</h1>
      <div style={{ height: 600, width: '35%', float: 'left' }}>
        <h2 style={{ float: 'left' }}>Materials</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }} />
        <DataGrid rows={materialRows} columns={materialColumns} pageSize={9} />
      </div>
      <div style={{ height: 600, width: '55%', float: 'right' }}>
        <h2 style={{ float: 'left' }}>Products</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }} />
        <DataGrid rows={productRows} columns={productColumns} pageSize={9} />
      </div>
    </div>
  );
}

function Production() {
  const classes = useStyles();
  const [materialRows, setMaterialRows] = useState([]);
  const [productRows, setProductRows] = useState([]);

  const waitForGetRequest = async () => {
    getMaterials().then(materials => setMaterialRows(materials));
    getProducts().then(products => setProductRows(products));
  }

  return (
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} materialRows={materialRows} productRows={productRows} />
    </SpinBeforeLoading>
  );
}

export default Production;