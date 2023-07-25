import { HTMLAttributes } from "react";

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
        margin: 0,
        padding: 0,

        color,
        ...fonts[typography],
      }}
      {...props}
    />
  );
}

export const fonts = {
  bold32: {
    fontSize: "32px",
    fontWeight: 700,
    lineHeight: "48px",
    letterSpacing: 0,
  },
  bold24: {
    fontSize: "24px",
    fontWeight: 700,
    lineHeight: "36px",
    letterSpacing: 0,
  },
  bold20: {
    fontSize: "20px",
    fontWeight: 700,
    lineHeight: "32px",
    letterSpacing: 0,
  },
  bold16: {
    fontSize: "16px",
    fontWeight: 700,
    lineHeight: "24px",
    letterSpacing: 0,
  },
  bold12: {
    fontSize: "12px",
    fontWeight: 700,
    lineHeight: "16px",
    letterSpacing: 0,
  },
  medium32: {
    fontSize: "32px",
    fontWeight: 500,
    lineHeight: "48px",
    letterSpacing: 0,
  },
  medium24: {
    fontSize: "24px",
    fontWeight: 500,
    lineHeight: "36px",
    letterSpacing: 0,
  },
  medium20: {
    fontSize: "20px",
    fontWeight: 500,
    lineHeight: "32px",
    letterSpacing: 0,
  },
  medium16: {
    fontSize: "16px",
    fontWeight: 500,
    lineHeight: "24px",
    letterSpacing: 0,
    textAlign: "left" as const,
  },
  medium12: {
    fontSize: "12px",
    fontWeight: 500,
    lineHeight: "16px",
    letterSpacing: 0,
  },
};
