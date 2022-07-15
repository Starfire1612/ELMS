import React from "react";
import { Link } from "react-router-dom";

function InstructorDashboard() {
  return (
    <div>
      <Link to="/instructor/add-course">
        <button>add course</button>
      </Link>
    </div>
  );
}

export default InstructorDashboard;
