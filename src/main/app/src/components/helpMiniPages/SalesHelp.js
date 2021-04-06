import exportImage from "../../misc/helpPageImages/exporting.png";
import orderingImage from "../../misc/helpPageImages/ordering.png";
function SalesHelp() {
  return (
    <div>
      <h1>Sales</h1>
      <h2>Managing Customers And Sales Orders</h2>
      <h4>
        Customers and sales orders can be added to, edited and deleted from
        their respective tables.{" "}
      </h4>
      <h4>
        To add a customers, select "Add new customers ", while to add a
        purchase, select "Add new sales
      </h4>
      <h4>
        To edit or delete a customers record or sales order , navigate to the
        desired record in it's table , and select the "Edit" or "Delete" button
        respectively
      </h4>
      <h2>Reordering of tables</h2>
      <h4>
        Tables can be reordered in different orders by selecting the
        corresponding column title
      </h4>
      <img
        src={orderingImage}
        alt="img"
        style={{ width: "10%", height: "10%", resizeMode: "contain" }}
      />
      <h2>Exporting as CSV</h2>
      <h4>
        Both the customer and sales order tables can easily be expored as an
        external .csv file for usage.
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

export default SalesHelp;
