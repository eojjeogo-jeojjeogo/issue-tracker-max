import { ColorScheme } from "../contexts/ThemeContext";

const gray50 = "#FEFEFE";
const gray100 = "#F7F7FC";
const gray200 = "#EFF0F6";
const gray300 = "#D9DBE9";
const gray400 = "#BEC1D5";
const gray500 = "#A0A3BD";
const gray600 = "#6E7191";
const gray700 = "#4E4B66";
const gray800 = "#2A2A44";
const gray900 = "#14142B";

const red = "#FF3B30";
const navy = "#0025E6";
const blue = "#007AFF";
const offWhite = "#FEFEFE";

export const lightColors: ColorScheme = {
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
    offWhite,
  },
};

export const darkColors: ColorScheme = {
  neutral: {
    text: {
      weak: gray500,
      default: gray400,
      strong: gray50,
    },
    surface: {
      default: gray900,
      bold: gray700,
      strong: gray800,
    },
    border: {
      default: gray600,
      defaultActive: gray300,
    },
  },
  brand: {
    text: {
      weak: blue,
      default: gray50,
    },
    surface: {
      weak: gray900,
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
    offWhite,
  },
};

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ원본ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

// export function useColors() {
//   // This function assumes you have some way of determining whether you are currently in dark mode
//   const isDarkMode = useIsDarkMode(); // Replace with your own dark mode check

//   return isDarkMode ? colors.dark : colors.light;
// }

// const gray50 = "#FEFEFE";
// const gray100 = "#F7F7FC";
// const gray200 = "#EFF0F6";
// const gray300 = "#D9DBE9";

// const gray600 = "#6E7191";
// const gray700 = "#4E4B66";

// const gray900 = "#14142B";

// const red = "#FF3B30";
// const navy = "#0025E6";
// const blue = "#007AFF";
// const offWhite = "#FEFEFE";

// export const lightColors = {
//   gray400: "#BEC1D5",
//   gray500: "#A0A3BD",
//   gray800: "#2A2A44",
//   neutral: {
//     text: {
//       weak: gray600,
//       default: gray700,
//       strong: gray900,
//     },
//     surface: {
//       default: gray100,
//       bold: gray200,
//       strong: gray50,
//     },
//     border: {
//       default: gray300,
//       defaultActive: gray900,
//     },
//   },
//   brand: {
//     text: {
//       weak: blue,
//       default: gray50,
//     },
//     surface: {
//       weak: gray50,
//       default: blue,
//     },
//     border: {
//       default: blue,
//     },
//   },
//   danger: {
//     text: {
//       default: red,
//     },
//     border: {
//       default: red,
//     },
//   },
//   palette: {
//     blue,
//     navy,
//     red,
//     offWhite,
//   },
// };

// export const darkColors = {
//   gray400: "#BEC1D5",
//   gray500: "#A0A3BD",
//   gray800: "#2A2A44",
//   neutral: {
//     text: {
//       weak: gray600,
//       default: gray700,
//       strong: gray900,
//     },
//     surface: {
//       default: gray100,
//       bold: gray200,
//       strong: gray50,
//     },
//     border: {
//       default: gray300,
//       defaultActive: gray900,
//     },
//   },
//   brand: {
//     text: {
//       weak: blue,
//       default: gray50,
//     },
//     surface: {
//       weak: gray50,
//       default: blue,
//     },
//     border: {
//       default: blue,
//     },
//   },
//   danger: {
//     text: {
//       default: red,
//     },
//     border: {
//       default: red,
//     },
//   },
//   palette: {
//     blue,
//     navy,
//     red,
//     offWhite,
//   },
// };

// export const colors = {
//   light: lightColors,
//   dark: darkColors,
// };

// export const color = useIsDarkMode();
