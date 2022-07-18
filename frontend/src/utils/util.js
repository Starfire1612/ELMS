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

//Validates YouTube video url and returns videoId
const youtubeParser = (url) => {
  let regExp =
    /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#&?]*).*/;
  let match = url.match(regExp);
  return match && match[7].length === 11 ? match[7] : false;
};

//converts ISO8601 time format to minutes
const convertDurationToMinutes = (duration) => {
  let day_time = duration.split("T");
  let day_duration = day_time[0].replace("P", "");
  let day_list = day_duration.split("D");
  let day, hour, minute, second;
  if (day_list.length === 2) {
    day = parseInt(day_list[0]) * 60 * 60 * 24;
    day_list = day_list[1];
  } else {
    day = 0;
    day_list = day_list[0];
  }
  let hour_list = day_time[1].split("H");
  if (hour_list.length === 2) {
    hour = parseInt(hour_list[0]) * 60 * 60;
    hour_list = hour_list[1];
  } else {
    hour = 0;
    hour_list = hour_list[0];
  }
  let minute_list = hour_list.split("M");
  if (minute_list.length === 2) {
    minute = parseInt(minute_list[0]) * 60;
    minute_list = minute_list[1];
  } else {
    minute = 0;
    minute_list = minute_list[0];
  }
  let second_list = minute_list.split("S");
  if (second_list.length === 2) {
    second = parseInt(second_list[0]);
  } else second = 0;
  return parseInt(day + hour + minute + second) / 60;
};

// compare 2 objects in javascript
function compareObjects() {
  var i, l, leftChain, rightChain;

  const compare2Objects = (x, y) => {
    var p;

    // remember that NaN === NaN returns false
    // and isNaN(undefined) returns true
    if (
      isNaN(x) &&
      isNaN(y) &&
      typeof x === "number" &&
      typeof y === "number"
    ) {
      return true;
    }

    // Compare primitives and functions.
    // Check if both arguments link to the same object.
    // Especially useful on the step where we compare prototypes
    if (x === y) {
      return true;
    }

    // Works in case when functions are created in constructor.
    // Comparing dates is a common scenario. Another built-ins?
    // We can even handle functions passed across iframes
    if (
      (typeof x === "function" && typeof y === "function") ||
      (x instanceof Date && y instanceof Date) ||
      (x instanceof RegExp && y instanceof RegExp) ||
      (x instanceof String && y instanceof String) ||
      (x instanceof Number && y instanceof Number)
    ) {
      return x.toString() === y.toString();
    }

    // At last checking prototypes as good as we can
    if (!(x instanceof Object && y instanceof Object)) {
      return false;
    }

    if (x.isPrototypeOf(y) || y.isPrototypeOf(x)) {
      return false;
    }

    if (x.constructor !== y.constructor) {
      return false;
    }

    if (x.prototype !== y.prototype) {
      return false;
    }

    // Check for infinitive linking loops
    if (leftChain.indexOf(x) > -1 || rightChain.indexOf(y) > -1) {
      return false;
    }

    // Quick checking of one object being a subset of another.
    // todo: cache the structure of arguments[0] for performance
    for (p in y) {
      if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
        return false;
      } else if (typeof y[p] !== typeof x[p]) {
        return false;
      }
    }

    for (p in x) {
      if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
        return false;
      } else if (typeof y[p] !== typeof x[p]) {
        return false;
      }

      switch (typeof x[p]) {
        case "object":
        case "function":
          leftChain.push(x);
          rightChain.push(y);

          if (!compare2Objects(x[p], y[p])) {
            return false;
          }

          leftChain.pop();
          rightChain.pop();
          break;

        default:
          if (x[p] !== y[p]) {
            return false;
          }
          break;
      }
    }

    return true;
  };

  if (arguments.length < 1) {
    return true; //Die silently? Don't know how to handle such case, please help...
    // throw "Need two or more arguments to compare";
  }

  for (i = 1, l = arguments.length; i < l; i++) {
    leftChain = []; //Todo: this can be cached
    rightChain = [];

    if (!compare2Objects(arguments[0], arguments[i])) {
      return false;
    }
  }

  return true;
}

export {
  calculateDiscountedPrice,
  ratingsColor,
  requestOtp,
  verifyOtp,
  youtubeParser,
  convertDurationToMinutes,
  compareObjects,
};
