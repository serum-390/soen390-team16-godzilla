import { ButtonBase, FormControlLabel, Icon, IconButton, List, ListItem, ListItemIcon, ListItemText, makeStyles, Paper, Switch, Typography } from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import clsx from 'clsx';
import { Fragment, useRef } from "react";
import useUserCardStyles, { centeredGrid, defaultPic, shortTransition, useFlippedCardStyles } from "./styles";

const defaultBorder = '0.2rem solid rgba(0, 0, 0, 0.12)';
const useStyles = makeStyles(theme => ({
  flippedCardTop: centeredGrid({
    gridTemplateColumns: 'auto auto',
    gridTemplateRows: 'auto',
  }),
  flippedCardBottom: centeredGrid({
    gridTemplateColumns: 'auto',
    gridAutoRows: '120% auto',
  }),
  flippedRolesList: {
    display: 'flex',
    placeItems: 'center',
    placeContent: 'center',
    width: '100%',
    height: '100%',
    borderBottom: defaultBorder,
    '& *': {
      fontSize: '1rem',
    },
    '& > li': {
      border: '0.05rem solid rgba(0, 0, 0, 0.12)',
      backgroundColor: 'rgba(0, 0, 0, 0.05)',
      borderRadius: '1em',
      margin: 'auto',
      width: '70%',
      height: '40%',
      textAlign: 'center',
      transition: shortTransition(theme),
      '&:hover': {
        border: '0.05rem solid rgba(0, 0, 0, 0.3)',
        backgroundColor: 'rgba(193, 219, 193, 0.4)',
      },
    },
  },
  removeRoleButton: {
    width: '1em',
    height: '1em',
    alignSelf: 'center',
    marginRight: '-1em',
  },
  addRoleButtonRoot: {
    fontSize: 'smaller',
    width: '100%',
    height: '59%',
    borderRadius: '0% 0% 14em 14em',
    background: theme.palette.success.main,
    display: 'flex',
    placeItems: 'center',
    placeContent: 'center',
    transition: shortTransition(theme),
    alignSelf: 'start',
    '&:hover': {
      filter: 'brightness(0.85)',
    },
    '& > svg': {
      color: 'white',
      zIndex: 5100,
    },
  },
  adminSwitch: {
    justifySelf: 'right',
    marginRight: '0.2rem',
    transform: 'scale(0.7)',
  },
}));

const defaultImg =
  <img
    src={defaultPic}
    alt="Profile Pic"
  />;

const Text = props =>
  <Typography>
    {props.children}
  </Typography>;

function UserProfilePicBlock({ userName, image, admin, ...props }) {
  const classes = useUserCardStyles();
  const adminTag = () => <Text>ADMIN</Text>;
  const userTag = () => <Text>USER</Text>;
  return (
    <div className={classes.profilePic} {...props}>
      <Text>{userName}</Text>
      {image ? image : defaultImg}
      {(admin ? adminTag : userTag)()}
    </div>
  );
}

function FlippedUserProfilePicBlock({ userName, image, ...props }) {
  const classes = useUserCardStyles();

  return (
    <div className={classes.flippedProfilePic} {...props}>
      {image ? image : defaultImg}
      <Text>{userName}</Text>
    </div>
  );
}

function FlippedCardTop(props) {
  const classes = useStyles();
  return (
    <div className={classes.flippedCardTop}>
      {props.children}
    </div>
  );
}

function FlippedCardBottom(props) {
  const classes = useStyles();
  return (
    <div className={classes.flippedCardBottom}>
      {props.children}
    </div>
  );
}

function RolesList({ roles, transformRole, ...props }) {
  const classes = useUserCardStyles();
  const listItemTextClasses = makeStyles(() => ({
    listItemTextPrimary: { fontSize: '1.25em', },
    listItemTextSecondary: { fontSize: '0.75em' },
  }))();

  const roleToListItem = (role, i) =>
    <ListItem key={i} style={{ fontSize: 'inherit', }}>
      <ListItemIcon style={{ fontSize: '1.8em', }}>
        <AttachMoneyIcon fontSize='inherit' />
      </ListItemIcon>
      <ListItemText
        classes={{
          primary: listItemTextClasses.listItemTextPrimary,
          secondary: listItemTextClasses.listItemTextSecondary,
        }}
        primary={role.role}
        secondary={role.description ? role.description : null}
      />
    </ListItem>;

  return (
    <List
      className={classes.rolesList}
      style={{ fontSize: 'inherit' }}
      {...props}
    >
      {roles.map(transformRole ? transformRole : roleToListItem)}
    </List>
  );
}

function AdminSwitch({ admin, ...props }) {
  const classes = useStyles();
  return (
    <FormControlLabel
      className={classes.adminSwitch}
      control={<Switch name="admin" className="admin-switch" />}
      label="Admin"
      {...props}
    />
  );
}

const x = <Icon style={{
  display: 'flex',
  placeSelf: 'center',
  placeItems: 'center',
  placeContent: 'center',
}}> × </Icon>;

function FlippedRolesList({ user, ...props }) {
  const classes = useStyles();
  const transformRole = (role, i) =>
    <ListItem key={i} style={{ padding: 0, paddingLeft: '0.1em', }}>
      <ListItemIcon style={{ fontSize: '1.8em', minWidth: 0, }}>
        <IconButton className={classes.removeRoleButton}>
          {x}
        </IconButton>
      </ListItemIcon>
      <ListItemText primary={role.role.toUpperCase()} />
    </ListItem>;

  return (
    <RolesList
      className={classes.flippedRolesList}
      roles={user.roles}
      transformRole={transformRole}
      {...props}
    />
  );
}

function AddRoleButton({ ...props }) {
  const classes = useStyles();
  return (
    <ButtonBase focusRipple className={classes.addRoleButtonRoot} {...props}>
      <div />
      <AddIcon />
    </ButtonBase>
  );
}

function EditUserCard({ user, flipped, flip, unflipAll, hovering, ...props }) {
  const classes = useUserCardStyles();
  const paperRef = useRef(null);
  const flippedCardClasses = useFlippedCardStyles(paperRef);

  const profilePicProps = {
    userName: user && user.name ? user.name : "",
    admin: user && user.admin ? user.admin : false,
  };

  const UnflippedCardContents = () =>
    <Fragment>
      <UserProfilePicBlock {...profilePicProps} />
      <RolesList roles={user.roles} />
    </Fragment>;

  const FlippedCardContents = () =>
    <Fragment>
      <FlippedCardTop>
        <FlippedUserProfilePicBlock {...profilePicProps} />
        <AdminSwitch />
      </FlippedCardTop>
      <FlippedCardBottom>
        <FlippedRolesList user={user} />
        <AddRoleButton />
      </FlippedCardBottom>
    </Fragment>;

  return (
    <div className={classes.cardBase}>
      <Paper
        ref={paperRef}
        variant='outlined'
        elevation={0}
        onClick={flipped ? () => { } : flip}
        className={clsx(classes.card, {
          [classes.unflippedCard]: !flipped,
          [classes.unflippedHoverTrigger]: hovering,
          [flippedCardClasses.flippedCard]: flipped,
        })}
        {...props}
      >
        {flipped ? <FlippedCardContents /> : <UnflippedCardContents />}
      </Paper>
    </div>
  );
}

export default EditUserCard;
export { EditUserCard };
