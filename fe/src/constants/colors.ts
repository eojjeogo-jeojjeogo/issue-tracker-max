const gray50 = "#FEFEFE";
const gray100 = "#F7F7FC";
const gray200 = "#EFF0F6";
const gray300 = "#D9DBE9";

const gray600 = "#6E7191";
const gray700 = "#4E4B66";

const gray900 = "#14142B";

const red = "#FF3B30";
const navy = "#0025E6";
const blue = "#007AFF";

export const color = {
  gray400: "#BEC1D5",
  gray500: "#A0A3BD",
  gray800: "#2A2A44",
  neutral: {
    text: {
      weak: gray600,
      default: gray700,
      strong: gray900,
    },
    surface: {
      default: gray100,
      bold: gray200,
      strong: gray50,
    },
    border: {
      default: gray300,
      defaultActive: gray900,
    },
  },
  brand: {
    text: {
      weak: blue,
      default: gray50,
    },
    surface: {
      weak: gray50,
      default: blue,
    },
    border: {
      default: blue,
    },
  },
  danger: {
    text: {
      default: red,
    },
    border: {
      default: red,
    },
  },
  palette: {
    blue,
    navy,
    red,
  },
};
