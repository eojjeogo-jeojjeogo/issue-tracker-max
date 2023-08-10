import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "./Icon";
import { useState } from "react";
import { fonts } from "../../constants/fonts";

type Props = {
  item: { title: string; icon: string | null; color: string | null };
  isSelected: boolean;
  onClick?: (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => void;
};

const dropdownItemStyle = (color: ColorScheme, isHover: boolean) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  width: 100%;
  height: 40px;
  border-top: 1px solid ${color.neutral.border.default};
  padding: 8px 16px;
  box-sizing: border-box;
  gap: 8px;
  background-color: ${isHover
    ? color.neutral.surface.bold
    : color.neutral.surface.strong};
`;

const imageStyle = css`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
`;
const colorCircleStyle = (
  item: {
    title: string;
    icon: string | null;
    color: string | null;
  },
  color: ColorScheme
) => css`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  box-sizing: border-box;
  border: 1.6px solid
    ${item.color === "#FEFEFE" ? color.neutral.border.default : "none"};
  background-color: ${item.color};
`;

const textContentStyle = (color: ColorScheme, isSelected: boolean) => css`
  display: flex;
  align-items: center;
  width: 156px;
  height: 24px;
  color: ${color.neutral.text.default};
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  ${isSelected ? fonts.bold16 : fonts.medium16};
`;

export function DropdownItems({ item, isSelected, onClick }: Props) {
  const [isHover, setIsHover] = useState(false);

  const color = useTheme() as ColorScheme;

  const onMouseEnter = () => {
    setIsHover(true);
  };

  const onMouseLeave = () => {
    setIsHover(false);
  };

  const onClickItem = () => {
    if (onClick) onClick(item);
  };

  return (
    <li
      onClick={onClickItem}
      onMouseEnter={onMouseEnter}
      onMouseLeave={onMouseLeave}
      className="ddListItem"
      css={dropdownItemStyle(color, isHover)}>
      {(item.icon || item.color) && (
        <>
          {item.icon && <img css={imageStyle} src={item.icon} alt="icon" />}
          {item.color && <div css={colorCircleStyle(item, color)} />}
        </>
      )}
      <div title={item.title} css={textContentStyle(color, isSelected)}>
        {item.title}
      </div>
      <Icon
        type={isSelected ? "checkOnCircle" : "checkOffCircle"}
        color={color.neutral.text.default}
      />
    </li>
  );
}
