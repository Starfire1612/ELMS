import { PAYMENT_KEY, PAYMENT_KEY_SECRET } from "../../utils/constants.js";
import { enrollStudentInCourse } from "./../courses/courses-util";

export const makePayment = async (
  userData,
  courseId,
  price,
  confirmationToast
) => {
  let result = null;
  let options = {
    key: PAYMENT_KEY,
    key_secret: PAYMENT_KEY_SECRET,
    amount: price * 100,
    currency: "INR",
    name: "ELMS",
    description: "for testing purpose",
    handler: async (response) => {
      let responseId = response.razorpay_payment_id;
      const paymentResponseBody = {
        paymentAmount: price,
        paymentResponseMessage: responseId,
        paymentStatus: "200",
      };
      console.log("PAYMENTID", responseId);
      result = await enrollStudentInCourse(
        userData.studentId,
        courseId,
        paymentResponseBody
      );
      confirmationToast();
      return result;
    },
    prefill: {
      name: userData.studentName,
      email: userData.studentEmail,
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
};
