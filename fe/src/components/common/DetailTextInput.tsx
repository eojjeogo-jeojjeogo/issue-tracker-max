import { useState } from "react";
// import { color } from "../../constants/colors";
import { fonts } from "../util/Txt";
import { Icon } from "./Icon";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useTheme } from "@emotion/react";

type Props = {
  mode: string;
  title: string;
  inputText: string;
  textColor?: string;
  widthType?: string;
  icon?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;

  placeholder?: string;
};

export function DetailTextInput({
  mode,
  title,
  inputText,
  textColor,
  widthType,
  icon,
  onChange,
  placeholder,
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
    <div
      css={{
        display: "flex",
        alignItems: "center",
        width: widthType === "auto" ? "max-content" : "100%",
        height: "40px",
        gap: "8px",
        color: textColor,
        borderRadius: "12px",
        padding: "0px 16px",
        boxSizing: "border-box",
        backgroundColor: color.neutral.surface.bold,
        ...fonts.medium16,
      }}>
      <div
        className="title"
        css={{
          color: color.neutral.text.weak,
          display: "flex",
          width: "64px",
          gap: "8px",
          ...fonts.medium12,
        }}>
        {title}
      </div>

      <div
        className="contentWrapper"
        css={{
          width: widthType === "auto" ? "max-content" : "100%",
          minWidth: "112px",
        }}>
        <input
          onChange={onChange}
          onFocus={onFocusInput}
          onBlur={onBlurInput}
          css={{
            "width": "100%",
            "color": color.neutral.text.default,
            ...fonts.medium16,
            "backgroundColor": "transparent",
            "border": "none",
            "outline": "none",
            "&::placeholder": {
              color: "원하는 색깔",
              opacity: 1,
            },
          }}
          type="text"
          placeholder={inputText ? inputText : isFocused ? "" : placeholder}
          value={mode === "edit" ? inputText : inputText}
        />
      </div>
      {icon && <Icon type={icon} color={textColor} />}
    </div>
  );
}
