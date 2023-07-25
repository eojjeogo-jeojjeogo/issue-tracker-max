import React from "react";
import { color } from "../../constants/colors";
import { Icon } from "./Icon";

type Props = {
  textColor?: string;
  type?: "contained" | "outline" | "ghost";
  size?: "L" | "M" | "S";
  status?: "enabled" | "hover" | "press" | "disabled" | "selected";
  icon?: string;
  text?: string;
  flexible: "fixed" | "flexible";
  css?: any;
};

export function Button({
  textColor,
  type,
  size,
  status,
  icon,
  text,
  flexible,
  css,
}: Props) {
  const iconColor = textColor
    ? textColor
    : type === "contained"
    ? color.brand.text.default
    : type === "outline"
    ? color.brand.text.weak
    : color.neutral.text.default;

  return (
    <button
      css={{
        ...css,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        cursor: "pointer",
        gap: "4px",
        border:
          type === "outline"
            ? `1px solid ${color.brand.border.default}}`
            : "none",
        borderRadius: "12px",
        width:
          flexible === "fixed"
            ? size === "L"
              ? "240px"
              : size === "M"
              ? "184px"
              : size === "S"
              ? "128px"
              : ""
            : "",
        height: size === "L" ? "56px" : size === "M" ? "48px" : "40px",
        color: textColor
          ? textColor
          : type === "contained"
          ? color.brand.text.default
          : type === "outline"
          ? color.brand.text.weak
          : color.neutral.text.default,
        backgroundColor:
          type === "contained"
            ? color.brand.surface.default
            : type === "outline"
            ? "#007AFF"
            : "transparent",
      }}>
      {icon && <Icon type={icon} color={iconColor} />}
      {text}
    </button>
  );
}
