import {
  openTabContainer,
  tableContainer,
  tableHeaderStyle,
  tableStyle,
} from "../styles/commonStyles";
import { useEffect, useState } from "react";

import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { Button } from "../components/common/Button";
import { MainArea } from "../components/common/MainArea";
import { TabButton } from "../components/common/TabButton";
import { TotalCount } from "./MilestonePage";
import { LoadingBar } from "../components/common/LoadingBar";
import { ColorScheme } from "../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { useNavigate } from "react-router-dom";
import { FilterBar } from "../components/common/FilterBar";
import {
  IssueElement,
  contentContainer,
} from "../components/Issue/IssueElement";

import { IssueDropdownTab } from "../components/Issue/IssueDropdownTab";
import { Icon } from "../components/common/Icon";
import { Txt } from "../components/util/Txt";

const TOTAL_COUNT_URL =
  "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/common/navigation";
// const ISSUE_URL = API 연결 시 사용
// "http://aed497a9-4c3a-45bf-91b8-433463633b2e.mock.pstmn.io/api/eojjeogojeojjeogo/issues?assignee=anonymous&label=label&milestone=milestone&author=author&label=label2";

export type IssueData = {
  openedIssueCount: number;
  closedIssueCount: number;
  issues: {
    id: number;
    number: number;
    title: string;
    createdTime: string;
    author: string;
    authorUrl: string;
    milestoneTitle: string;
    labels: {
      id: number;
      title: string;
      description: string;
      backgroundColor: string;
      textColor: string | number;
    }[];
  }[];
};

export type Issue = IssueData["issues"][0];

const OPEN_ISSUE = "열린 이슈";
const MY_ISSUE = "내가 작성한 이슈";
const ASSIGNED_ISSUE = "나에게 할당된 이슈";
const COMMENTED_ISSUE = "내가 댓글을 남긴 이슈";
const CLOSED_ISSUE = "닫힌 이슈";

const buttonTabsContainer = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
`;

const selectHeaderStyle = css`
  display: flex;
  align-items: center;
  gap: 32px;
  padding: 0 32px;
  box-sizing: border-box;
  width: 100%;
`;

export function IssuePage() {
  const [totalCount, setTotalCount] = useState<TotalCount | undefined>();

  // const [issue, setIssue] = useState<IssueData | undefined>(); // API 연결 시 사용

  const [loading, setLoading] = useState(true);
  const [isAddIssueOpen, setIsAddIssueOpen] = useState(false);
  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [filterSelected, setFilterSelected] = useState<string>(OPEN_ISSUE);
  const [isOpenSelected, setIsOpenSelected] = useState(true);
  const [selectedIssues, setSelectedIssues] = useState<number[]>([]);

  const navigate = useNavigate();
  const color = useTheme() as ColorScheme;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const totalCountResponse = await fetch(TOTAL_COUNT_URL);
        // const issueResponse = await fetch(ISSUE_URL); // API 연결 시 사용
        const totalCountData = await totalCountResponse.json();
        // const issueData = await issueResponse.json(); // API 연결 시 사용

        setTotalCount(totalCountData);
        // setIssue(issueData);

        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const onClickAddIssueButton = () => {
    navigate("/issues/new");
    setIsAddIssueOpen(true);
  };

  const onClickAllSelect = () => {
    if (selectedIssues.length === issue?.issues.length) {
      setSelectedIssues([]);
      return;
    }
    setSelectedIssues(issue?.issues.map((issue) => issue.id) || []);
  };

  const onClickLeftTab = () => {
    navigate("/label");
  };
  const onClickRightTab = () => {
    navigate("/milestone");
  };

  const onClickOpenTab = () => {
    setIsOpenSelected(true);
  };
  const onClickClosedTab = () => {
    setIsOpenSelected(false);
  };

  const onClickFilterOpenButton = () => {
    setIsFilterOpen(!isFilterOpen);
  };

  const onClickFilterOption = (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    setFilterSelected(item.title);
    setIsFilterOpen(false);
  };

  const onClickIssueSelect = (item: number) => {
    selectedIssues.includes(item)
      ? setSelectedIssues(selectedIssues.filter((id) => id !== item))
      : setSelectedIssues([...selectedIssues, item]);
  };

  const AddIssueButtonStatus = isAddIssueOpen ? "disabled" : "enabled";

  const filterItems = [
    { title: OPEN_ISSUE, icon: null, color: null },
    { title: MY_ISSUE, icon: null, color: null },
    { title: ASSIGNED_ISSUE, icon: null, color: null },
    { title: COMMENTED_ISSUE, icon: null, color: null },
    { title: CLOSED_ISSUE, icon: null, color: null },
  ];

  const leftTabProps = {
    leftIcon: "label",
    leftText: "레이블" + `(${totalCount ? totalCount.labelsCount : ""})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText:
      "마일스톤" + `(${totalCount ? totalCount?.milestonesCount : ""})`,
    onClickRightTab: onClickRightTab,
  };

  if (loading) {
    return (
      <Background>
        <LoadingBar />
        <Header />
      </Background>
    );
  }
  if (issue) {
    return (
      <Background>
        <Header />
        <MainArea>
          <div css={{ display: "flex", justifyContent: "space-between" }}>
            <FilterBar
              filterSelected={filterSelected}
              isFilterOpen={isFilterOpen}
              filterItems={filterItems}
              onClickFilterOpenButton={onClickFilterOpenButton}
              onClickFilterOption={onClickFilterOption}
            />
            <div css={buttonTabsContainer}>
              <TabButton
                leftTabProps={leftTabProps}
                rightTabProps={rightTabProps}
              />
              <Button
                onClick={onClickAddIssueButton}
                status={AddIssueButtonStatus}
                type="contained"
                size="S"
                text="이슈 작성"
                icon="plus"
              />
            </div>
          </div>
          <div css={tableContainer}>
            <div css={tableStyle(color)}>
              {selectedIssues.length > 0 ? (
                <div css={selectHeaderStyle}>
                  <div onClick={onClickAllSelect}>
                    <Icon
                      type="checkBoxDisable"
                      color={color.neutral.surface.strong}
                    />
                  </div>
                  <div css={contentContainer}>
                    <Txt typography="bold16" color={color.neutral.text.default}>
                      {selectedIssues.length}개 이슈 선택
                    </Txt>
                    <div>
                      <Txt
                        typography="medium16"
                        color={color.neutral.text.default}>
                        상태 수정
                      </Txt>
                      <Icon
                        type="chevronDown"
                        color={color.neutral.text.default}
                      />
                    </div>
                  </div>
                </div>
              ) : (
                <div css={tableHeaderStyle(color)}>
                  <div css={openTabContainer}>
                    <Button
                      onClick={onClickOpenTab}
                      status={isOpenSelected ? "selected" : "enabled"}
                      icon="alertCircle"
                      type="ghost"
                      size="M"
                      text={`열린 이슈(${issue ? issue.openedIssueCount : ""})`}
                    />
                    <Button
                      onClick={onClickClosedTab}
                      status={isOpenSelected ? "enabled" : "selected"}
                      icon="archive"
                      type="ghost"
                      size="M"
                      text={`닫힌 이슈(${issue ? issue.closedIssueCount : ""})`}
                    />
                  </div>
                  <IssueDropdownTab issue={issue} />
                </div>
              )}
              {issue?.issues.map((issue) => (
                <IssueElement
                  selectedIssues={selectedIssues}
                  onClickIssueSelect={onClickIssueSelect}
                  key={issue.id}
                  issue={issue}
                />
              ))}
            </div>
          </div>
        </MainArea>
      </Background>
    );
  }
}

// const totalCount = {
//   labelsCount: 3,
//   milestonesCount: 4,
// };

const issue = {
  openedIssueCount: 1,
  closedIssueCount: 2,
  issues: [
    {
      id: 3,
      number: 1,
      title: "title1",
      createdTime: "2023-07-31T11:54:55.471314",
      author: "그랬냥",
      authorUrl:
        "https://img.freepik.com/free-photo/adorable-kitty-looking-like-it-want-to-hunt_23-2149167099.jpg?w=2000",
      milestoneTitle: "milestoneTitle5",
      labels: [
        {
          title: "label",
          backgroundColor: "#D93F0B",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label2",
          backgroundColor: "#FBCA04",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label3",
          backgroundColor: "#0E8A16",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label4",
          backgroundColor: "#1D76DB",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label5",
          backgroundColor: "#0052CC",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label6",
          backgroundColor: "#1D76DB",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
      ],
    },
    {
      id: 4,
      number: 2,
      title: "title1",
      createdTime: "2023-07-21T11:54:55.471334",
      author: "고양선생",
      authorUrl:
        "https://cdn.eyesmag.com/content/uploads/posts/2022/08/08/main-ad65ae47-5a50-456d-a41f-528b63071b7b.jpg",
      milestoneTitle: "milestoneTitle3",
      labels: [
        {
          title: "label2",
          backgroundColor: "#FEF2C0",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label3",
          backgroundColor: "#E99695",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label4",
          backgroundColor: "#F9D0C4",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label5",
          backgroundColor: "#FBCA04",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label7",
          backgroundColor: "#0052CC",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label8",
          backgroundColor: "#F9D0C4",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
      ],
    },
    {
      id: 5,
      number: 3,
      title: "title1",
      createdTime: "2023-07-25T11:54:55.471337",
      author: "고양학생",
      authorUrl:
        "https://src.hidoc.co.kr/image/lib/2022/5/12/1652337370806_0.jpg",
      milestoneTitle: "milestoneTitle8",
      labels: [
        {
          title: "label",
          backgroundColor: "#D4C4FB",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label2",
          backgroundColor: "#BED3F3",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label3",
          backgroundColor: "#C4DEF6",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label9",
          backgroundColor: "#006B75",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label10",
          backgroundColor: "#1D76DB",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label11",
          backgroundColor: "#5319E7",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
      ],
    },
    {
      id: 6,
      number: 4,
      title: "title1",
      createdTime: "2023-07-27T11:54:55.47134",
      author: "고양시민",
      authorUrl:
        "https://i.namu.wiki/i/abZPxKt_L98I8ttqw56pLHtGiR5pAV4YYmpR3Ny3_n0yvff5IDoKEQFof7EbzJUSZ_-uzR5S7tzTzGQ346Qixw.webp",
      milestoneTitle: "milestoneTitle2",
      labels: [
        {
          title: "label",
          backgroundColor: "#C4DEF6",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label2",
          backgroundColor: "#C2E0C6",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label15",
          backgroundColor: "#0052CC",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label17",
          backgroundColor: "#0E8A16",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label18",
          backgroundColor: "#990000",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label6",
          backgroundColor: "#ffbfbf",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
      ],
    },
    {
      id: 7,
      number: 5,
      title: "title1",
      createdTime: "2023-06-25T11:54:55.471342",
      author: "야옹이다옹",
      authorUrl:
        "https://images.mypetlife.co.kr/content/uploads/2023/01/04154159/AdobeStock_101112878-scaled.jpeg",
      milestoneTitle: "milestoneTitle7",
      labels: [
        {
          title: "label5",
          backgroundColor: "#1D76DB",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label14",
          backgroundColor: "#5319E7",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label17",
          backgroundColor: "#FEF2C0",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label12",
          backgroundColor: "#C4DEF6",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label20",
          backgroundColor: "#FEF2C0",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
        {
          title: "label15",
          backgroundColor: "#BED3F3",
          textColor: 1,
          description: "레이블 설명",
          id: 1,
        },
      ],
    },
  ],
};
