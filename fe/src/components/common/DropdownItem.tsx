import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { fonts } from "../util/Txt";
import { Icon } from "./Icon";
import { useState } from "react";

export function DropdownItems({
  item,
  isSelected,
  onClick,
}: {
  item: string;
  isSelected: boolean;
  onClick?: () => void;
}) {
  const color = useTheme() as ColorScheme;
  const [isHover, setIsHover] = useState(false);

  const onMouseEnter = () => {
    setIsHover(true);
  };

  const onMouseLeave = () => {
    setIsHover(false);
  };

  const onClickItem = () => {
    if (!isSelected) onClick && onClick();
  };

  return (
    <li
      onClick={onClickItem}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
      className="ddListItem"
      css={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        cursor: "pointer",
        width: "100%",
        height: "40px",
        borderTop: `1px solid ${color.neutral.border.default}`,
        padding: "8px 16px",
        boxSizing: "border-box",
        gap: "8px",
        backgroundColor: isHover
          ? color.neutral.surface.bold
          : color.neutral.surface.strong,
      }}>
      {/* <Icon type="userImageSmall" color={color.neutral.surface.bold} /> */}
      <div
        css={{
          display: "flex",
          alignItems: "center",
          width: "156px",
          height: "24px",
          color: color.neutral.text.default,
          ...(isSelected ? fonts.bold16 : fonts.medium16),
        }}>
        {item}
      </div>
      <Icon
        type={isSelected ? "checkOnCircle" : "checkOffCircle"}
        color={color.neutral.text.default}
      />
    </li>
  );
}
