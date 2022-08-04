import React from "react";
import Reports from "../../../static/images/Reports.png";
function NoCourseReports() {
  return (
    <div className="no-report">
      <div>
        <p className="text-center">Select any course to see reports</p>
        <img className="reports-img" src={Reports} />
      </div>
    </div>
  );
}

export default NoCourseReports;
