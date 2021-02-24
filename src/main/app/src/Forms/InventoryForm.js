import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    button: {
        display: 'block',
    },
    formControl: {
        margin: theme.spacing(2),
        minWidth: 120,
    },
}));

export default function InventoryForm(props) {
    const [open, setOpen] = React.useState(false);
    const[location, setLocation] = React.useState("");
    const[itemName, setItemName] = React.useState("");
    const[sellPrice, setSellPrice] = React.useState("");
    const[buyPrice, setBuyPrice] = React.useState("");
    const[quantity, setQuantity] = React.useState("");


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleUpdate = () => {
        props.onUpdate(location,itemName, sellPrice, buyPrice,quantity);
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
                        id="itemName"
                        label="Item Name"
                        type="string"
                        onChange = {(event)=> setItemName(event.target.value)}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="location"
                        label="Location"
                        type="string"
                        onChange = {(event)=> setLocation(event.target.value)}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="quantity"
                        label="Quantity"
                        type="string"
                        onChange = {(event)=> setQuantity(event.target.value)}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="sellPrice"
                        label="Sell Price"
                        type="string"
                        onChange = {(event)=> setSellPrice(event.target.value)}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="buyPrice"
                        label="Buy Price"
                        type="string"
                        onChange = {(event)=> setBuyPrice(event.target.value)}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleUpdate} color="primary">
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
