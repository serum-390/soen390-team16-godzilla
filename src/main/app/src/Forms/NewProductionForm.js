import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Snackbar from '@material-ui/core/Snackbar';
import Fade from '@material-ui/core/Fade';

export default function NewProductionForm(props) {
  const [open, setOpen] = React.useState(false);
  const [orderID, setOrderID] = React.useState("");
  const [productionDate, setProductionDate] = React.useState("");
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertText, setAlertText] = React.useState('');

  function closeAlert(){
    setOpenAlert(false);
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    if(orderID === '' || productionDate === ''){
      setOpenAlert(true);
      setAlertText("Please do not leave any fields empty.")
      return;
    }    

    let data = {
      id: orderID,
      date: productionDate,
    };
    props.onSubmit(data, true);
    
    setOpen(false);
  };

  return (
    <div>
      <Snackbar
          open={openAlert}
          onClose={closeAlert}
          TransitionComponent={Fade}
          message={alertText}
          autoHideDuration={6000}
        />
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
            onKeyDown={(e) => e.stopPropagation()}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="productionDate"
            label="Production Date"
            type="date"
            onChange={(event) => setProductionDate(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
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
