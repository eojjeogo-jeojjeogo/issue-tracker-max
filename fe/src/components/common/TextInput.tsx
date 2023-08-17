import { useState } from "react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { fonts } from "../../constants/fonts";

export type InputState = "enabled" | "active" | "disabled" | "error";

const textInputContainer = (
  color: ColorScheme,
  inputState: string,
  width: string,
  height: number
) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: start;
  position: relative;
  width: ${width};
  height: ${height}px;
  border: ${inputState === "active"
    ? `1px solid ${color.neutral.border.defaultActive}`
    : inputState === "error"
    ? `1px solid ${color.danger.border.default}`
    : "none"};
  padding: 0 16px;
  border-radius: 16px;
  box-sizing: border-box;
  background-color: ${inputState === "enabled"
    ? color.neutral.surface.bold
    : inputState === "disabled"
    ? color.neutral.surface.bold
    : color.neutral.surface.strong};
`;

const input = (color: ColorScheme, inputState: string) => css`
  ...${fonts.medium16};
  outline: none;
  width: 100%;
  height: 24px;
  color: ${
    inputState === "enabled"
      ? color.neutral.text.default
      : inputState === "disabled"
      ? color.neutral.text.weak
      : color.neutral.text.strong
  };
  font-weight: ${fonts.medium16.fontWeight};
  font-size: ${fonts.medium16.fontSize};
  border: none;
  background-color: transparent;
  
  ::placeholder {
    color: ${color.neutral.text.weak};
  }
`;

export function TextInput({
  isPassword,
  width,
  height,
  placeholder,
  onChange,
  isFormValid,
  inputValue,
  caption,
}: {
  isPassword?: boolean;
  width: string;
  height: number;
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  isFormValid?: boolean;
  inputValue?: string;
  caption?: string;
}) {
  const [inputState, setInputState] = useState<InputState>("enabled");

  const color = useTheme() as ColorScheme;

  const onFocus = () => {
    setInputState("active");
  };

  const onBlur = () => {
    setInputState("enabled");
  };

  return (
    <>
      <div css={textInputContainer(color, inputState, width, height)}>
        {inputState === "active" && (
          <div
            css={{
              ...fonts.medium12,
              color: color.neutral.text.weak,
            }}>
            {placeholder}
          </div>
        )}
        <input
          value={inputValue ? inputValue : ""}
          onFocus={onFocus}
          onBlur={onBlur}
          onChange={onChange}
          placeholder={placeholder}
          css={input(color, inputState)}
          type={isPassword ? "password" : "text"}
        />
        {isFormValid ? null : inputValue ? (
          <div
            css={{
              display: "flex",

              position: "absolute",
              ...fonts.medium12,
              color: color.danger.text.default,
              marginTop: "70px",
            }}>
            {caption}
          </div>
        ) : null}
      </div>
    </>
  );
}
