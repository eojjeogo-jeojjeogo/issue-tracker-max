import { useTheme } from "@emotion/react";
import { ColorScheme, ThemeContext } from "../../contexts/ThemeContext";
import { useContext } from "react";

export function DarkModeButton() {
  const color = useTheme() as ColorScheme;
  const ThemeContextValue = useContext(ThemeContext)!;
  const { toggleTheme } = ThemeContextValue!;
  return (
    <button
      onClick={toggleTheme}
      css={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "relative",
        bottom: "20px",
        left: "50%",
        width: "36px",
        height: "40px",
        backgroundColor: "transparent",
        border: `2px solid ${color.neutral.text.weak}`,
        borderRadius: "8px",
        transform: "translateX(-40px)",
        cursor: "pointer",
      }}>
      <svg
        width="30px"
        height="30px"
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg">
        <g fill="none">
          <g fill={color.neutral.text.weak}>
            <path d="M12,22 C17.5228475,22 22,17.5228475 22,12 C22,6.4771525 17.5228475,2 12,2 C6.4771525,2 2,6.4771525 2,12 C2,17.5228475 6.4771525,22 12,22 Z M12,20.5 L12,3.5 C16.6944204,3.5 20.5,7.30557963 20.5,12 C20.5,16.6944204 16.6944204,20.5 12,20.5 Z" />
          </g>
        </g>
      </svg>
    </button>
  );
}
