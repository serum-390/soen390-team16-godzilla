import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";

export default function ShippingForm(props) {
  const [open, setOpen] = React.useState(false);
  const [shippingDate, setShippingDate] = React.useState("2021-01-01");
  const [orderID, setOrderID] = React.useState("1");
  const [shippingMethod, setShippingMethod] = React.useState("air");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      shippingDate: shippingDate,
      orderID: orderID,
      shippingMethod: shippingMethod,
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
            id="shippingDate"
            label="Shipping Date"
            type="date"
            defaultValue= "2021-01-01"
            onChange={(event) => setShippingDate(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="orderID"
            label="Order ID"
            type="number"
            defaultValue={1}
            onChange={(event) => setOrderID(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            InputLabelProps={{
              shrink: true,
            }}
            InputProps={{inputProps: {min: 1}}}
            fullWidth
          />

          <InputLabel id="label" shrink={true}>Shipping Method</InputLabel>
          <Select labelId="label" id="select" value={shippingMethod}
              onKeyDown={(e) => e.stopPropagation()}
              onChange={(event) => setShippingMethod(event.target.value)}>
            <MenuItem value="air">Air</MenuItem>
            <MenuItem value="car">Car</MenuItem>
            <MenuItem value="ferry">Ferry</MenuItem>
          </Select>
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
