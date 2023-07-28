// import { color } from "../../constants/colors";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "../common/Icon";
import { fonts } from "../util/Txt";

export function ColorSelector({
  onClickRefreshButton,
  randomColor,
}: {
  onClickRefreshButton: () => void;
  randomColor: string;
}) {
  const color = useTheme() as ColorScheme;

  return (
    <div
      css={{
        display: "flex",
        alignItems: "center",

        width: "max-content",
        height: "40px",
        gap: "8px",
        color: color.neutral.text.default,
        borderRadius: "12px",
        padding: "0px 16px",
        boxSizing: "border-box",
        backgroundColor: color.neutral.surface.bold,
        ...fonts.medium16,
      }}>
      <div
        className="title"
        css={{
          display: "flex",
          width: "64px",
          gap: "8px",
          color: color.neutral.text.weak,
          ...fonts.medium12,
        }}>
        배경 색상
      </div>
      <div
        className="contentWrapper"
        css={{
          display: "flex",
          width: "112px",
          // minWidth: "112px",
          height: "24px",
          color: color.neutral.text.default,
          boxSizing: "border-box",
        }}>
        <input
          css={{
            "width": "100%",
            "color": color.neutral.text.default,
            ...fonts.medium16,
            "backgroundColor": "transparent",
            "border": "none",
            "outline": "none",
            "&::placeholder": {
              color: color.neutral.text.default,
              opacity: 1, // 필요하다면 투명도 조절 가능
            },
          }}
          type="text"
          placeholder={randomColor}
        />
      </div>
      <div onClick={onClickRefreshButton} css={{ cursor: "pointer" }}>
        <Icon type="refreshCcw" color={color.neutral.text.default} />
      </div>
    </div>
  );
}
