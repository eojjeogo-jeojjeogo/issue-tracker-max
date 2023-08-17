import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";

import { AddBoxItem } from "./AddBoxItem";
import { useContext, useEffect, useState } from "react";
import {
  ASSIGNEE_URL,
  FILTER_URL,
  LABEL_URL,
  MILESTONE_URL,
  SERVER,
} from "../../constants/url";
import { TokenContext } from "../../contexts/TokenContext";
import { IssueDetail } from "../../pages/IssueDetailPage";

export const assigneeUserImage =
  "https://images.mypetlife.co.kr/content/uploads/2023/01/04154159/AdobeStock_101112878-scaled.jpeg";

const addBoxContainer = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 288px;
  height: max-content;
  border: 1px solid ${color.neutral.border.default};
  border-radius: 16px;

  & > :first-of-type {
    border-top-left-radius: 16px;
    border-top-right-radius: 16px;
  }

  & > :last-child {
    border-bottom-left-radius: 16px;
    border-bottom-right-radius: 16px;
  }
`;

export type FilterLabel = {
  id: number;
  name: string;
  backgroundColor: string;
  isDark: boolean;
};

export type FilterMilestone = {
  id: number;
  name: string;
};

export type FilterAssignee = {
  id: number;
  name: string;
  imgUrl: string;
};

export function AddBox({
  issueId,
  mode,
  issueDetail,
}: {
  issueId?: string;
  mode: string;
  issueDetail?: IssueDetail;
}) {
  const [assigneeItems, setAssigneeItems] = useState<FilterAssignee[]>([]);
  const [labelItems, setLabelItems] = useState<FilterLabel[]>([]);
  const [milestoneItems, setMilestoneItems] = useState<FilterMilestone[]>([]);

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const color = useTheme() as ColorScheme;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const headers = {
          "Authorization": "Bearer " + accessToken,
          "Content-Type": "application/json",
        };

        const labelsPromise = fetch(`${SERVER}${LABEL_URL}${FILTER_URL}`, {
          headers,
        });
        const milestonesPromise = fetch(
          `${SERVER}${MILESTONE_URL}${FILTER_URL}`,
          { headers }
        );

        const assigneesPromise = fetch(
          `${SERVER}${ASSIGNEE_URL}${FILTER_URL}`,
          { headers }
        );

        const [labelsResponse, milestonesResponse, assigneesResponse] =
          await Promise.all([
            labelsPromise,
            milestonesPromise,
            assigneesPromise,
          ]);

        const labelsData = await labelsResponse.json();
        const milestoneData = await milestonesResponse.json();
        const assigneeData = await assigneesResponse.json();

        console.log(assigneeData);
        console.log(labelsData);
        console.log(milestoneData);
        // setLabelItems(getDropdownItems("label", labelsData));
        // setMilestoneItems(getDropdownItems("milestone", milestoneData));
        setLabelItems(labelsData);
        setMilestoneItems(milestoneData);
        setAssigneeItems(assigneeData);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
      }
    };

    fetchData();
  }, []);

  return (
    <div css={addBoxContainer(color)}>
      <AddBoxItem
        issueId={issueId}
        issueDetail={issueDetail}
        mode={mode}
        title="담당자"
        dropdownItems={assigneeItems}
      />
      <AddBoxItem
        issueId={issueId}
        issueDetail={issueDetail}
        mode={mode}
        title="레이블"
        dropdownItems={labelItems}
      />
      <AddBoxItem
        issueId={issueId}
        issueDetail={issueDetail}
        mode={mode}
        title="마일스톤"
        dropdownItems={milestoneItems}
      />
    </div>
  );
}

// const authorItems = [
//   {
//     id: 1,
//     name: "그랬냥",
//     imgUrl:
//       "https://img.freepik.com/free-photo/adorable-kitty-looking-like-it-want-to-hunt_23-2149167099.jpg?w=2000",
//   },
//   {
//     id: 2,
//     name: "고양선생",
//     imgUrl:
//       "https://cdn.eyesmag.com/content/uploads/posts/2022/08/08/main-ad65ae47-5a50-456d-a41f-528b63071b7b.jpg",
//   },
//   {
//     id: 3,
//     name: "고양학생",
//     imgUrl: "https://src.hidoc.co.kr/image/lib/2022/5/12/1652337370806_0.jpg",
//   },
//   {
//     id: 4,
//     name: "고양시민",
//     imgUrl:
//       "https://i.namu.wiki/i/abZPxKt_L98I8ttqw56pLHtGiR5pAV4YYmpR3Ny3_n0yvff5IDoKEQFof7EbzJUSZ_-uzR5S7tzTzGQ346Qixw.webp",
//   },
//   {
//     id: 5,
//     name: "야옹이다옹",
//     imgUrl:
//       "https://images.mypetlife.co.kr/content/uploads/2023/01/04154159/AdobeStock_101112878-scaled.jpeg",
//   },
// ];
