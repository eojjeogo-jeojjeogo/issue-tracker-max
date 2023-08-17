import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "./Icon";
import { Txt } from "../util/Txt";
import { fonts } from "../../constants/fonts";
import { useRef, useState } from "react";
import { Dropdown } from "./Dropdown";
import { useOutsideClick } from "../../hooks/useOutsideClick";

type Props = {
  filterBarValue: string;
  selectedOptions: string[];
  isFilterOpen: boolean;
  filterItems: { title: string; icon: string | null; color: string | null }[];
  onClickFilterOpenButton: () => void;
  onClickFilterOption: (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => void;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

const filterBarContainer = (color: ColorScheme, isActive: boolean) => css`
  display: flex;
  align-items: center;
  width: 560px;
  height: 40px;

  overflow: hidden;
  border: 1px solid
    ${isActive ? color.neutral.text.default : color.neutral.border.default};
  border-radius: 12px;
`;

const filterBarButtonContainer = (color: ColorScheme, isActive: boolean) => css`
  display: flex;
  align-items: center;
  width: 128px;
  height: 100%;
  gap: 4px;
  cursor: pointer;
  padding: 0 24px;
  box-sizing: border-box;
  border-right: 1px solid ${color.neutral.border.default};
  background-color: ${isActive
    ? color.neutral.surface.strong
    : color.neutral.surface.default};
`;

const filterBarButtonWrapper = css`
  display: flex;
  align-items: center;
  width: 60px;
`;

const inputContainer = (color: ColorScheme, isActive: boolean) => css`
  display: flex;
  align-items: center;
  width: 431px;
  height: 100%;
  padding: 0 24px;
  gap: 8px;
  box-sizing: border-box;
  background-color: ${isActive
    ? color.neutral.surface.strong
    : color.neutral.surface.bold};
`;

const input = (color: ColorScheme, isActive: boolean) => css`
  width: 100%;
  border: none;
  outline: none;
  background-color: transparent;
  color: ${isActive ? color.neutral.text.default : color.neutral.text.weak};
  // &::placeholder {
  //   color: ${color.neutral.text.weak};
  //   font-size: ${fonts.medium16.fontSize};
  //   font-weight: ${fonts.medium16.fontWeight};
  // }
`;

export function FilterBar({
  filterBarValue,
  onChange,
  selectedOptions,
  isFilterOpen,
  filterItems,
  onClickFilterOpenButton,
  onClickFilterOption,
}: Props) {
  const [isActive, setIsActive] = useState(false);

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  const color = useTheme() as ColorScheme;

  useOutsideClick(dropdownRef, [filterButtonRef], () => {
    onClickFilterOpenButton();
  });

  const onFocus = () => {
    setIsActive(true);
  };

  const onBlur = () => {
    setIsActive(false);
  };

  return (
    <div css={filterBarContainer(color, isActive)}>
      <div
        ref={filterButtonRef}
        onClick={onClickFilterOpenButton}
        css={filterBarButtonContainer(color, isActive)}>
        <div css={filterBarButtonWrapper}>
          <Txt typography="medium16" color={color.neutral.text.default}>
            필터
          </Txt>
        </div>
        <Icon type="chevronDown" color={color.neutral.text.default} />
        <div
          css={{
            position: "absolute",
            left: "80px",
            top: "174px",
            zIndex: "100",
          }}>
          <Dropdown
            ref={dropdownRef}
            onClick={onClickFilterOption}
            selectedOptions={selectedOptions}
            isDropdownOpen={isFilterOpen}
            title="이슈 필터"
            items={filterItems}
          />
        </div>
      </div>
      <div css={inputContainer(color, isActive)}>
        <Icon type="search" color={color.neutral.text.default} />
        <input
          value={filterBarValue}
          onChange={onChange}
          onFocus={onFocus}
          onBlur={onBlur}
          css={input(color, isActive)}
          type="text"
          // placeholder={filterBarValue}
        />
      </div>
    </div>
  );
}
