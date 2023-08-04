import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "../common/Icon";
import { Txt } from "../util/Txt";
import { Dropdown } from "../common/Dropdown";
import { useRef, useState } from "react";
import { useOutsideClick } from "../../hooks/useOutsideClick";

const dropdownWrapper = css`
  position: relative;
  display: flex;
  align-items: center;
  width: 80px;
  height: 32px;
  gap: 4px;
  cursor: pointer;
`;

const textWrapper = css`
  display: flex;
  align-items: center;
  width: 60px;
  height: 100%;
`;

export function IssueOption({
  title,
  items,
}: {
  title: string;
  items: { title: string; icon: string | null; color: string | null }[];
}) {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [filterSelected, setFilterSelected] = useState("");

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  useOutsideClick(dropdownRef, [filterButtonRef], () => {
    setIsDropdownOpen(!isDropdownOpen);
  });

  const onClickFilterOption = (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    setFilterSelected(item.title);
  };

  const onClickDropdownOpen = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const color = useTheme() as ColorScheme;
  return (
    <div
      ref={filterButtonRef}
      onClick={onClickDropdownOpen}
      css={dropdownWrapper}>
      <div css={textWrapper}>
        <Txt typography="medium16" color={color.neutral.text.weak}>
          {title}
        </Txt>
      </div>
      <Icon type="chevronDown" color={color.neutral.text.default} />
      <div
        css={{
          position: "absolute",
          top: "36px",
          transform: "translateX(-160px)",
          zIndex: "100",
        }}>
        <Dropdown
          ref={dropdownRef}
          onClick={onClickFilterOption}
          filterSelected={filterSelected}
          isDropdownOpen={isDropdownOpen}
          title={title}
          items={items}
          multiSelect={false}
        />
      </div>
    </div>
  );
}
