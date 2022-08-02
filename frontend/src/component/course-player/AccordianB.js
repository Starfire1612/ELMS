import Accordion from 'react-bootstrap/Accordion';

function AccordianB(props) {
  return (
            <div className='accordion-bootstrap'>
            <Accordion defaultActiveKey="0">
              <Accordion.Item eventKey="0">
                <Accordion.Header className="d-flex justify-content-center">COURSE DESCRIPTION</Accordion.Header>
                <Accordion.Body>
                {props.desc}
                </Accordion.Body>
              </Accordion.Item>
              <Accordion.Item eventKey="1">
                <Accordion.Header>FEEDBACKS</Accordion.Header>
                <Accordion.Body>
                         <ul>
                            <li><h3>{props.feedback[0].name} - {props.feedback[0].Feedback} {props.feedback[0].ratings}</h3>  
                            </li>
                            <li><h3>{props.feedback[1].name} - {props.feedback[1].Feedback} {props.feedback[0].ratings}</h3>
                            </li>
                            <li><h3>{props.feedback[2].name} - {props.feedback[2].Feedback} {props.feedback[0].ratings}</h3> 
                            </li>
                            <li><h3>{props.feedback[3].name} - {props.feedback[3].Feedback} {props.feedback[0].ratings}</h3>
                            </li>
                        </ul>
                </Accordion.Body>
              </Accordion.Item>
            </Accordion>
            </div>
          );
}

export default AccordianB;