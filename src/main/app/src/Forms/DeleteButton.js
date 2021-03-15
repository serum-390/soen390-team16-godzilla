import React from 'react';
import { Button, Dialog, DialogContentText } from '@material-ui/core';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import useBooleanState from '../util/hooks';

function DeleteButton(props) {
    const [open, handleOpen, handleClose] = useBooleanState(false);


    const DeleteDialogBox = () => {
        return (
            <Dialog open={open} onClose={handleClose} fullWidth maxWidth='sm'>
                <DialogTitle>Delete Vendor</DialogTitle>
                <DialogContent>
                    <DialogContentText>Are you sure you want to delete {props.vendorName}?</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
            </Button>
                    <Button onClick={() => {
                        handleClose();
                    }} color="secondary">
                        Delete
            </Button>
                </DialogActions>
            </Dialog>
        );
    };

    return (
        <div> <Button variant='contained'
            color='secondary' onClick={handleOpen}>
            Delete</Button>
            < DeleteDialogBox />
        </div >
    );
}

export default DeleteButton;