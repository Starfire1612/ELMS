import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import Description from "../student/Components/Description.js";
import ShowFeedback from "../student/Components/ShowFeedback.js";
import './TabB.css';

// import Sonnet from '../../components/Sonnet';
import Feedback from './../feedback/feedback';

function TabsB(props) {
  return (
    <div className="tabs-b">
    <Tabs
      defaultActiveKey="Description"
      id="uncontrolled-tab-example"
      className="mb-3"
      justify
    >

      <Tab tabClassName="tab-text-color" eventKey="Description" title="Overview">
      <Description desc={props.desc}/>
      </Tab>
   
      <Tab tabClassName="tab-text-color" eventKey="Feedback" title="Feedback" >
        <Feedback userName={props.userData.studentName}/>
        <ShowFeedback feedbacks={props.feedbacks}/>
      </Tab>
    </Tabs>
    </div>
  );
}

export default TabsB;
