import React from "react";
import image1 from "../../static/images/image-1.jpg";
import image2 from "../../static/images/image-2.jpg";
import image3 from "../../static/images/image-3.jpg";
import { Carousel } from "react-bootstrap";

export default function DashboardCarousel() {
  return (
    <Carousel>
      <Carousel.Item>
        <img
          className="d-block w-100 carousel-image"
          src={image1}
          alt="First slide"
        />
        <Carousel.Caption>
          <h5>First slide label</h5>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100 carousel-image"
          src={image2}
          alt="Second slide"
        />
        <Carousel.Caption>
          <h5>Second slide label</h5>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100 carousel-image"
          src={image3}
          alt="Third slide"
        />
        <Carousel.Caption>
          <h5>Third slide label</h5>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}
