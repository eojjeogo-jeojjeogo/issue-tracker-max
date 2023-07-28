import { createContext, useState, ReactNode } from "react";
import { ThemeProvider as EmotionThemeProvider } from "@emotion/react";
import { darkColors, lightColors } from "../constants/colors";

type ThemeContextType = {
  isDarkMode: boolean;
  toggleTheme: () => void;
};

type ThemeProviderProps = {
  children: ReactNode;
};

export const ThemeContext = createContext<ThemeContextType | undefined>(
  undefined
);

export function ThemeProvider({ children }: ThemeProviderProps) {
  const [isDarkMode, setIsDarkMode] = useState(false);

  const color = isDarkMode ? darkColors : lightColors;

  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  const value: ThemeContextType = {
    isDarkMode,
    toggleTheme,
  };

  return (
    <ThemeContext.Provider value={value}>
      <EmotionThemeProvider theme={color}>{children}</EmotionThemeProvider>
    </ThemeContext.Provider>
  );
}

export type ColorScheme = {
  neutral: {
    text: {
      weak: string;
      default: string;
      strong: string;
    };
    surface: {
      default: string;
      bold: string;
      strong: string;
    };
    border: {
      default: string;
      defaultActive: string;
    };
  };
  brand: {
    text: {
      weak: string;
      default: string;
    };
    surface: {
      weak: string;
      default: string;
    };
    border: {
      default: string;
    };
  };
  danger: {
    text: {
      default: string;
    };
    border: {
      default: string;
    };
  };
  palette: {
    blue: string;
    navy: string;
    red: string;
    offWhite: string;
  };
};
