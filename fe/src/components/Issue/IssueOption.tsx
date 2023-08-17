import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "../common/Icon";
import { Txt } from "../util/Txt";
import { Dropdown } from "../common/Dropdown";
import { useContext, useRef, useState } from "react";
import { useOutsideClick } from "../../hooks/useOutsideClick";
import { IssueContext } from "../../contexts/IssueContext";

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
  IdItems,
  title,
  items,
}: {
  IdItems: { title: string; id: number }[];
  title: string;
  items: { title: string; icon: string | null; color: string | null }[];
}) {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [selectedOptions, setFilterSelected] = useState<string[]>([]);

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  const issueContextValue = useContext(IssueContext)!;
  const {
    setSelectedAssigneeFilter,
    setSelectedLabelFilter,
    setSelectedMilestoneFilter,
    setSelectedAuthorFilter,
    selectedLabelFilter,
    selectedAssigneeFilter,
    selectedAuthorFilter,
    selectedMilestoneFilter,
    setShouldFetchAgain,
  } = issueContextValue;

  useOutsideClick(dropdownRef, [filterButtonRef], () => {
    setIsDropdownOpen(!isDropdownOpen);
  });

  const onClickFilterOption = (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    setFilterSelected([item.title]);

    const idOfSelectedItem = IdItems.find(
      (IdItem) => IdItem.title === item.title
    )?.id;

    if (idOfSelectedItem === undefined) {
      // Id를 찾지 못한 경우에는 더 이상 진행하지 않습니다.
      return;
    }

    switch (title) {
      case "담당자":
        if (selectedAssigneeFilter.includes(idOfSelectedItem)) {
          setSelectedAssigneeFilter([]);
          setFilterSelected([]);
        } else {
          setSelectedAssigneeFilter([idOfSelectedItem]);
          console.log(item.title);
          setFilterSelected([item.title]);
        }
        break;

      case "레이블":
        if (selectedLabelFilter.includes(idOfSelectedItem)) {
          setSelectedLabelFilter((prevSelected) =>
            prevSelected.filter((id) => id !== idOfSelectedItem)
          );
        } else {
          setSelectedLabelFilter((prevSelected) => [
            ...prevSelected,
            idOfSelectedItem,
          ]);
        }
        break;

      case "마일스톤":
        if (selectedMilestoneFilter.includes(idOfSelectedItem)) {
          setSelectedMilestoneFilter([]);
          setFilterSelected([]);
        } else {
          setSelectedMilestoneFilter([idOfSelectedItem]);
          setFilterSelected([item.title]);
        }
        break;

      case "작성자":
        if (selectedAuthorFilter.includes(idOfSelectedItem)) {
          setSelectedAuthorFilter([]);
          setFilterSelected([]);
        } else {
          setSelectedAuthorFilter([idOfSelectedItem]);
          setFilterSelected([item.title]);
        }
        break;
    }

    setShouldFetchAgain(true);
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
          selectedOptions={selectedOptions}
          isDropdownOpen={isDropdownOpen}
          title={title}
          items={items}
        />
      </div>
    </div>
  );
}
