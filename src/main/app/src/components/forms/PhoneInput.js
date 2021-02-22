import React from 'react';
import InputLabel from '@material-ui/core/InputLabel';
import MaskedInput from 'react-text-mask';
import PropTypes from 'prop-types';
import Input from '@material-ui/core/Input';

function TextMaskCustom(props) {
    const { inputRef, ...other } = props;
    return (
        <MaskedInput
            {...other}
            ref={(ref) => {
                inputRef(ref ? ref.inputElement : null);
            }}
            mask={['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]}
            placeholderChar={'\u2000'}
            showMask
        />
    );
}

TextMaskCustom.propTypes = {
    inputRef: PropTypes.func.isRequired,
};

function PhoneInput() {
    const [values, setValues] = React.useState({
        textmask: '(  )    -  ss  ',
        numberformat: '1320',
    });

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    };
    return (
        <div>
            <InputLabel>Phone Number</InputLabel>
            <Input
                value={values.textmask}
                onChange={handleChange}
                name="textmask"
                inputComponent={TextMaskCustom}
            />
        </div >
    );
}
export default PhoneInput;