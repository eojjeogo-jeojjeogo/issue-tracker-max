import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { IssueDetail } from "../../pages/IssueDetailPage";
import { Txt } from "../util/Txt";
import { Icon } from "../common/Icon";
import { Label } from "../Label/Label";
// import { progressBar } from "../Milestone/MilestoneElement";
import { useContext, useEffect, useRef, useState } from "react";
import { Dropdown } from "../common/Dropdown";
import { assigneeUserImage } from "./AddBox";
import {
  // ASSIGNEE_URL,
  FILTER_URL,
  LABEL_URL,
  MILESTONE_URL,
  SERVER,
} from "../../constants/url";
import { useOutsideClick } from "../../hooks/useOutsideClick";
import { IssueContext } from "../../contexts/IssueContext";
// import { LabelType } from "../../pages/LabelPage";

type Milestone = {
  id: number;
  name: string;
};

type Label = {
  id: number;
  name: string;
  backgroundColor: string;
  isDark: boolean;
};

type Labels = Label[];

type Milestones = Milestone[];

type Assignee = {
  id: number;
  name: string;
  imgUrl: string;
}[];

type FilterItem = Labels | Milestones | Assignee;

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

const getDropdownItems = (
  filterItems: FilterItem,
  selectedCategory: string
) => {
  switch (selectedCategory) {
    case "assignees":
      return (filterItems as Assignee).map((assignee) => {
        return {
          title: assignee.name,
          icon: assigneeUserImage,
          color: null,
        };
      });
    case "레이블":
      return (filterItems as Labels).map((label) => ({
        title: label.name,
        icon: "",
        color: label.backgroundColor,
      }));
    case "마일스톤":
      return (filterItems as Milestones).map((milestone) => {
        return {
          title: milestone.name,
          icon: null,
          color: null,
        };
      });
    default:
      return [];
  }
};

export function AddBoxItem({
  // issueDetail,
  title,
}: {
  mode: string;
  issueDetail?: IssueDetail;
  title: string;
  items: {
    title: string;
    icon: string;
    color: null;
  }[];
}) {
  const [isHover, setIsHover] = useState(false);
  const [filterItems, setFilterItems] = useState<FilterItem>();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [filterSelected, setFilterSelected] = useState<string>("");
  const [selectedCategory, setSelectedCategory] = useState<string>("");
  const [selectedItems, setSelectedItems] = useState<
    {
      title: string;
      icon: string | null;
      color: string | null;
    }[]
  >([]);

  const IssueContextValue = useContext(IssueContext)!;
  const {
    // assigneeList,
    // setAssigneeList,
    // labelList,
    // setLabelList,
    // milestone,
    // setMilestone,
  } = IssueContextValue;

  const getURL = (title: string) => {
    switch (title) {
      case "담당자":
        return "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/assignees?type=filter";
      // return `${SERVER}${ASSIGNEE_URL}${FILTER_URL}`; // 담당자 미완성
      case "레이블":
        return `${SERVER}${LABEL_URL}${FILTER_URL}`;
      case "마일스톤":
        return `${SERVER}${MILESTONE_URL}${FILTER_URL}`;
      default:
        return "";
    }
  };

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const filterItemsResponse = await fetch(getURL(title));
        const filterItemsData = await filterItemsResponse.json();

        switch (title) {
          case "담당자":
            // setAssigneeList(filterItemsData);
            setFilterItems(filterItemsData);
            break;
          case "레이블":
            // setLabelList(filterItemsData);
            setFilterItems(filterItemsData);
            break;
          case "마일스톤":
            // setFilterItems(filterItemsData);
            break;
          default:
            break;
        }
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
      }
    };

    fetchData();
  }, [isHover]);

  useOutsideClick(dropdownRef, [filterButtonRef], () => {
    setIsDropdownOpen(!isDropdownOpen);
  });

  const onClickTitleContainer = () => {
    console.log(selectedItems);
    setIsDropdownOpen(!isDropdownOpen);
  };

  const onClickOption = (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    switch (selectedCategory) {
      case "담당자":
        setSelectedItems((prev) => {
          const isExist = prev.find(
            (assignee) => assignee.title === item.title
          );
          if (isExist) {
            return prev.filter((assignee) => assignee.title !== item.title);
          } else {
            return [
              ...prev,
              { title: item.title, icon: item.icon, color: null },
            ];
          }
        });
        break;
      case "레이블":
        setSelectedItems((prev) => {
          const isExist = prev.find((label) => label.title === item.title);
          if (isExist) {
            return prev.filter((label) => label.title !== item.title);
          } else {
            return [
              ...prev,
              { title: item.title, color: item.color, icon: null },
            ];
          }
        });

        break;
      case "마일스톤":
        setSelectedItems((prev) => {
          const isExist = prev.find(
            (milestone) => milestone.title === item.title
          );
          if (isExist) {
            return prev.filter((milestone) => milestone.title !== item.title);
          } else {
            return [...prev, { title: item.title, color: null, icon: null }];
          }
        });
        break;
      default:
        break;
    }
    setFilterSelected(item.title);
  };

  const onMouseEnter = () => {
    setSelectedCategory(title);

    setIsHover(true);
  };

  const onMouseLeave = () => {
    // setSelectedCategory("");
    setIsHover(false);
  };

  const color = useTheme() as ColorScheme;
  return (
    <div css={addBoxItem(color)}>
      <div
        ref={filterButtonRef}
        onClick={onClickTitleContainer}
        onMouseEnter={onMouseEnter}
        onMouseLeave={onMouseLeave}
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
        {isDropdownOpen && filterItems ? (
          <div css={{ position: "absolute", top: "32px", zIndex: "300" }}>
            <Dropdown
              ref={dropdownRef}
              items={getDropdownItems(filterItems, selectedCategory)}
              isDropdownOpen={isDropdownOpen}
              title={`${title} 설정`}
              multiSelect={false}
              filterSelected={filterSelected}
              onClick={onClickOption}
            />
          </div>
        ) : null}
      </div>

      {/* {title === "레이블"
        ? selectedItems.map((label, idx) => <Label label={label} key={idx} />)
        : null} */}

      {/* () => { // 작성중인 코드입니다
            switch (title) {
              case "담당자":
                // return issueDetail.assignees.map((assignee, idx) => (
                return assigneeList.map((assignee, idx) => (
                  <IssueItems key={idx} assignee={assignee} color={color} />
                ));
              case "레이블":
                // return issueDetail.labels.map((label, idx) => (
                return labelList.map((label, idx) => (
                  <Label label={label} key={idx} />
                ));
              case "마일스톤":
                return (
                  <MilestoneItems
                    // milestone={issueDetail.milestone}
                    issueDetail={issueDetail}
                    color={color}
                  />
                );
              default:
                return null;
            }
          })(); */}
    </div>
  );
}

//api 데이터 형태 관련 문제로 보류중인 코드들입니다

// function MilestoneItems({
//   // milestone,
//   issueDetail,
//   color,
// }: {
//   // milestone: Milestone;
//   issueDetail: IssueDetail;
//   color: ColorScheme;
// }) {
//   return (
//     <>
//       <div css={progressBar(getPercentage(issueDetail))}></div>
//       <Txt typography="medium12" color={color.neutral.text.strong}>
//         {issueDetail.milestone.id}
//       </Txt>
//     </>
//   );
// }

// function IssueItems({
//   assignee,
//   color,
// }: {
//   assignee: {
//     id: number;
//     nickName: string;
//     imgUrl: string;
//   };
//   color: ColorScheme;
// }) {
//   return (
//     <div css={{ display: "flex", gap: "8px" }}>
//       <img
//         css={{
//           width: "20px",
//           height: "20px",
//           cover: "fit",
//           borderRadius: "50%",
//         }}
//         src={assigneeUserImage}
//         alt="profileImage"
//       />
//       <Txt typography="medium12" color={color.neutral.text.strong}>
//         {assignee.nickName}
//       </Txt>
//     </div>
//   );
// }
