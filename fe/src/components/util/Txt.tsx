import { HTMLAttributes } from "react";
import { fonts } from "../../constants/fonts";

interface Props extends HTMLAttributes<HTMLSpanElement> {
  typography?:
    | "bold32"
    | "bold24"
    | "bold20"
    | "bold16"
    | "bold12"
    | "medium20"
    | "medium16"
    | "medium12";
  color: string;
}
export function Txt({ typography = "medium16", color, ...props }: Props) {
  return (
    <span
      css={{
        display: "flex",
        alignItems: "center",
        margin: 0,
        padding: 0,
        color,
        ...fonts[typography],
      }}
      {...props}
    />
  );
}
