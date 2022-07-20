import { useState } from "react";
import { PAYMENT_KEY, PAYMENT_KEY_SECRET } from "../utils/constants";
export default function Payment() {
  const [amount, setamount] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (amount === "") {
      alert("please enter amount");
    } else {
      let options = {
        key: PAYMENT_KEY,
        key_secret: PAYMENT_KEY_SECRET,
        amount: amount * 100,
        currency: "INR",
        name: "ELMS",
        description: "for testing purpose",
        handler: function (response) {
          alert(response.razorpay_payment_id);
        },
        prefill: {
          name: "Rohan Sohan",
          email: "rohansohan998@gmail.com",
          contact: "1234567890",
        },
        notes: {
          address: "Razorpay Corporate office",
        },
        theme: {
          color: "#A020F0",
        },
      };
      var pay = new window.Razorpay(options);
      pay.open();
    }
  };
  return (
    <div className="">
      <h2>Razorpay Payment Integration Using React</h2>
      <br />
      <input
        type="text"
        placeholder="Enter Amount"
        value={amount}
        onChange={(e) => setamount(e.target.value)}
      />
      <br />
      <br />
      <button onClick={handleSubmit}>submit</button>
    </div>
  );
}
