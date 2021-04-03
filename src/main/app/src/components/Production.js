import { Button, makeStyles} from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import CustomToolbar from './tables/CustomToolbar';
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
      <div style={{ height: 720, width: '45%', float: 'left', display: 'table'}}>
        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Materials</h2>
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <DataGrid rows={materialRows} columns={materialColumns} pageSize={9} components={{ Toolbar: CustomToolbar}} />
        </div>
      </div>
      <div style={{ height: 720, width: '50%', float: 'right', display: 'table' }}>
        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Products</h2>
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <DataGrid rows={productRows} columns={productColumns} pageSize={9} components={{ Toolbar: CustomToolbar}} />
        </div>
      </div>
    </div>
  );
}

function Production() {
  const classes = useStyles();
  const [materialRows, setMaterialRows] = useState([]);
  const [productRows, setProductRows] = useState([]);
  const [loading, setDoneLoading] = useState(true);

  const waitForGetRequest = async () => {
    getMaterials().then(materials => setMaterialRows(materials));
    getProducts().then(products => setProductRows(products));
    setDoneLoading(false);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} materialRows={materialRows} productRows={productRows} />
    </SpinBeforeLoading> :
    <LoadedView classes={classes} materialRows={materialRows} productRows={productRows} />
  );
}

export default Production;