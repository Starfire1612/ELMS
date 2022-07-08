//calculate discounted price
const calculateDiscountedPrice = (price, discount) => {
  return price - Math.floor((price * discount) / 100);
};

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

export { calculateDiscountedPrice, ratingsColor };
