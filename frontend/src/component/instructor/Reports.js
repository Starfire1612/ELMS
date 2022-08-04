import "../../styles/Reports.css";
import ReportsCourseList from "./reports/ReportsCourseList.js";
import { Outlet } from "react-router-dom";

export default function Reports({ userData }) {
  return (
    <div className="reports-main-container">
      <div className="reports-course-list">
        <ReportsCourseList userData={userData} />
      </div>
      <div className="reports-course-container">
        <Outlet />
      </div>
    </div>
  );
}
