import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";
import ShowFeedback from "../student/Components/ShowFeedback.js";
import './TabB.css';

// import Sonnet from '../../components/Sonnet';

function TabsB(props) {
  return (
    <div className="tabs-b">
    <Tabs
      defaultActiveKey="Description"
      id="uncontrolled-tab-example"
      className="mb-3"
    >

      <Tab className="tab-desc"  eventKey="Description" title="Overview">
        <div className="tab-desc text-sm"><h2>{props.desc}</h2></div>
      </Tab>
{/*    
      <Tab  eventKey="Feedback" title="Feedback" >
        <ShowFeedback feedbacks={props.feedbacks}/>
      </Tab> */}
    </Tabs>
    </div>
  );
}

export default TabsB;
