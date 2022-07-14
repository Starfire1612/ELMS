import { fetchOtp } from "./http-requests";
import bcryptjs from "bcryptjs";

//calculate discounted price
const calculateDiscountedPrice = (price, discount) => {
  return price - Math.floor((price * discount) / 100);
};

//color for different ratings of courses
const ratingsColor = (ratings) => {
  if (ratings === 1) {
    return "danger";
  }
  if (ratings <= 2) {
    return "warning";
  }
  if (ratings === 3 || ratings === 4) {
    return "primary";
  }
  return "success";
};

//hanlde Otp and set proper states for user and loading
const requestOtp = async (setIsOtpSent, setUser, user) => {
  // if (!checkEmail || !typeMatch) {
  //   return;
  // }
  const encryptedOtp = await fetchOtp(user.email, user.type);
  if (!encryptedOtp) {
    return;
  }
  setUser((prevState) => ({
    ...prevState,
    encryptedOtp,
  }));
  console.log(encryptedOtp);
  //set isLoading to false
};

//verify whether entered OTP is correct or not and set appropriate states
const verifyOtp = async (user, setUser, setIsOtpVerified) => {
  if (!user?.otp) {
    return;
  }
  const res = await bcryptjs.compare(
    user.otp,
    user.encryptedOtp,
    (err, same) => {
      if (same) {
        setIsOtpVerified(true);
        // setMatch(true);
        setUser((prevUser) => ({
          email: prevUser.email,
          type: prevUser.type,
        }));
      } else {
        //do something is OTP entered is not correct
      }
    }
  );
  console.log(res);
};

//Validation state for Auth components
// const updateValidations = (eventName, value, setValidationFields) => {
//   setValidationFields((prevValidationFields) => ({
//     ...prevValidationFields,
//     [eventName]: value,
//   }));
// };

// // for validation of input fields in Auth components
// const validateFields = (
//   eventName,
//   value,
//   validateFields,
//   setValidationFields
// ) => {
//   console.log(eventName, value);
//   switch (eventName) {
//     case "type": {
//       if (value === "Select one") {
//         updateValidations(eventName, false, setValidationFields);
//       } else {
//         updateValidations(eventName, true, setValidationFields);
//       }
//       break;
//     }
//     case "email": {
//       if (value === "") {
//         updateValidations(eventName, false, setValidationFields);
//       } else {
//         updateValidations(eventName, true, setValidationFields);
//       }
//       break;
//     }
//     case "otp": {
//       break;
//     }
//     case "password":
//     case "confirmPassword": {
//       if (validateFields.password && validateFields.confirmPassword)
//         if (validateFields.password !== validateFields.confirmPassword) {
//           updateValidations(eventName, false, setValidationFields);
//         } else {
//           updateValidations(eventName, true, setValidationFields);
//         }
//       break;
//     }
//     default:
//   }
// };

export { calculateDiscountedPrice, ratingsColor, requestOtp, verifyOtp };
