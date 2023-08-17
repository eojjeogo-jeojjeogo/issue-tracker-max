import {
  openTabContainer,
  tableContainer,
  tableHeaderStyle,
  tableStyle,
} from "../styles/commonStyles";
import { useContext, useEffect, useRef, useState } from "react";

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
import {
  COMMON_URL,
  ISSUE_URL,
  SERVER,
  STATIC_FILTER_URL,
} from "../constants/url";
import { TokenContext } from "../contexts/TokenContext";
import { Dropdown } from "../components/common/Dropdown";
import { useOutsideClick } from "../hooks/useOutsideClick";
import { IssueContext } from "../contexts/IssueContext";

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
      isDark: boolean;
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

  const [issue, setIssue] = useState<IssueData | undefined>(); // API 연결 시 사용
  const [memberId, setMemberId] = useState<number>(-1); // API 연결 시 사용

  const [loading, setLoading] = useState(true);
  const [isAddIssueOpen, setIsAddIssueOpen] = useState(false);
  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);
  const [isOpenSelected, setIsOpenSelected] = useState(true);
  const [selectedIssues, setSelectedIssues] = useState<number[]>([]);
  const [filterBarValue, setFilterBarValue] =
    useState<string>("is:issue is:open");

  const [selectedStatus, setSelectedStatus] = useState<string[]>([]);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  // const [shouldFetchAgain, setShouldFetchAgain] = useState(false);
  const issueContextValue = useContext(IssueContext)!;
  const {
    selectedAssigneeFilter,
    selectedLabelFilter,
    selectedMilestoneFilter,
    selectedAuthorFilter,
    shouldFetchAgain,
    setShouldFetchAgain,
  } = issueContextValue;

  const dropdownRef = useRef<HTMLDivElement>(null);
  const filterButtonRef = useRef<HTMLDivElement>(null);

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const navigate = useNavigate();
  // if (!accessToken) navigate("/login");

  const color = useTheme() as ColorScheme;

  useEffect(() => {
    if (selectedOptions.length === 0) return;

    if (selectedOptions.includes(OPEN_ISSUE)) {
    }
  }, [selectedOptions]);

  const assigneeFilterURL =
    selectedAssigneeFilter.length > 0
      ? `&assignee=${selectedAssigneeFilter[0]}`
      : null;
  const labelFilterURL =
    selectedLabelFilter.length > 0
      ? selectedLabelFilter.map((id) => `&label=${id}`).join("")
      : null;
  const milestoneFilterURL =
    selectedMilestoneFilter.length > 0
      ? `&milestone=${selectedMilestoneFilter[0]}`
      : null;
  const authorFilterURL =
    selectedAuthorFilter.length > 0
      ? `&author=${selectedAuthorFilter[0]}`
      : null;

  useEffect(() => {
    const fetchData = async () => {
      const headers = {
        "Authorization": "Bearer " + accessToken,
        "Content-Type": "application/json",
      };
      try {
        setLoading(true);
        const totalCountResponse = await fetch(`${SERVER}${COMMON_URL}`, {
          headers,
        });
        const totalCountData = await totalCountResponse.json();
        setTotalCount(totalCountData);

        const baseIssueURL = "http://54.180.146.130/api/백엔드/issues";
        const queryParameters =
          selectedOptions.length === 0
            ? "?isClosed=false"
            : getSelectedURL(selectedOptions[0]);

        const filters = [
          assigneeFilterURL,
          labelFilterURL,
          milestoneFilterURL,
          authorFilterURL,
        ].filter(Boolean);

        const additionalQueryParameters = filters.join("");

        const QUERY_ISSUE_URL = `${baseIssueURL}${queryParameters}${additionalQueryParameters}`;
        // const QUERY_ISSUE_URL = `${baseIssueURL}${queryParameters}`;

        const issueResponse = await fetch(QUERY_ISSUE_URL, {
          headers,
        }); // API 연결 시 사용
        const issueData = await issueResponse.json(); // API 연결 시 사용

        const memberResponse = await fetch(
          `${SERVER}${ISSUE_URL}${STATIC_FILTER_URL}`,
          {
            headers,
          }
        );
        const memberData = await memberResponse.json();
        console.log(memberData);

        setMemberId(memberData.memberId);
        setIssue(issueData);
        setLoading(false);

        if (selectedOptions.length > 0) {
          navigate(
            `${getSelectedURL(selectedOptions[0])}${additionalQueryParameters}`
          );
        }
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
        navigate("/login");
      }
    };
    console.log(assigneeFilterURL);
    console.log(labelFilterURL);
    console.log(milestoneFilterURL);
    console.log(authorFilterURL);
    console.log("담당자", selectedAssigneeFilter);
    console.log("레이블", selectedLabelFilter);
    console.log("마일스톤", selectedMilestoneFilter);
    console.log("작성자", selectedAuthorFilter);
    fetchData();
    setShouldFetchAgain(false);
  }, [selectedOptions, shouldFetchAgain]);

  const getSelectedURL = (selectedOption: string) => {
    const selectedIndex = filterItems.findIndex(
      (item) => item.title === selectedOption
    );

    if (selectedIndex === -1) return ""; // 기본값 또는 적절한 값을 반환
    return selectedURL[selectedIndex].url;
  };

  const onClickStatusFilterOption = async (item: {
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    console.log(item.title);
    console.log(selectedIssues);
    try {
      const headers = {
        "Authorization": "Bearer " + accessToken,
        "Content-Type": "application/json",
      };
      const url = `${SERVER}${ISSUE_URL}`;
      const method = "PATCH";
      const body = JSON.stringify({
        issues: selectedIssues,
        isClosed: item.title === CLOSE_SELECT_ISSUE ? true : false,
      });
      const response = await fetch(url, {
        method,
        headers,
        body,
      });
      if (response.ok) {
        setShouldFetchAgain(true);
      }
    } catch (error) {
      console.error("API 요청 중 에러 발생:", error);
      navigate("/login");
    }

    setSelectedStatus([item.title]);
    setSelectedIssues([]);
    setIsDropdownOpen(false);
  };

  useOutsideClick(dropdownRef, [filterButtonRef], () => {
    setIsDropdownOpen(!isDropdownOpen);
  });

  const onClickStatusEditButton = () => {
    setSelectedStatus([]);
    setIsDropdownOpen(!isDropdownOpen);
  };

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

  useEffect(() => {
    if (filterBarValue === "is:issue is:open") {
      onClickOpenTab();
      return;
    }
    if (filterBarValue === "is:issue is:closed") {
      onClickClosedTab();
      return;
    }
  }, [filterBarValue]);

  useEffect(() => {
    if (selectedOptions.length === 0) return;
    if (selectedOptions[0] === OPEN_ISSUE) {
      setFilterBarValue("is:issue is:open");
      setIsOpenSelected(true);
      return;
    }
    if (selectedOptions[0] === CLOSED_ISSUE) {
      setFilterBarValue("is:issue is:closed");
      setIsOpenSelected(false);
      return;
    }
    if (selectedOptions[0] === MY_ISSUE) {
      setFilterBarValue(
        `is:issue is:${isOpenSelected ? "open" : "closed"} author:@me`
      );
      setIsOpenSelected(true);
      return;
    }
  }, [selectedOptions]);

  const onClickOpenTab = () => {
    setSelectedOptions([OPEN_ISSUE]);
    setFilterBarValue("is:issue is:open");
    setIsOpenSelected(true);
  };
  const onClickClosedTab = () => {
    setSelectedOptions([CLOSED_ISSUE]);
    setFilterBarValue("is:issue is:closed");
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
    setSelectedOptions([item.title]);
    setIsFilterOpen(false);
  };

  const onClickIssueSelect = (item: number) => {
    selectedIssues.includes(item)
      ? setSelectedIssues(selectedIssues.filter((id) => id !== item))
      : setSelectedIssues([...selectedIssues, item]);
  };

  const onChangeFilterBarValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilterBarValue(e.target.value);
  };
  const AddIssueButtonStatus = isAddIssueOpen ? "disabled" : "enabled";

  const filterItems = [
    { title: OPEN_ISSUE, icon: null, color: null },
    { title: MY_ISSUE, icon: null, color: null },
    { title: ASSIGNED_ISSUE, icon: null, color: null },
    { title: COMMENTED_ISSUE, icon: null, color: null },
    { title: CLOSED_ISSUE, icon: null, color: null },
  ];

  const selectedURL = [
    { url: "?isClosed=false" },
    { url: `?author=${memberId}` },
    { url: `?assignee=${memberId}` },
    { url: `?commenter=${memberId}` },
    { url: "?isClosed=true" },
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

  const OPEN_SELECT_ISSUE = "선택한 이슈 열기";
  const CLOSE_SELECT_ISSUE = "선택한 이슈 닫기";

  const statusSelectedOptions = [
    { title: OPEN_SELECT_ISSUE, icon: null, color: null },
    { title: CLOSE_SELECT_ISSUE, icon: null, color: null },
  ];

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
              onChange={onChangeFilterBarValue}
              filterBarValue={filterBarValue}
              selectedOptions={selectedOptions}
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
                    <div
                      onClick={onClickStatusEditButton}
                      ref={filterButtonRef}
                      css={{
                        display: "flex",
                        alignItems: "center",
                        gap: "4px",
                        width: "80px",
                        height: "32px",
                      }}>
                      <Txt
                        typography="medium16"
                        color={color.neutral.text.default}>
                        상태 수정
                      </Txt>
                      <Icon
                        type="chevronDown"
                        color={color.neutral.text.default}
                      />
                      <div
                        css={{
                          position: "absolute",
                          top: "36px",
                          transform: "translateX(-160px)",
                          zIndex: "100",
                        }}>
                        <Dropdown
                          ref={dropdownRef}
                          onClick={onClickStatusFilterOption}
                          selectedOptions={selectedStatus}
                          isDropdownOpen={isDropdownOpen}
                          title="상태 변경"
                          items={statusSelectedOptions}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                <div css={tableHeaderStyle(color)}>
                  <div css={openTabContainer}>
                    <div onClick={onClickAllSelect}>
                      <Icon
                        type="checkBoxInitial"
                        color={color.neutral.border.default}
                      />
                    </div>
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
                  <IssueDropdownTab />
                </div>
              )}
              <div css={{ overflow: "auto" }}>
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
          </div>
        </MainArea>
      </Background>
    );
  }
}

// const issue = {
//   openedIssueCount: 1,
//   closedIssueCount: 2,
//   issues: [
//     {
//       id: 3,
//       number: 1,
//       title: "title1",
//       createdTime: "2023-07-31T11:54:55.471314",
//       author: "그랬냥",
//       authorUrl:
//         "https://img.freepik.com/free-photo/adorable-kitty-looking-like-it-want-to-hunt_23-2149167099.jpg?w=2000",
//       milestoneTitle: "milestoneTitle5",
//       labels: [
//         {
//           title: "label",
//           backgroundColor: "#D93F0B",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 1,
//         },
//         {
//           title: "label2",
//           backgroundColor: "#FBCA04",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 2,
//         },
//         {
//           title: "label3",
//           backgroundColor: "#0E8A16",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 3,
//         },
//         {
//           title: "label4",
//           backgroundColor: "#1D76DB",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 4,
//         },
//         {
//           title: "label5",
//           backgroundColor: "#0052CC",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 5,
//         },
//         {
//           title: "label6",
//           backgroundColor: "#1D76DB",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 6,
//         },
//       ],
//     },
//     {
//       id: 4,
//       number: 2,
//       title: "title1",
//       createdTime: "2023-07-21T11:54:55.471334",
//       author: "고양선생",
//       authorUrl:
//         "https://cdn.eyesmag.com/content/uploads/posts/2022/08/08/main-ad65ae47-5a50-456d-a41f-528b63071b7b.jpg",
//       milestoneTitle: "milestoneTitle3",
//       labels: [
//         {
//           title: "label2",
//           backgroundColor: "#FEF2C0",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 12,
//         },
//         {
//           title: "label3",
//           backgroundColor: "#E99695",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 13,
//         },
//         {
//           title: "label4",
//           backgroundColor: "#F9D0C4",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 14,
//         },
//         {
//           title: "label5",
//           backgroundColor: "#FBCA04",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 15,
//         },
//         {
//           title: "label7",
//           backgroundColor: "#0052CC",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 16,
//         },
//         {
//           title: "label8",
//           backgroundColor: "#F9D0C4",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 1,
//         },
//       ],
//     },
//     {
//       id: 5,
//       number: 3,
//       title: "title1",
//       createdTime: "2023-07-25T11:54:55.471337",
//       author: "고양학생",
//       authorUrl:
//         "https://src.hidoc.co.kr/image/lib/2022/5/12/1652337370806_0.jpg",
//       milestoneTitle: "milestoneTitle8",
//       labels: [
//         {
//           title: "label",
//           backgroundColor: "#D4C4FB",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 17,
//         },
//         {
//           title: "label2",
//           backgroundColor: "#BED3F3",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 18,
//         },
//         {
//           title: "label3",
//           backgroundColor: "#C4DEF6",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 19,
//         },
//         {
//           title: "label9",
//           backgroundColor: "#006B75",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 111,
//         },
//         {
//           title: "label10",
//           backgroundColor: "#1D76DB",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 112,
//         },
//         {
//           title: "label11",
//           backgroundColor: "#5319E7",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 113,
//         },
//       ],
//     },
//     {
//       id: 6,
//       number: 4,
//       title: "title1",
//       createdTime: "2023-07-27T11:54:55.47134",
//       author: "고양시민",
//       authorUrl:
//         "https://i.namu.wiki/i/abZPxKt_L98I8ttqw56pLHtGiR5pAV4YYmpR3Ny3_n0yvff5IDoKEQFof7EbzJUSZ_-uzR5S7tzTzGQ346Qixw.webp",
//       milestoneTitle: "milestoneTitle2",
//       labels: [
//         {
//           title: "label",
//           backgroundColor: "#C4DEF6",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 1134,
//         },
//         {
//           title: "label2",
//           backgroundColor: "#C2E0C6",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 145,
//         },
//         {
//           title: "label15",
//           backgroundColor: "#0052CC",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 136,
//         },
//         {
//           title: "label17",
//           backgroundColor: "#0E8A16",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 13456,
//         },
//         {
//           title: "label18",
//           backgroundColor: "#990000",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 12345,
//         },
//         {
//           title: "label6",
//           backgroundColor: "#ffbfbf",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 1231,
//         },
//       ],
//     },
//     {
//       id: 7,
//       number: 5,
//       title: "title1",
//       createdTime: "2023-06-25T11:54:55.471342",
//       author: "야옹이다옹",
//       authorUrl:
//         "https://images.mypetlife.co.kr/content/uploads/2023/01/04154159/AdobeStock_101112878-scaled.jpeg",
//       milestoneTitle: "milestoneTitle7",
//       labels: [
//         {
//           title: "label5",
//           backgroundColor: "#1D76DB",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 11234,
//         },
//         {
//           title: "label14",
//           backgroundColor: "#5319E7",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 16758,
//         },
//         {
//           title: "label17",
//           backgroundColor: "#FEF2C0",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 1876,
//         },
//         {
//           title: "label12",
//           backgroundColor: "#C4DEF6",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 187,
//         },
//         {
//           title: "label20",
//           backgroundColor: "#FEF2C0",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 154,
//         },
//         {
//           title: "label15",
//           backgroundColor: "#BED3F3",
//           textColor: 1,
//           description: "레이블 설명",
//           id: 15678,
//         },
//       ],
//     },
//   ],
// };
