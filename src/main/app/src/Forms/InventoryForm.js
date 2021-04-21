import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { DataGrid } from '@material-ui/data-grid';
import CustomToolbar from '../components/tables/CustomToolbar';

export default function InventoryForm(props) {
  const item = props.onSubmit(null, false);
  const [open, setOpen] = React.useState(false);
  const [location, setLocation] = React.useState("");
  const [itemName, setItemName] = React.useState("");
  const [sellPrice, setSellPrice] = React.useState("");
  const [buyPrice, setBuyPrice] = React.useState("");
  const [quantity, setQuantity] = React.useState("");
  const [goodType, setGoodType] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      id: "",
      itemName: itemName,
      goodType: goodType,
      quantity: quantity,
      sellPrice: sellPrice,
      buyPrice: buyPrice,
      location: location,
      billOfMaterial: {}
    };
    props.onSubmit(data, true);
    setOpen(false);
  };


  return (
    <div>
      <Button variant="contained" color="primary" style={{float: 'right'}} onClick={handleClickOpen}>
        {props.initialButton}
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">{props.dialogTitle}</DialogTitle>

        <DialogContent>
          <DialogContentText>
            {props.dialogContentText}
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="itemName"
            label="Item Name"
            type="string"
            defaultValue={item.itemName}
            onChange={(event) => setItemName(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="location"
            label="Location"
            type="string"
            defaultValue={item.location}
            onChange={(event) => setLocation(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="quantity"
            label="Quantity"
            type="number"
            defaultValue={item.quantity}
            onChange={(event) => setQuantity(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="sellPrice"
            label="Sell Price"
            type="number"
            step={0.5}
            defaultValue={item.sellPrice}
            onChange={(event) => setSellPrice(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="buyPrice"
            label="Buy Price"
            type="number"
            step={0.5}
            defaultValue={item.buyPrice}
            onChange={(event) => setBuyPrice(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="goodType"
            label="Good Type"
            onChange={(event) => setGoodType(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
            type="number"
            defaultValue={item.goodType}
            InputProps={{inputProps: {min: 1, max: 6}}}
          />


        <div style={{ height: 300, width: '100%' }}>
          <h3>Bill Of Material</h3>
          <DataGrid rows={rows} columns={columns} pageSize={9} components={{ Toolbar: CustomToolbar}}/>
        </div>


        </DialogContent>
        <DialogActions>
          <Button onClick={handleSubmit} color="primary">
            {props.submitButton}
          </Button>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}


const rows = [
  { id: 1, col1: 'Frame Speed A', col2: '2', col3: '$0' },
  { id: 2, col1: 'Frame Speed B', col2: '3', col3: '$0'  },
  { id: 3, col1: 'Frame Speed C', col2: '3', col3: '$0' },
];

const columns = [
  { field: 'col1', headerName: 'Name', width: 150 },
  { field: 'col2', headerName: 'Quality', width: 150 },
  { field: 'col3', headerName: 'Price', width: 150 },
];

