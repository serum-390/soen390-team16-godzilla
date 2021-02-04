import { Box, Button, useTheme } from '@material-ui/core';
import axios from 'axios';

const postMe = async () => {
  const data = {
    id: "5babfb45-40e3-48ef-ac86-8ca65662065b",
    title: "R2dbc is refined",
    content: "aasdasdasdasd",
  };

  try {
    const api = `/api/posts/${data.id}`
    const posted = await axios.put(api, data);
    console.log(`STATUS CODE: ${posted.status}`);
    console.log(`DATA: ${posted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
  }
};

const getMe = async () => {
  const api = '/api/posts/5babfb45-40e3-48ef-ac86-8ca65662065b';
  try {
    const got = await axios.get(api);
    console.log(got.data);
  } catch (err) {
    console.log(err);
  }
};

function Home() {
  const buttonStyle = {
    width: '15vw',
    margin: 'auto',
    marginTop: useTheme().spacing(1),
  };

  return (
    <div>
      <h1>This is the Home page</h1>
      <Box alignItems='center' display='flex' flexDirection='column' flexGrow={1}>
        <Button style={buttonStyle} variant='outlined' color='primary' onClick={postMe}>POST ME</Button>
        <Button style={buttonStyle} variant='outlined' color='secondary' onClick={getMe}>GET ME</Button>
      </Box>
    </div>
  );
}

export default Home;
