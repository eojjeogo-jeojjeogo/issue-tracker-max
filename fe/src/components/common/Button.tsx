import { color } from "../../constants/colors";

type Props = {
  type?: "contained" | "outline" | "ghost";
  size?: "L" | "M" | "S";
  status?: "enabled" | "hover" | "press" | "disabled" | "selected";
  icon?: boolean;
  text?: string;
  flexible: "fixed" | "flexible";
};

export function Button({ type, size, status, icon, text, flexible }: Props) {
  return (
    <button
      css={{
        cursor: "pointer",
        border:
          type === "outline"
            ? `1px solid ${color.brand.border.default}}`
            : "none",
        borderRadius: size === "L" ? "16px" : size === "M" ? "6px" : "4px",
        width:
          flexible === "fixed"
            ? "L"
              ? "240px"
              : size === "M"
              ? "184px"
              : "128px"
            : "",
        height: size === "L" ? "56px" : size === "M" ? "48px" : "40px",
        color:
          type === "contained"
            ? color.brand.text.default
            : type === "outline"
            ? color.brand.text.weak
            : color.nuetral.text.default,
        backgroundColor:
          type === "contained"
            ? color.brand.surface.default
            : type === "outline"
            ? "#007AFF"
            : "transparent",
      }}>
      {text}
    </button>
  );
}
