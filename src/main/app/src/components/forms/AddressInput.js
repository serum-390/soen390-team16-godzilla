import React from 'react';
import Grid from '@material-ui/core/Grid';
import InputLabel from '@material-ui/core/InputLabel';
import TextField from '@material-ui/core/TextField';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';

const ProvinceDrawer = () => {
    const [province, setProvince] = React.useState('');

    const handleChange = (event) => {
        setProvince(event.target.value);
    };
    return (
        <div >
            <Select
                value={province}
                onChange={handleChange}
                margin="normal"
            >
                {
                    ['AB', 'BC', 'MB',
                        'NB', 'NL', 'NT',
                        'NS', 'NU', 'ON',
                        'PE', 'QC', 'SK', 'YT'
                    ].map(province =>
                        <MenuItem value={province}>
                            {province}
                        </MenuItem>
                    )
                }
            </Select>
        </div>
    );
};

function AddressInput() {
    return (
        <div>
            <InputLabel>Address</InputLabel>
            <TextField
                autoFocus
                margin="normal"
                type="text"
                fullWidth />
            <Grid container spacing={2}>
                <Grid item md={3}>
                    <InputLabel>City</InputLabel>
                    <TextField
                        autoFocus
                        margin="normal"
                        type="text" />
                </Grid>
                <Grid item md={3}>
                    <InputLabel>Postal Code</InputLabel>
                    <TextField
                        autoFocus
                        margin="normal"
                        inputProps={{ maxLength: 6 }}
                        type="text" />
                </Grid>
                <Grid item md={3}>
                    <InputLabel>Province</InputLabel>
                    <ProvinceDrawer />
                </Grid>
            </Grid>
        </div>
    );
}

export default AddressInput;