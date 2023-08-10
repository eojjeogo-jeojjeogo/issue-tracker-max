import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";

const mainArea = (color: ColorScheme) => css`
  display: flex;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  flex-direction: column;
  padding: 32px 80px;
  overflow-y: auto;

  &::-webkit-scrollbar {
    width: 8px;
  }

  &::-webkit-scrollbar-thumb {
    background-color: ${color.neutral.border.default};
    border-radius: 5px; // 스크롤바 둥글게
  }
`;

export function MainArea({ children }: { children: React.ReactNode }) {
  const color = useTheme() as ColorScheme;

  return <div css={mainArea(color)}>{children}</div>;
}
