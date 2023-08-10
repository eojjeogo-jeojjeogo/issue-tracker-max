import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "../common/Icon";
import { fonts } from "../../constants/fonts";
import { textInputTitle } from "../../styles/commonStyles";

const colorSelector = (color: ColorScheme) => css`
  display: flex;
  align-items: center;
  width: max-content;
  height: 40px;
  gap: 8px;
  color: ${color.neutral.text.default};
  border-radius: 12px;
  padding: 0px 16px;
  box-sizing: border-box;
  background-color: ${color.neutral.surface.bold};
  ${fonts.medium16};
`;

const contentWrapper = (color: ColorScheme) => css`
  display: flex;
  width: 112px;
  height: 24px;
  color: ${color.neutral.text.default};
  box-sizing: border-box;
`;

const input = (color: ColorScheme) => css`
  width: 100%;
  color: ${color.neutral.text.default};
  ${fonts.medium16};
  background-color: transparent;
  border: none;
  outline: none;
  &::placeholder {
    color: ${color.neutral.text.default};
    opacity: 1;
  }
`;

export function ColorSelector({
  onClickRefreshButton,
  randomColor,
}: {
  onClickRefreshButton: () => void;
  randomColor: string;
}) {
  const color = useTheme() as ColorScheme;

  
  return (
    <div css={colorSelector(color)}>
      <div css={textInputTitle(color)}>배경 색상</div>
      <div css={contentWrapper(color)}>
        <input css={input(color)} type="text" placeholder={randomColor} />
      </div>
      <div onClick={onClickRefreshButton} css={{ cursor: "pointer" }}>
        <Icon type="refreshCcw" color={color.neutral.text.default} />
      </div>
    </div>
  );
}
