import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";

import { IssueDetail } from "../../pages/IssueDetailPage";

import { AddBoxItem } from "./AddBoxItem";

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

export function AddBox({
  issueDetail,
  mode,
}: {
  issueDetail?: IssueDetail;
  mode: string;
}) {
  const color = useTheme() as ColorScheme;
  // const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const authorsInDropdown = [
    ...new Set(
      issueDetail?.assignees.map((issue) => {
        return { title: issue.nickName, icon: issue.imgUrl, color: null };
      })
    ),
  ];

  return (
    <div css={addBoxContainer(color)}>
      <AddBoxItem
        mode={mode}
        issueDetail={issueDetail}
        title="담당자"
        items={authorsInDropdown}
      />
      <AddBoxItem
        mode={mode}
        issueDetail={issueDetail}
        title="레이블"
        items={authorsInDropdown}
      />
      <AddBoxItem
        mode={mode}
        issueDetail={issueDetail}
        title="마일스톤"
        items={authorsInDropdown}
      />
    </div>
  );
}
