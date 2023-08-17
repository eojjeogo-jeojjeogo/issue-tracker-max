import { useContext, useEffect, useState } from "react";

import { IssueOption } from "./IssueOption";
import { TokenContext } from "../../contexts/TokenContext";
import { DYNAMIC_FILTER_URL, ISSUE_URL, SERVER } from "../../constants/url";

type DynamicFilter = {
  assignees: {
    id: number;
    name: string;
    imgUrl: string;
  }[];
  authors: {
    id: number;
    name: string;
    imgUrl: string;
  }[];
  labels: {
    id: number;
    name: string;
    backgroundColor: string;
    isDark: boolean;
  }[];
  milestones: {
    id: number;
    name: string;
  }[];
};

export function IssueDropdownTab() {
  const [dynamicFilterItems, setDynamicFilterItems] = useState<DynamicFilter>();

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const headers = {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        };

        const response = await fetch(
          `${SERVER}${ISSUE_URL}${DYNAMIC_FILTER_URL}`,
          {
            headers,
          }
        );

        const data = await response.json();
        setDynamicFilterItems(data);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
      }
    };
    fetchData();
  }, []);

  const authorsInDropdown = [
    ...new Set(
      dynamicFilterItems?.assignees.map((assignee) => {
        return { title: assignee.name, icon: assignee.imgUrl, color: null };
      })
    ),
  ];

  const labelsInDropdown = [
    ...new Set(
      dynamicFilterItems?.labels.map((label) => {
        return { title: label.name, icon: null, color: label.backgroundColor };
      })
    ),
  ];

  const milestonesInDropdown = [
    ...new Set(
      dynamicFilterItems?.milestones.map((milestone) => {
        return { title: milestone.name, icon: null, color: null };
      })
    ),
  ];

  const assigneeItems = [
    { title: "담당자가 없는 이슈", icon: null, color: null },
    ...authorsInDropdown,
  ];
  const labelItems = [
    { title: "레이블이 없는 이슈", icon: null, color: null },
    ...labelsInDropdown,
  ];
  const milestoneItems = [
    { title: "마일스톤이 없는 이슈", icon: null, color: null },
    ...milestonesInDropdown,
  ];
  const authorItems = authorsInDropdown;

  const assigneeIdItems = [
    ...new Set(
      dynamicFilterItems?.assignees.map((assignee) => {
        return { title: assignee.name, id: assignee.id };
      })
    ),
  ];
  const labelIdItems = [
    ...new Set(
      dynamicFilterItems?.labels.map((label) => {
        return { title: label.name, id: label.id };
      })
    ),
  ];
  const milestoneIdItems = [
    ...new Set(
      dynamicFilterItems?.milestones.map((milestone) => {
        return { title: milestone.name, id: milestone.id };
      })
    ),
  ];
  const authorIdItems = [
    ...new Set(
      dynamicFilterItems?.authors.map((author) => {
        return { title: author.name, id: author.id };
      })
    ),
  ];

  return (
    <div
      css={{
        display: "flex",
        gap: "32px",
        height: "32px",
        alignItems: "center",
      }}>
      <IssueOption
        IdItems={assigneeIdItems}
        title="담당자"
        items={assigneeItems}
      />
      <IssueOption IdItems={labelIdItems} title="레이블" items={labelItems} />
      <IssueOption
        IdItems={milestoneIdItems}
        title="마일스톤"
        items={milestoneItems}
      />
      <IssueOption IdItems={authorIdItems} title="작성자" items={authorItems} />
    </div>
  );
}
