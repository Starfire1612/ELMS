import './App.css';
import React,{useState} from 'react';
function App() {
  const [amount, setamount] = useState('');

  const handleSubmit = (e)=>{
    e.preventDefault();
    if(amount === ""){
    alert("please enter amount");
    }else{
      var options = {
        key: "rzp_test_ft7szNW60HgFCv",
        key_secret:"XzcapfpBoumpHXrgxi7KcreU",
        amount: amount *100,
        currency:"INR",
        name:"ELMS",
        description:"for testing purpose",
        handler: function(response){
          alert(response.razorpay_payment_id);
        },
        prefill: {
          name:"Radhika Shah",
          email:"radhikashah1612@gmail.com",
          contact:"9374620106"
        },
        notes:{
          address:"Razorpay Corporate office"
        },
        theme: {
          color:"#A020F0"
        }
      };
      var pay = new window.Razorpay(options);
      pay.open();
    }
  }
  return (
    <div className="App">
     <h2>Razorpay Payment Integration Using React</h2>
     <br/>
     <input type="text"placeholder='Enter Amount' value={amount} onChange={(e)=>setamount(e.target.value)} />
     <br/><br/>
     <button onClick={handleSubmit}>submit</button>
    </div>
  );
}

export default App;
