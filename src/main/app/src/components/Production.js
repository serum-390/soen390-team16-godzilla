import { Button, makeStyles, TextField } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React, { useEffect, useState } from 'react';
import { Spinner } from './inventory/Inventory';

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

const materialColumns = [
  { field: 'id', headerName: '', width: 0,  hide: true},
  { field: 'material', headerName: 'Material', width: 230 },
  { field: 'mID', headerName: 'Material ID', width: 180 },
  { field: 'status', headerName: 'Status', width: 180 }
];

const materialRows = [
  { id: "1", material: 'Material 1', mID: 'MAT-1', status: 'In Stock'},
  { id: '2', material: 'Material 2', mID: 'MAT-2', status: 'In Stock'},
  { id: "3", material: 'Material 3', mID: 'MAT-3', status: 'In Stock'},
  { id: "4", material: 'Material 4', mID: 'MAT-4', status: 'In Stock'},
  { id: "5", material: 'Material 5', mID: 'MAT-5', status: 'Backorder'},
  { id: "6", material: 'Material 6', mID: 'MAT-6', status: 'In Stock'},
  { id: "7", material: 'Material 7', mID: 'MAT-7', status: 'Backorder'}
];

const purchaseColumns = [
  { field: 'id', headerName: '', width: 0, hide: true},
  { field: 'product', headerName: 'Product', width: 230 },
  { field: 'requiredMaterials', headerName: 'Required Materials (Material ID)', width: 560 },
  { field: 'production', headerName: 'Production', width: 130, renderCell: () => (
    <div style={{ margin: 'auto' }}>
      <Button variant='contained' color='primary' onClick={(newSelection) => { ToggleProduction(); }}>Start</Button>
    </div>
  )}
];


const purchaseRows = [
  { id: 1, product: 'Product 1', requiredMaterials: 'MAT-1, MAT-3, MAT-4, MAT-7', production: ''},
  { id: 2, product: 'Product 2', requiredMaterials: 'MAT-1, MAT-2, MAT-5, MAT-6, MAT-7', production: '05/10/19'},
  { id: 3, product: 'Product 3', requiredMaterials: 'MAT-1, MAT-3, MAT-4, MAT-6', production: '05/10/19'},
  { id: 4, product: 'Product 4', requiredMaterials: 'MAT-1, MAT-3', production: '05/10/19'},
  { id: 5, product: 'Product 5', requiredMaterials: 'MAT-1, MAT-2, MAT-7', production: '05/10/19'}
];

function ToggleProduction() {
  alert("Now starting production...");
}

const LoadedView = () => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Production Department</h1>
      <div style={{ height: 600, width: '35%', float: 'left' }}>
        <h2 style={{ float: 'left' }}>Materials</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }}/>
        <DataGrid rows={materialRows} columns={materialColumns} pageSize={9} />
      </div>
      <div style={{ height: 600, width: '55%', float: 'right' }}>
        <h2 style={{ float: 'left' }}>Products</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }}/>
        <DataGrid rows={purchaseRows} columns={purchaseColumns} pageSize={9}/>
      </div>
    </div>
  );
}

function Production() {

  const classes = useStyles();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, []);


  return loading ? <Spinner />
                 : <LoadedView classes={classes} />;
}


export default Production;