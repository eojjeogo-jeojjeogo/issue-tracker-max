import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { fonts } from "../util/Txt";

import { DropdownItems } from "./DropdownItem";

export function Dropdown({
  isDropdownOpen,
  title,
  items = [],
  // multiSelect = false,
  isDark,
  onClick,
}: {
  isDropdownOpen: boolean;
  title: string;
  items: string[];
  multiSelect: boolean;
  isDark?: boolean | "";
  onClick: () => void;
}) {
  const color = useTheme() as ColorScheme;
  if (!isDropdownOpen) return;

  return (
    <div
      className="dropdown"
      css={{
        position: "absolute",
        width: "240px",
        height: "max-content",
        left: "50%",
        border: `1px solid ${color.neutral.border.default}`,
        borderRadius: "16px",
        overflow: "hidden",
        transform: "translate(-17%, 0)",
      }}>
      <div
        className="ddHeader"
        css={{
          display: "flex",
          justifyContent: "flex-start",
          alignItems: "center",
          width: "100%",
          height: "32px",
          color: color.neutral.text.weak,
          padding: "8px 16px",
          boxSizing: "border-box",
          ...fonts.medium12,
          backgroundColor: color.neutral.surface.default,
        }}>
        <div className="ddHeaderTitle">{title}</div>
      </div>
      <ul className="ddList">
        {items.map((item) => {
          const isSelected = isDark
            ? item === "밝은색"
              ? false
              : true
            : item === "어두운색"
            ? false
            : true;
          return (
            <DropdownItems
              key={item}
              onClick={onClick}
              isSelected={isSelected}
              item={item}
            />
          );
        })}
      </ul>
    </div>
  );
}
