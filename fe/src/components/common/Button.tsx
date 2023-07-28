// import { color } from "../../constants/colors";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { fonts } from "../util/Txt";
import { Icon } from "./Icon";

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
      color: color.neutral.text.default,
      backgroundColor: "transparent",
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

const STATUS_VARIANT = {
  enabled: "1",
  hover: "0.8",
  press: "0.64",
  disabled: "0.32",
  selected: "1",
};

const SIZE_VARIANT = {
  L: {
    width: "240px",
    height: "56px",
    ...fonts.medium20,
  },
  M: {
    width: "184px",
    height: "48px",
    ...fonts.medium16,
  },
  S: {
    width: "128px",
    height: "40px",
    ...fonts.medium12,
  },
};
