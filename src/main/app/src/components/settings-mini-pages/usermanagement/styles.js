import { makeStyles } from "@material-ui/core";

const fontSize = 1;
const profilePicDimensions = { height: 50, width: 55 };
const defaultPic = "/resources/images/user.png";

const cardCommonAttributes = {
  fontSize: `${fontSize}em`,
  minHeight: "17em",
  minWidth: "15em",
  borderRadius: '1em',
  borderWidth: 5,
};

const centeredGrid = attributes => ({
  display: 'grid',
  placeItems: 'center',
  ...attributes,
});

const shortTransition = theme => theme.transitions.create(['all'], {
  duration: theme.transitions.duration.short,
  easing: theme.transitions.easing.sharp,
});

const useUserCardStyles = makeStyles(theme => ({
  cardBase: {
    ...cardCommonAttributes,
    border: '5px dashed grey',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    width: '12em',
    height: '12em',
    zIndex: 0,
    marginRight: 0,
    marginLeft: 0,
  },
  card: {
    ...cardCommonAttributes,
    flex: 'none',
    display: 'flex',
    flexDirection: 'column',
    zIndex: 3,
    maxWidth: "18em",
    maxHeight: "25em",
    transition: theme.transitions.create(['all'], {
      duration: theme.transitions.duration.standard,
      easing: theme.transitions.easing.easeInOut,
    }),
    '& > *': {
      fontSize: '5%',
    },
    '& img': {
      margin: '0.5em',
    },
    '& svg': {
      margin: '0.5em',
    },
  },
  unflippedCard: {
    '&:hover': {
      transform: 'scale(1.2)',
      cursor: 'pointer',
      zIndex: 5100,
    },
  },
  unflippedHoverTrigger: {
    transform: 'scale(1.2)',
    cursor: 'pointer',
    zIndex: 5100,
  },
  profilePic: {
    height: `${profilePicDimensions.height}%`,
    width: `${profilePicDimensions.width}%`,
    display: 'flex',
    flexDirection: 'column',
    paddingTop: '1em',
    margin: 'auto',
    justifyContent: 'center',
    textAlign: 'center',
    fontSize: 'inherit',
  },
  flippedProfilePic: {
    minHeight: '4rem',
    minWidth: '4rem',
    wordWrap: 'anywhere',
    margin: '0.5rem',
    justifySelf: 'left',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center',
    '& img': {
      height: '2.5rem',
      width: '2.5rem',
    },
  },
  rolesList: {
    height: '100%',
    width: '100%',
    '& li': {
      margin: 0,
      padding: 0,
    },
  },
  userListContainer: {
  },
}));

const useUserGridStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    placeItems: 'center',
    placeContent: 'center',
    width: '100%',
    '& > div': {
      display: 'grid',
      width: '100%',
      columnGap: '1rem',
      gridTemplateColumns: '70% 1fr',
      gridTemplateRows: 'auto',
      justifyContent: 'center',
      justifyItems: 'center',
    },
  },
  grid: {
    margin: 'auto',
    marginTop: '1em',
    // maxWidth: theme.spacing(120),
  },
  userListContainer: {
    zIndex: 5,
    borderRadius: '1em',
    display: 'flex',
    maxHeight: '80%',
    minHeight: '42.5em',
    justifyContent: 'center',
    position: 'sticky',
    [theme.breakpoints.down('sm')]: {
      display: 'none',
    },
    '& > div': {
      maxHeight: 'inherit',
      borderRadius: 'inherit',
    },
  },
  blackout: {
    background: 'rgba(0, 0, 0, 0.5)',
    position: 'fixed',
    inset: '0% 0% 0% 0%',
    zIndex: 5000,
    height: '100%',
    width: '100%',
    backdropFilter: 'blur(5px)',
    WebkitBackdropFilter: 'blur(5px)',
    transition: theme.transitions.create(['all'], {
      duration: theme.transitions.duration.short,
      easing: theme.transitions.easing.sharp,
    }),
  },
}));

const useFlippedCardStyles = makeStyles({
  flippedCard: {
    zIndex: 6000,
    position: 'fixed',
    transform: ref => {
      const bodyRectangle = document.body.getBoundingClientRect();
      const elementRectangle = ref.current
        ? ref.current.getBoundingClientRect()
        : null;

      const offset = {
        x: (elementRectangle
          ? elementRectangle.left
          : bodyRectangle.left)
          - bodyRectangle.left,

        y: (elementRectangle
          ? elementRectangle.top
          : bodyRectangle.top)
          - bodyRectangle.top,
      };

      return `
        translate(
          calc(50vw - ${offset.x}px - 60%),
          calc(50vh - ${offset.y}px - 60%))

        scale(3)
      `;
    },
  },
});

export default useUserCardStyles;
export {
  useFlippedCardStyles,
  useUserCardStyles,
  useUserGridStyles,
  shortTransition,
  centeredGrid,
  defaultPic
};
