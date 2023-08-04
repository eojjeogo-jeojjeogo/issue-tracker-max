import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";

const loadingBar = (color: ColorScheme) => css`
  width: 0%;
  height: 6px;
  position: absolute;
  left: 0;
  background-color: ${color.palette.blue};
  border-radius: 4px;
  overflow: hidden;
  animation: fillLoadingBar 1s forwards;

  @keyframes fillLoadingBar {
    0% {
      width: 0%;
    }
    40% {
      width: 20%;
    }
    100% {
      width: 100%;
    }
  }
`;

export function LoadingBar() {
  const color = useTheme() as ColorScheme;

  return (
    <div
      css={{
        display: "flex",
        justifyContent: "center",
        position: "relative",
        width: "100%",
      }}>
      <div css={loadingBar(color)}></div>
    </div>
  );
}
