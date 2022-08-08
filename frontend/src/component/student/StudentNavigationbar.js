import React, { useState } from "react";
import { Button, Form, FormControl } from "react-bootstrap";
import { BoxArrowLeft, PersonCircle } from "react-bootstrap-icons";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link, useNavigate } from "react-router-dom";
import "../../styles/Navigationbar.css";

function StudentNavigationbar({ handleLogout, userName }) {
  const navigate = useNavigate();

  const [searchQuery, setSearchQuery] = useState("");

  const handleOnChange = (event) => {
    setSearchQuery(event.target.value);
  };
  const handleSearchCourse = (event) => {
    event.preventDefault();
    navigate(`/home/searchedCourses/${searchQuery}`);
  };

  return (
    <div className="nav-bar nav-bar-light student-navbar">
      <div>
        <Link to="../home">
          <span className="navbar-brand">ELMS LOGO</span>
        </Link>
      </div>
      <Form onSubmit={handleSearchCourse} className="d-flex ms-3 flex-grow-1">
        <FormControl
          className="navbar-search-courses"
          type="text"
          required
          placeholder="Search for anything"
          name="searchQuery"
          onChange={handleOnChange}
        />
        <Button className="type-1 navbar-search-course-button" type="submit">
          Search
        </Button>
      </Form>
      <div className="d-flex align-items-center navbar-right-div">
        <Link className="mx-2" to="../home/my-learning">
          My learning
        </Link>

        {/* user profile and signout drop down */}
        <NavDropdown
          className="navbar-user-name"
          title={userName ? userName : "Username"}
          menuVariant="dark"
          align="end"
        >
          <div className="p-1">
            <div className="text-center dark-gray mt-0 dropdown-menu-link">
              <Link to="/profile">
                <PersonCircle className="me-2" />
                Profile
              </Link>
            </div>
            <div
              className="text-center mt-2 border-top border-secondary dropdown-menu-link dark-gray"
              onClick={handleLogout}
            >
              <BoxArrowLeft className="me-2" />
              Sign out
            </div>
          </div>
        </NavDropdown>
      </div>
    </div>
  );
}

export default StudentNavigationbar;
