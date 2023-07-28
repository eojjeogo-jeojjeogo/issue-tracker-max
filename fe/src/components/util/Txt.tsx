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

const fontFamily = `-apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo",
"Pretendard Variable", Pretendard, Roboto, "Noto Sans KR", "Segoe UI",
"Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
sans-serif`;

export const fonts = {
  bold32: {
    fontSize: "32px",
    fontWeight: 700,
    // lineHeight: "48px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  bold24: {
    fontSize: "24px",
    fontWeight: 700,
    // lineHeight: "36px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  bold20: {
    fontSize: "20px",
    fontWeight: 700,
    // lineHeight: "32px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  bold16: {
    fontSize: "16px",
    fontWeight: 700,
    // lineHeight: "24px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  bold12: {
    fontSize: "12px",
    fontWeight: 700,
    // lineHeight: "16px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  medium32: {
    fontSize: "32px",
    fontWeight: 500,
    // lineHeight: "48px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  medium24: {
    fontSize: "24px",
    fontWeight: 500,
    // lineHeight: "36px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  medium20: {
    fontSize: "20px",
    fontWeight: 500,
    // lineHeight: "32px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
  medium16: {
    fontSize: "16px",
    fontWeight: 500,
    // lineHeight: "24px",
    letterSpacing: 0,
    fontFamily: fontFamily,
    textAlign: "left" as const,
  },
  medium12: {
    fontSize: "12px",
    fontWeight: 500,
    // lineHeight: "16px",
    letterSpacing: 0,
    fontFamily: fontFamily,
  },
};
