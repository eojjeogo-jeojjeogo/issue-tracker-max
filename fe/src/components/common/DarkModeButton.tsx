import { useTheme } from "@emotion/react";
import { ColorScheme, ThemeContext } from "../../contexts/ThemeContext";
import { useContext, useEffect, useState } from "react";
// import { Icon } from "./Icon";
import moon from "../../assets/Icons/moon.svg";
import sun from "../../assets/Icons/sun.svg";

export function DarkModeButton() {
  const color = useTheme() as ColorScheme;
  const ThemeContextValue = useContext(ThemeContext)!;
  const { toggleTheme, isDarkMode } = ThemeContextValue!;
  const [animate, setAnimate] = useState(false);

  useEffect(() => {
    if (isDarkMode !== undefined) {
      setAnimate(true);
      const timer = setTimeout(() => setAnimate(false), 1200);
      return () => clearTimeout(timer);
    }
  }, [isDarkMode]);
  return (
    <button
      onClick={toggleTheme}
      css={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        bottom: "16px",
        right: 0,
        width: "36px",
        height: "40px",
        backgroundColor: "transparent",

        borderRadius: "8px",
        transform: "translateX(-16px)",
        cursor: "pointer",
      }}>
      <img
        css={{
          "fill": color.neutral.text.weak,
          "animation": animate
            ? "slideFromLeftBottomToRightTop 1.2s ease-out"
            : "none", // animate 상태에 따라 애니메이션 적용
          "@keyframes slideFromLeftBottomToRightTop": {
            "0%": { opacity: 0, transform: "translate(-150%, 150%)" },
            "100%": { opacity: 1, transform: "translate(0, 0)" },
          },
        }}
        src={isDarkMode ? moon : sun}
        alt={isDarkMode ? "moon" : "sun"}
        width="24px"
        height="24px"
      />
    </button>
  );
}
