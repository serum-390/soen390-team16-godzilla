import { Box, CircularProgress } from "@material-ui/core";

const spinnyBoi = (
  <Box
    display='flex'
    justifyContent='center'
    style={{ paddingTop: '38vh' }}
  >
    <CircularProgress color='secondary' />
  </Box>
);

function About() {
  return (
    <div>
      <h1>This is the About us page</h1>
      {spinnyBoi}
    </div>
  );
}

export { About, spinnyBoi };
export default About;