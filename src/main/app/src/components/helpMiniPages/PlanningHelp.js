import planningImage from "../../misc/helpPageImages/planning.png";

function PlanningHelp() {
  return (
    <div>
      <h1>Planning</h1>
      <h2>The Interative Calendar</h2>
      <img
        src={planningImage}
        alt="img"
        style={{ width: "90%", height: "90%", resizeMode: "contain" }}
      />
      <h4>
        The planning section features an interactive calender which displays all
        active and scheduled production activites.
      </h4>
      <h4>
        THe Interative Calender can be used in planning new production
        pipelines, and is useful in ensuring given deadlines for product
        deliveries are met
      </h4>
      <h2>Adding a Task</h2>
      <h4>
        To schedule or add a new production activity, simply navigate and click
        on the desired date on the intertive calendar, and fill out the form
        information on the right side of the screen.
      </h4>
    </div>
  );
}

export default PlanningHelp;
