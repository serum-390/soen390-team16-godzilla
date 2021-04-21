import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import BillOfMaterialForm from "./BillOfMaterialForm";

export default function InventoryForm(props) {
  const [item] = React.useState(props.item);
  const [open, setOpen] = React.useState(false);
  const [location, setLocation] = React.useState("");
  const [itemName, setItemName] = React.useState("");
  const [sellPrice, setSellPrice] = React.useState("");
  const [buyPrice, setBuyPrice] = React.useState("");
  const [quantity, setQuantity] = React.useState("");
  const [goodType, setGoodType] = React.useState("");
  const [bom, setBOM] = React.useState("");

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
      billOfMaterial: bom
    };
    props.onSubmit(data);
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.itemName : ''}
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.location : ''}
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.quantity : ''}
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.sellPrice : ''}
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.buyPrice : ''}
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
            defaultValue={(typeof props.item !== "undefined" && props.item != null) ? item.goodType : ''}
            InputProps={{inputProps: {min: 1, max: 6}}}
          />

          <div style={{margin: 'auto'}}>
            <BillOfMaterialForm
              initialButton='BOM'
              dialogTitle={'Bill of Material'}
              dialogContentText={''}
              submitButton='Submit'
              onSubmit={(itemList) => setBOM(itemList)}
              billOfMaterial={(typeof props.item !== "undefined" && props.item != null) ? item.billOfMaterial : {}}
            />
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
