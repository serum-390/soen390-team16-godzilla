import exportImage from "../../misc/helpPageImages/exporting.png";
import useStyles from "../Help";
import orderingImage from "../../misc/helpPageImages/ordering.png";

function ProductionHelp() {
  const classes = useStyles();
  return (
    <div>
      <h1>Production</h1>
      <h4>
        The Production Page displays a list of all items in the production line
        until the items are completed. This list consists of :
      </h4>
      <ul>
        <li>Raw materials</li>
        <li>Products under assembly</li>
      </ul>
      <h4>
        All completed items will be removed from the production line and
        displayed in the inventory page
      </h4>
      <h2>Reordering of tables</h2>
      <h4>
        Tables can be reordered in different orders by selecting the
        corresponding column title
      </h4>
      <img
        src={orderingImage}
        alt="img"
        classname={classes.orderImg}
      />
      <h2>Exporting as CSV</h2>
      <h4>
        Both Materials and Products can easily be expored as an external .csv
        file for usage.
      </h4>
      <img
        src={exportImage}
        alt="img"
        style={{ width: "100%", height: "100%", resizeMode: "contain" }}
      />
      <h4>
        To Export as a .csv file , click on the "Export" button, and then
        "Download as CSV". This will immediately begin a download of an exported
        CSV file to your device
      </h4>
    </div>
  );
}

export default ProductionHelp;
