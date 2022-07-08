import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import React from "react";
import { useState, useEffect } from "react";

function Courses() {
  const [name, setName] = useState([]);
  useEffect(() => {
    names();
  }, []);

  const names = async () => {
    const responce = await fetch(
      "https://jsonplaceholder.typicode.com/photos/"
    );
    setName(await responce.json());
  };
  // const card = (photo) => {
  //   <Card style={{ width: "18rem" }}>
  //     {/* <Card.Img variant="top" src={photo.thumbnailUrl} /> */}
  //     <Card.Body>
  //       <Card.Title>{photo.title}</Card.Title>
  //       <Card.Text>
  //         Some quick example text to build on the card title and make up the
  //         bulk of the card's content. Some quick example text to build on the
  //         card title and make up the bulk of the card's content.
  //       </Card.Text>
  //       <Button variant="primary">Connect</Button>
  //     </Card.Body>
  //   </Card>;
  // };

  return (
    <div>
      <h1> Name is writen hare bilow </h1>
      <ol className="list-group list-group-numbered">
        {name.map((data) => {
          return (
            <Card style={{ width: "18rem" }}>
              <Card.Img variant="top" src={data.thumbnailUrl} />
              <Card.Body>
                <Card.Title>{data.title}</Card.Title>
                <Card.Text>
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content. Some quick example text to
                  build on the card title and make up the bulk of the card's
                  content.
                </Card.Text>
                <Button variant="primary">Connect</Button>
              </Card.Body>
            </Card>
          );
        })}
      </ol>
    </div>
  );
}

export default Courses;
