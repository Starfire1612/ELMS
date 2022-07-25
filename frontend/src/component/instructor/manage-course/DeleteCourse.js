import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { ClipLoader } from "react-spinners";
import "../../../styles/manage-course/ManageCourse.css";

function DeleteCourse({ showDelete, setShowDelete, handleDeleteCourse }) {
  const [deleteState, setDeleteState] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleClose = () => setShowDelete(false);

  const handleDelete = async () => {
    setIsLoading(true);
    await handleDeleteCourse();
    setIsLoading(false);
    setShowDelete(false);
  };
  const handleChange = (event) => {
    setDeleteState(event.target.value);
  };

  return (
    <>
      <Modal
        show={showDelete}
        onHide={handleClose}
        backdrop="static"
        centered
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title className="dark-gray ms-3">Delete Course</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p className="dark-gray mb-0">
            Course will be deleted permanently. Type{" "}
            <span className="fst-italic text-secondary">delete</span> to
            confirm.
          </p>
          <input
            className="form-control input-delete"
            type="text"
            onChange={handleChange}
            placeholder="delete"
          />
        </Modal.Body>
        <Modal.Footer>
          <Button className="type-3" onClick={handleClose}>
            Cancel
          </Button>
          <Button
            className="type-1"
            onClick={handleDelete}
            disabled={deleteState !== "delete" || isLoading}
          >
            Confirm
            {isLoading && (
              <ClipLoader className="ms-1" size="15px" color="white" />
            )}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default DeleteCourse;
