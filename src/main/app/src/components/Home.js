import { Box, Button, TextField, useTheme } from '@material-ui/core';
import axios from 'axios';
import { useState } from 'react';

const grabFirstId = async () => (await axios.get('/api/goods/')).data[0].id;

const postMe = async data => {
  try {
    const api = `/api/goods/${await grabFirstId()}`;
    const posted = await axios.put(api, data);
    console.log(`STATUS CODE: ${posted.status}`);
    console.log(`DATA: ${posted.data || "Nothing"}`);
    return JSON.stringify({
      statusCode: posted.status,
      data: posted.data || 'Nothing',
    }, null, 4);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const getMe = async () => {
  const api = `/api/goods/${await grabFirstId()}`;
  try {
    const got = await axios.get(api);
    console.log(got.data);
    return JSON.stringify(got.data, null, 4);
  } catch (err) {
    console.log(err);
    return err;
  }
};

function Home() {
  const buttonStyle = {
    width: '15vw',
    margin: 'auto',
    marginTop: useTheme().spacing(1),
    '& > *': {
      margin: 'auto',
    },
  };

  const [newName, setNewName] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [textAreaValue, setTextAreaValue] = useState(
    'Make a REST API request using the above controls' +
    ' and the response will be shown here...'
  );

  const handleNameChange = event => setNewName(event.target.value);
  const handleDescriptionChange = event => setNewDescription(event.target.value);

  const getDataFromSpring = () => getMe().then(got => setTextAreaValue(got));
  const postDataToSpring = () => postMe({
    name: newName,
    description: newDescription,
  }).then(response => setTextAreaValue(response))

  return (
    <div>
      <h1>This is the Home page</h1>
      <Box alignItems='center' display='flex' flexDirection='column' flexGrow={1}>
        <Button style={buttonStyle}
                variant='outlined'
                color='primary'
                onClick={postDataToSpring}>POST ME</Button>
        <Button style={buttonStyle}
                variant='outlined'
                color='secondary'
                onClick={getDataFromSpring}>GET ME</Button>
        <Box style={{ width: '35vw', marginTop: useTheme().spacing(2) }} display='flex' flexDirection='row'>
          <TextField id='name'
                     label='New Good Name'
                     placeholder='New good name...'
                     variant='outlined'
                     onChange={handleNameChange} />
          <TextField id='description'
                     label='New Good Description'
                     placeholder='New good description...'
                     multiline
                     variant='outlined'
                     rows={5}
                     style={{ width: '20vw', marginLeft: 'auto' }}
                     onChange={handleDescriptionChange} />
        </Box>
        <TextField id='rest-response'
                   label='REST API Response:'
                   value={textAreaValue}
                   multiline
                   variant='outlined'
                   rows={7}
                   color='secondary'
                   InputProps={{ readOnly: true }}
                   style={{ width: '30vw', marginTop: useTheme().spacing(2), }} />
      </Box>
    </div>
  );
}

export default Home;
