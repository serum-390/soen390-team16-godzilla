import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import PropTypes from 'prop-types';
import MaskedInput from 'react-text-mask';
import Input from '@material-ui/core/Input';

const useStyles = makeStyles((theme) => ({
  button: {
    display: 'block',
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 120,
  },
}));

export default function NewProductionForm(props) {
  const [open, setOpen] = React.useState(false);
  const [orderID, setOrderID] = React.useState("");
  const [productionDate, setProductionDate] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      id: orderID,
      date: productionDate,
    };
    props.onSubmit(data, true);
    setOpen(false);
    // want to update the table after clicking this
  };

  return (
    <div>
      <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={handleClickOpen}>
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
            id="orderID"
            label="Order #"
            type="string"
            onChange={(event) => setOrderID(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="productionDate"
            label="Production Date"
            type="date"
            onChange={(event) => setProductionDate(event.target.value)}
            fullWidth
          />
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
