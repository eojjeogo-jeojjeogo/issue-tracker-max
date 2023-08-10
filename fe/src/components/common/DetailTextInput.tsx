import { useState } from "react";
import { Icon } from "./Icon";
import { ColorScheme } from "../../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { fonts } from "../../constants/fonts";
import { textInputTitle } from "../../styles/commonStyles";

type Props = {
  isDate?: boolean;
  mode: string;
  title: string;
  inputText: string;
  textColor?: string;
  widthType?: string;
  icon?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;

  placeholder?: string;
};

const detailTextInput = (
  color: ColorScheme,
  widthType?: string,
  textColor?: string
) => css`
  display: flex;
  align-items: center;
  width: ${widthType === "auto" ? "max-content" : "100%"};
  height: 40px;
  gap: 8px;
  color: ${textColor};
  border-radius: 12px;
  padding: 0px 16px;
  box-sizing: border-box;
  background-color: ${color.neutral.surface.bold};
  ${fonts.medium16};
`;

const input = (color: ColorScheme) => css`
  width: 100%;
  color: ${color.neutral.text.default};
  ${fonts.medium16};
  background-color: transparent;
  border: none;
  outline: none;
  &::placeholder {
    color: ${color.neutral.text.weak};
    opacity: 1;
  }
`;

const contentWrapper = (widthType?: string) => css`
  width: ${widthType === "auto" ? "max-content" : "100%"};
  min-width: 112px;
`;

export function DetailTextInput({
  mode,
  title,
  inputText,
  textColor,
  widthType,
  icon,
  onChange,
  placeholder,
  isDate,
}: Props) {
  const color = useTheme() as ColorScheme;
  const [isFocused, setIsFocused] = useState(false);

  const onFocusInput = () => {
    setIsFocused(true);
  };

  const onBlurInput = () => {
    setIsFocused(false);
  };

  return (
    <div css={detailTextInput(color, widthType, textColor)}>
      <div css={textInputTitle(color)}>{title}</div>
      <div className="contentWrapper" css={contentWrapper(widthType)}>
        <input
          pattern={isDate ? "^d{4}[-.]?d{2}[-.]?d{2}$" : ""}
          title={
            isDate ? "YYYY.MM.DD 또는 YYYY-MM-DD 형식으로 입력하세요." : ""
          }
          css={input(color)}
          onChange={onChange}
          onFocus={onFocusInput}
          onBlur={onBlurInput}
          type="text"
          placeholder={inputText ? inputText : isFocused ? "" : placeholder}
          value={mode === "edit" ? inputText : inputText}
        />
      </div>
      {icon && <Icon type={icon} color={textColor} />}
    </div>
  );
}
