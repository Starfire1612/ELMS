import React, { useState, useEffect } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfilePic } from "../profile/profile-utils.js";
import { ClipLoader } from "react-spinners";

export default function EditProfilePic({ userData, reFetchUser }) {
  const userType = localStorage.getItem("userType");
  const [image, setImage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  //0-> default, 1-> password successfullt changed -1->error occured
  const [progress, setProgress] = useState(0);

  const [profilePic, setProfilePic] = useState(
    "https://img-c.udemycdn.com/user/200_H/anonymous_3.png"
  );

  useEffect(() => {
    setProfilePic("data:image/png;base64," + userData[`${userType}Image`]);
  }, [userData]);

  const handleImageChange = (event) => {
    const image = event.target.files[0];
    setImage(image);
    //convert image to base64 url
    const reader = new FileReader();
    reader.onloadend = () => {
      console.log(reader.result);
      setProfilePic(reader.result);
    };
    reader.readAsDataURL(image);
    setImage(image);
    // reader.readAsBinaryString(image);
  };
  const updateProfilePic = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    if (!image) return;
    const res = await postProfilePic(userType, userData, image);
    if (res) {
      setProgress(1);
      setTimeout(() => {
        setProgress(0);
      }, 4000);
      reFetchUser();
    } else {
      setProgress(-1);
      setTimeout(() => {
        setProgress(0);
      }, 4000);
    }
    setIsLoading(false);
  };
  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Photo"
        subtitle="Add a nice photo of yourself for your profile."
      />
      <Form onSubmit={updateProfilePic}>
        <div className="content-section-container">
          <p className="input-label">Image Preview:</p>
          <div className="image-preview-container">
            <img className="image-preview" src={profilePic} alt="" />
          </div>
          {progress === -1 && (
            <p className="text-center font-monospace text-danger">
              Something went wrong! try again.
            </p>
          )}
          <p className="input-label mt-3">Add / Change Image:</p>
          <Form.Control
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            required
            disabled={isLoading}
          />
        </div>
        <div className="content-section-container">
          {progress === 1 && (
            <p className="text-center font-monospace text-success">
              Profile image updated successfully
            </p>
          )}
          <Button
            disabled={isLoading}
            type="submit"
            className="type-1 mx-auto d-block"
          >
            Save
            {isLoading && (
              <ClipLoader className="ms-2" color="white" size={15} />
            )}
          </Button>
        </div>
      </Form>
    </div>
  );
}
