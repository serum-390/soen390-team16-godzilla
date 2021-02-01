import { makeStyles } from "@material-ui/core";

const useSalesPageStyles = makeStyles(theme => ({
  root: {
    height: '80vh',
    '& > h1, h2, h3, h4, h5, h6': {
      textAlign: 'center',
    },
  },
  grid: {
    width: '100%',
  },
}));

export default useSalesPageStyles;
