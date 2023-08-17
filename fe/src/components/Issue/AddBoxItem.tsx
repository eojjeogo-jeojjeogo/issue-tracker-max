import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
// import { IssueDetail } from "../../pages/IssueDetailPage";
import { Txt } from "../util/Txt";
import { Icon } from "../common/Icon";
import { Label } from "../Label/Label";
// import { progressBar } from "../Milestone/MilestoneElement";
import { useContext, useRef, useState } from "react";
import { Dropdown } from "../common/Dropdown";
// import { assigneeUserImage } from "./AddBox";

import { useOutsideClick } from "../../hooks/useOutsideClick";
import { IssueContext } from "../../contexts/IssueContext";
import { ISSUE_URL, SERVER } from "../../constants/url";
import { TokenContext } from "../../contexts/TokenContext";
import { IssueDetail } from "../../pages/IssueDetailPage";

type FilterLabel = {
  id: number;
  name: string;
  backgroundColor: string;
  isDark: boolean;
};

type FilterMilestone = {
  id: number;
  name: string;
};

type FilterAssignee = {
  id: number;
  name: string;
  imgUrl: string;
};

const addBoxItem = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  gap: 16px;
  justify-content: space-between;
  padding: 32px;
  background-color: ${color.neutral.surface.strong};
`;

const titleContainer = css`
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  height: 24px;
`;

// const getPercentage = (issueDetail: IssueDetail) => {
//   const openIssueCount = issueDetail.milestone.issueOpenedCount;
//   const closedIssueCount = issueDetail.milestone.issueClosedCount;
//   const totalIssueCount = openIssueCount + closedIssueCount;

//   return Math.floor((closedIssueCount / totalIssueCount) * 100);
// };

type FilterItems = FilterLabel[] | FilterMilestone[] | FilterAssignee[];

export function AddBoxItem({
  issueId,
  // issueDetail,
  title,
  dropdownItems,
}: {
  issueId?: string;
  issueDetail?: IssueDetail;
  mode: string;
  title: string;
  dropdownItems: FilterLabel[] | FilterMilestone[] | FilterAssignee[];
  onMouseEnter?: (title: string) => void;
}) {
  const [isHover, setIsHover] = useState(false);

  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>("");
  const [addBoxFilterItems, setAddBoxFilterItems] = useState<FilterItems>([]);

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const IssueContextValue = useContext(IssueContext)!;
  const {
    // assigneeList,
    // setAssigneeList,
    labelList,
    // setLabelList,
    // milestone,
    // setMilestone,
    handleSetAssigneeList,
    handleSetLabelList,
    handleSetMilestoneList,
    setShouldFetchAgain,
  } = IssueContextValue;

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  const getDropdownItems = (data: FilterLabel[] | FilterMilestone[]) => {
    switch (title) {
      case "담당자":
        return (data as FilterAssignee[]).map((item) => {
          return {
            title: item.name,
            icon: item.imgUrl,
            color: null,
          };
        });
      case "레이블":
        return (data as FilterLabel[]).map((item) => {
          return {
            title: item.name,
            icon: null,
            color: item.backgroundColor,
          };
        });
      case "마일스톤":
        return (data as FilterMilestone[]).map((item) => {
          return {
            title: item.name,
            icon: null,
            color: null,
          };
        });
      default:
        return [];
    }
  };

  const getAddBoxItems = (selectedOptions: string[]) => {
    switch (title) {
      case "담당자":
        const newAssigneeList = (dropdownItems as FilterAssignee[]).filter(
          (item) => selectedOptions.includes(item.name)
        );
        console.log(newAssigneeList);
        return newAssigneeList;

      case "레이블":
        const newLabelList = (dropdownItems as FilterLabel[]).filter((item) =>
          selectedOptions.includes(item.name)
        );
        console.log(newLabelList);
        return newLabelList;

      case "마일스톤":
        const newMilestone = (dropdownItems as FilterMilestone[]).filter(
          (item) => selectedOptions.includes(item.name)
        );
        console.log(newMilestone);
        return newMilestone;
    }
  };

  useOutsideClick(dropdownRef, [filterButtonRef], async () => {
    try {
      const response = await fetch(`${SERVER}${ISSUE_URL}/${issueId}/labels`, {
        method: "PUT",
        headers: {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          labels: labelList,
        }),
      });

      if (response.ok) {
        setShouldFetchAgain(true);
      }
    } catch (error) {
      console.error("API 요청 중 에러 발생:", error);
    }

    setIsDropdownOpen(false);
    const addBoxItems = getAddBoxItems(selectedOptions)!;
    console.log(addBoxItems);
    setAddBoxFilterItems(addBoxItems);
    setCurrentSelectedOptions(addBoxItems);
  });

  const setCurrentSelectedOptions = (addBoxItems: FilterItems) => {
    switch (title) {
      case "담당자":
        handleSetAssigneeList(addBoxItems.map((item) => item.id));
        break;
      case "레이블":
        handleSetLabelList(addBoxItems.map((item) => item.id));
        break;
      case "마일스톤":
        handleSetMilestoneList(addBoxItems.map((item) => item.id));
        break;
      default:
        break;
    }
  };

  const onClickTitleContainer = () => {
    setIsDropdownOpen(true);
  };

  const onClickOption = (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    switch (title) {
      case "담당자":
        if (selectedOptions.includes(item.title)) {
          const newSelectedOptions = selectedOptions.filter(
            (option) => option !== item.title
          );
          setSelectedOptions(newSelectedOptions);
        } else {
          setSelectedOptions([...selectedOptions, item.title]);
        }
        break;
      case "레이블":
        if (selectedOptions.includes(item.title)) {
          const newSelectedOptions = selectedOptions.filter(
            (option) => option !== item.title
          );
          setSelectedOptions(newSelectedOptions);
        } else {
          setSelectedOptions([...selectedOptions, item.title]);
        }

        break;
      case "마일스톤":
        setSelectedOptions([item.title]);
        break;
      default:
        break;
    }
  };

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await fetch(`${SERVER}${ISSUE_URL}/1/labels`, {
  //         method: "PUT",
  //         headers: {
  //           "Authorization": "Bearer " + accessToken,
  //           "Content-Type": "application/json",
  //         },
  //         body: JSON.stringify({
  //           labels: selectedOptions,
  //         }),
  //       });

  //       if (response.ok) {
  //         setShouldFetchAgain(true);
  //       }
  //     } catch (error) {
  //       console.error("API 요청 중 에러 발생:", error);
  //     }
  //   };

  //   fetchData();
  //   setIsStatusChanged(false);
  // }, [isStatusChanged]);

  const renderAddBoxFilterItems = () => {
    switch (title) {
      case "담당자":
        return (
          <div css={{ display: "flex", gap: "16px", flexDirection: "column" }}>
            {(addBoxFilterItems as FilterAssignee[]).map((item) => (
              /* {assigneeItems.map((item) => ( */
              <div
                key={item.id}
                css={{
                  display: "flex",
                  gap: "8px",
                  width: "224px",
                  height: "20px",
                }}>
                <img
                  css={{
                    width: "20px",
                    height: "20px",
                    borderRadius: "50%",
                    objectFit: "cover",
                  }}
                  src={item.imgUrl}
                />
                <Txt typography="medium12" color={color.neutral.text.strong}>
                  {item.name}
                </Txt>
              </div>
            ))}
          </div>
        );
      case "레이블":
        return (
          <div css={{ display: "flex", gap: "4px", flexWrap: "wrap" }}>
            {(addBoxFilterItems as FilterLabel[]).map((item) => (
              /* {labelItems.map((item) => ( */
              <Label key={item.id} isDark={item.isDark} label={item} />
            ))}
          </div>
        );
      case "마일스톤":
        return (
          <div>
            {addBoxFilterItems.map((item) => (
              <div key={item.id}>{item.name}</div>
            ))}
          </div>
        );
      default:
        return null;
    }
  };

  const onTitleHover = () => {
    setSelectedCategory(title);
    console.log(selectedCategory);
    setIsHover(true);
  };

  const onTitleLeave = () => {
    setSelectedCategory("");
    setIsHover(false);
  };

  const color = useTheme() as ColorScheme;
  return (
    <div css={addBoxItem(color)}>
      <div
        onMouseEnter={onTitleHover}
        onMouseLeave={onTitleLeave}
        ref={filterButtonRef}
        onClick={onClickTitleContainer}
        css={titleContainer}>
        <Txt
          typography="medium16"
          color={isHover ? color.palette.blue : color.neutral.text.default}>
          {title}
        </Txt>
        <Icon
          type="plus"
          color={isHover ? color.palette.blue : color.neutral.text.default}
        />
        {isDropdownOpen && dropdownItems ? (
          <div css={{ position: "absolute", top: "32px", zIndex: "300" }}>
            <Dropdown
              ref={dropdownRef}
              items={getDropdownItems(dropdownItems)}
              isDropdownOpen={isDropdownOpen}
              title={`${title} 설정`}
              selectedOptions={selectedOptions}
              onClick={onClickOption}
            />
          </div>
        ) : null}
      </div>
      {addBoxFilterItems.length > 0 ? renderAddBoxFilterItems() : null}
    </div>
  );
}
