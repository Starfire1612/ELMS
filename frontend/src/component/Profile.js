import axios from "axios";
import React from "react";

const baseURL = "http://localhost:8100/course/2/feedback";

function Profile() {
  const [post, setPost] = React.useState(null);

  React.useEffect(() => {
    axios.get(baseURL).then((response) => {
      console.log(response.data);
      setPost(response.data);
    });
  }, []);

  if (!post) return null;

  return (
    <div>   
    </div>
  );
}

export default Profile;
// import React from "react";
// import { useEffect } from "react";
// import {axios} from "axios";


// const config= async ()=>{
//   await axios.get("http://localhost:8100/course/2/feedback")
//   .then(response=>console.log(response))
//   .catch(e=>console.log(e));
// }

// function Profile() {
//   useEffect(()=>{
//     config();
//   },[]);
//   return <div>Profile</div>;
// }

// export default Profile;
