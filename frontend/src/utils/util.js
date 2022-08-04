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

const compareObjectsForSorting = (object1, object2, key) => {
  // console.log(object1, object2, key);
  const obj1 = object1[key].toString().toUpperCase();
  const obj2 = object2[key].toString().toUpperCase();
  // console.log(obj1, obj2);
  if (obj1 < obj2) {
    return -1;
  }
  if (obj1 > obj2) {
    return 1;
  }
  return 0;
};

// Create our number formatter.
const formatter = (val) => {
  const numberFormatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  });
  return numberFormatter.format(val);
};
/* $2,500.00 */

export {
  calculateDiscountedPrice,
  ratingsColor,
  youtubeParser,
  convertDurationToMinutes,
  compareObjectsForSorting,
  formatter,
};
