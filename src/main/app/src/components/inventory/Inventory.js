import {Box, Button, makeStyles, Typography} from '@material-ui/core';
import React, {Fragment, useEffect, useState} from 'react';
import MenuIcon from '@material-ui/icons/Menu'
import AppLogo from '../../misc/logo.svg';
import '../../misc/React-Spinner.css';
import {spinnyBoi} from '../About';
import {DataGrid} from "@material-ui/data-grid";

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(1),
    },
    sort: {
        margin: theme.spacing(1),
        textTransform: 'none',
    },
}));

const inventoryCols = [
    {field: 'id', headerName: 'Item', width: 130},
    {field: 'ItemName', headerName: 'Item Name', width: 130},
    {field: 'GoodType', headerName: 'Good Type', width: 130},
    {field: 'Quantity', headerName: 'Quantity', width: 130},
    {field: 'SellPrice', headerName: 'Sell Price', width: 130},
    {field: 'BuyPrice', headerName: 'Buy Price', width: 130},
    {field: 'Location', headerName: 'Location', width: 130},

];

const inventoryRows = [
    {
        id: '1',
        ItemName: 'Item',
        GoodType: '1',
        Quantity: '3',
        SellPrice: '223.12',
        BuyPrice: '124.43',
        Location: 'Montreal'
    },
    {
        id: '1',
        ItemName: 'Item',
        GoodType: '1',
        Quantity: '3',
        SellPrice: '223.12',
        BuyPrice: '124.43',
        Location: 'Montreal'
    },
    {
        id: '1',
        ItemName: 'Item',
        GoodType: '1',
        Quantity: '3',
        SellPrice: '223.12',
        BuyPrice: '124.43',
        Location: 'Montreal'
    },
    {
        id: '1',
        ItemName: 'Item',
        GoodType: '1',
        Quantity: '3',
        SellPrice: '223.12',
        BuyPrice: '124.43',
        Location: 'Montreal'
    }
];

const getInventory = async () => {
    const api = '/api/inventory/';
    const got = await fetch(api);
    const json = await got.json();
    return json || [];
};

const FilledInventoryView = ({inventoryItems, classes}) => {
    let items = [];
        inventoryItems.map(item => items.push({
                id: <input value={item.id} />,
                ItemName: item.itemName,
                GoodType: item.goodType,
                Quantity: item.quantity,
                SellPrice: item.sellPrice,
                BuyPrice: item.buyPrice,
                Location: item.location
            })
        );
    return (
        <div className={classes.root} style={{height: 1000, width: '90%', float: 'left'}}>
            <DataGrid rows={items} columns={inventoryCols} pageSize={15}/>
        </div>
    );
};

const Spinner = () => {
    return (
        <Box
            display='flex'
            flexDirection='column'
            flexGrow={1}
            style={{
                justifyContent: 'center',
                alignItems: 'center',
            }}
        >
            <img src={AppLogo} alt='React Logo' className='App-logo'/>
            <h1>Loading...</h1>
        </Box>
    );
};

const LoadedView = ({classes, inventory}) => {
    return (
        <div>
            <Box display='flex' flexDirection='row-reverse'>
                <Button
                    variant='contained'
                    className={classes.sort}
                    color='secondary'
                    startIcon={<MenuIcon/>}
                >
                    <Typography variant='h6'>Sort By</Typography>
                </Button>
            </Box>
            {/*<div style={{height: 600, width: '80%', float: 'center'}}>*/}
            {/*    <h2 style={{float: 'left'}}>Inventory</h2>*/}
            {/*    <TextField id="filled-basic" label="Search" variant="filled" style={{float: 'right'}}/>*/}
            {/*    <DataGrid rows={inventoryRows} columns={inventoryCols} pageSize={9}/>*/}
            {/*</div>*/}
            <div style={{height: 600, width: '80%', float: 'center'}}>
                <FilledInventoryView
                    inventoryItems={inventory}
                    classes={classes}
                />
            </div>

        </div>
    );
};

const SpinBeforeLoading = ({
                               awaiting = async () => {
                               },
                               minLoadingTime = 0,
                               ...props
                           }) => {

    const [loading, setLoading] = useState(true);
    const [loadingMin, setLoadingMin] = useState(true);

    useEffect(() => {
        setTimeout(() => setLoadingMin(false), minLoadingTime);
        awaiting().then(() => setLoading(false));
    }, [awaiting, minLoadingTime]);

    return loading || loadingMin ? spinnyBoi
        : <Fragment>{props.children}</Fragment>;
};

const Inventory = () => {

    const classes = useStyles();
    const [inventory, setInventory] = useState([]);
    const waitForGetRequest = async () => getInventory().then(inv => setInventory(inv));

    return (
        <SpinBeforeLoading minLoadingTime={1000} awaiting={waitForGetRequest}>
            <LoadedView classes={classes} inventory={inventory}/>
        </SpinBeforeLoading>
    );
};

export {Inventory, FilledInventoryView, Spinner, SpinBeforeLoading};
export default Inventory;
