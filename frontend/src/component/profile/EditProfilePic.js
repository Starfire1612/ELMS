import React, { useState } from "react";
import HeaderSection from "./HeaderSection.js";
import { Form, Button } from "react-bootstrap";
import { postProfilePic } from "../profile/profile-utils.js";

export default function EditProfilePic({ userData, reFetchUser }) {
  const [profilePic, setProfilePic] = useState(
    "https://img-c.udemycdn.com/user/200_H/anonymous_3.png"
  );

  const userType = localStorage.getItem("userType");

  const [image, setImage] = useState(null);

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
  const updateProfilePic = async () => {
    if (!image) return;
    const res = await postProfilePic(userType, userData, image);
    reFetchUser();
    console.log("From Profile page", res);
  };
  return (
    <div className="edit-profile-container">
      <HeaderSection
        title="Photo"
        subtitle="Add a nice photo of yourself for your profile."
      />
      <div className="content-section-container">
        <p className="input-label">Image Preview:</p>
        <div className="image-preview-container">
          <img
            className="image-preview"
            src={
              profilePic === ""
                ? "data:image/png;base64," + userData[`${userType}Image`]
                : profilePic
            }
            alt=""
          />
        </div>

        <p className="input-label mt-3">Add / Change Image:</p>
        <Form.Control
          type="file"
          accept="image/*"
          onChange={handleImageChange}
        />
      </div>
      <div className="content-section-container">
        <Button className="type-1 mx-auto d-block" onClick={updateProfilePic}>
          Save
        </Button>
      </div>
    </div>
  );
}
