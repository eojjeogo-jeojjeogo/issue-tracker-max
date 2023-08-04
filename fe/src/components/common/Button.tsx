import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "./Icon";
import { fonts } from "../../constants/fonts";

type Props = {
  onClick?: () => void;
  textColor?: string;
  type?: "contained" | "outline" | "ghost";
  size?: "L" | "M" | "S";
  status?: "enabled" | "hover" | "press" | "disabled" | "selected";
  icon?: string;
  text?: string;

  css?: any;
};

export function Button({
  onClick,
  status = "enabled",
  textColor,
  type,
  size,
  icon,
  text,
  css,
}: Props) {
  const color = useTheme() as ColorScheme;

  const isGhost = type === "ghost";

  const ICON_COLOR_VARIANT = {
    contained: color.brand.text.default,
    outline: color.brand.text.weak,
    ghost: color.neutral.text.default,
  };
  const TYPE_VARIANT = {
    contained: {
      color: color.brand.text.default,
      backgroundColor: color.brand.surface.default,
    },
    outline: {
      color: color.brand.text.weak,
      backgroundColor: "transparent",
    },
    ghost: {
      color:
        status === "selected"
          ? color.neutral.text.strong
          : color.neutral.text.default,
      backgroundColor: "transparent",
    },
  };
  const STATUS_VARIANT = {
    enabled: "1",
    hover: "0.8",
    press: "0.64",
    disabled: "0.32",
    selected: "1",
  };

  const SIZE_VARIANT = {
    L: {
      width: isGhost ? "" : "240px",
      height: isGhost ? "" : "56px",
      ...(isGhost
        ? status === "selected"
          ? fonts.bold20
          : fonts.medium20
        : fonts.medium20),
    },
    M: {
      width: isGhost ? "" : "184px",
      height: isGhost ? "" : "48px",
      ...(isGhost
        ? status === "selected"
          ? fonts.bold16
          : fonts.medium16
        : fonts.medium16),
    },
    S: {
      width: isGhost ? "" : "128px",
      minWidth: "41px",
      height: isGhost ? "" : "40px",
      ...fonts.medium12,
    },
  };

  return (
    <button
      onClick={onClick}
      css={{
        ...css,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        cursor: "pointer",
        gap: "4px",
        ...TYPE_VARIANT[type!],
        ...SIZE_VARIANT[size!],
        borderRadius: "12px",
        opacity: STATUS_VARIANT[status],
        color: textColor ? textColor : TYPE_VARIANT[type!].color,
        border:
          type === "outline"
            ? `1px solid ${color.brand.border.default}}`
            : "none",
      }}>
      {icon && (
        <Icon
          type={icon}
          color={textColor ? textColor : ICON_COLOR_VARIANT[type!]}
        />
      )}
      {text}
    </button>
  );
}
