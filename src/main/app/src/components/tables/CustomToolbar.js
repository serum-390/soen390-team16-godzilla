import {
    GridToolbarContainer, GridToolbarExport, GridColumnsToolbarButton,
    GridFilterToolbarButton
  } from '@material-ui/data-grid';

function CustomToolbar() {
    return (
      <GridToolbarContainer>
        <GridColumnsToolbarButton />
        <GridFilterToolbarButton />
        <GridToolbarExport />
      </GridToolbarContainer>
    );
  }

export default CustomToolbar;