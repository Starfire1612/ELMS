import React from "react";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { NavLink, Link } from "react-router-dom";

function Navigationbar({ handleLogout, userData }) {
  return (
    <div>
      <Navbar bg="light" expand="lg">
        <Container fluid>
          <Navbar.Brand href="#">ELMS LOGO</Navbar.Brand>
          {/* <Navbar.Toggle aria-controls="navbarScroll" />
          <Navbar.Collapse id="navbarScroll"> */}
          <Form className="d-flex">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success">Search</Button>
          </Form>
          <div>
            <NavLink to="/elms/courses">My courses</NavLink>
          </div>
          <NavDropdown
            title={userData ? userData?.name : "name"}
            id="navbarScrollingDropdown"
          >
            <Link to="/elms/profile">
              <p className="text-center hover">View Profile</p>
            </Link>

            <NavDropdown.Divider />
            <NavDropdown.Item onClick={handleLogout}>
              <Link to="/">Sign Out</Link>
            </NavDropdown.Item>
          </NavDropdown>

          {/* </Navbar.Collapse> */}
        </Container>
      </Navbar>
    </div>
  );
}

export default Navigationbar;
