import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { DropdownItems } from "./DropdownItem";
import { fonts } from "../../constants/fonts";
import React from "react";

type Props = {
  isDropdownOpen: boolean;
  title: string;
  items: { title: string; icon: string | null; color: string | null }[];

  onClick?: (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => void;
  selectedOptions: string[];
};
const dropdownStyle = (color: ColorScheme) => css`
  position: absolute;
  width: 240px;
  height: max-content;
  max-height: 472px;
  left: 50%;
  border: 1px solid ${color.neutral.border.default};
  border-radius: 16px;
  overflow: scroll;

  animation: fadeIn 0.3s ease forwards; // 애니메이션 적용

  &::-webkit-scrollbar {
    display: none;
  }

  @keyframes fadeIn {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

const DropdownHeader = (color: ColorScheme) => css`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  height: 32px;
  color: ${color.neutral.text.weak};
  padding: 8px 16px;
  box-sizing: border-box;
  ${fonts.medium12};
  background-color: ${color.neutral.surface.default};
`;

export const Dropdown = React.forwardRef<HTMLDivElement, Props>(
  (
    {
      isDropdownOpen,
      title,
      items = [],

      onClick,
      selectedOptions,
    },
    ref
  ) => {
    const color = useTheme() as ColorScheme;
    if (!isDropdownOpen) return null;

    return (
      <div ref={ref} className="dropdown" css={dropdownStyle(color)}>
        <div css={DropdownHeader(color)}>
          <div>{title}</div>
        </div>
        <ul>
          {items.map((item, idx) => {
            const isSelected = selectedOptions.includes(item.title);
            return (
              <DropdownItems
                key={idx}
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
);
