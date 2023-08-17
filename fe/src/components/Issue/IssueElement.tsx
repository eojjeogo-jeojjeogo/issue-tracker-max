import { css, useTheme } from "@emotion/react";
import { fonts } from "../../constants/fonts";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Icon } from "../common/Icon";
import { Txt } from "../util/Txt";
import { Issue } from "../../pages/IssuePage";
import { Label } from "../Label/Label";
import { useNavigate } from "react-router-dom";

const elementContainer = (color: ColorScheme) => css`
  display: flex;
  // justify-content: space-between;
  align-items: center;
  gap: 32px;
  padding: 16px 32px;
  box-sizing: border-box;
  width: 100%;
  height: 96px;
  background-color: ${color.neutral.surface.strong};
  border-top: 1px solid ${color.neutral.border.default};
  ...${fonts.medium12};
`;

export const contentContainer = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 64px;
`;

const userImageStyle = css`
  position: relative;
  right: 22px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
`;

const contentTop = css`
  display: flex;
  align-items: center;
  gap: 8px;
  height: 32px;
`;

const contentBottom = css`
  display: flex;
  align-items: center;
  gap: 16px;
  height: 24px;
`;

export const getTimeDifference = (createdTime: string) => {
  const currentTime = new Date();
  const timeDifference =
    currentTime.getTime() - new Date(createdTime).getTime();

  const minutes = Math.floor(timeDifference / 60000);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);
  const months = Math.floor(days / 30);

  if (months > 0) {
    return `${months}달 전`;
  } else if (days > 0) {
    return `${days}일 전`;
  } else if (hours > 0) {
    return `${hours}시간 전`;
  } else if (minutes > 0) {
    return `${minutes}분 전`;
  } else {
    return "방금 전";
  }
};

export function IssueElement({
  selectedIssues,
  issue,
  onClickIssueSelect,
}: {
  selectedIssues: number[];
  issue: Issue;
  onClickIssueSelect: (item: number) => void;
}) {
  const color = useTheme() as ColorScheme;

  const navigate = useNavigate();

  const onClickCheckBox = () => {
    onClickIssueSelect(issue.id);
  };

  const onClickIssueTitle = () => {
    navigate(`/issues/${issue.id}`);
  };
  return (
    <div key={issue.id} css={elementContainer(color)}>
      <div
        onClick={onClickCheckBox}
        css={{ height: "100%", padding: "8px 0", boxSizing: "border-box" }}>
        <div css={{ width: "16px", height: "16px" }}>
          {selectedIssues.includes(issue.id) ? (
            <Icon type="checkBoxActive" color={color.neutral.surface.strong} />
          ) : (
            <Icon type="checkBoxInitial" color={color.neutral.border.default} />
          )}
        </div>
      </div>
      <div css={contentContainer}>
        <div css={{ display: "flex", flexDirection: "column", gap: "8px" }}>
          <div css={contentTop}>
            <Icon type="alertCircle" color={color.palette.blue} />
            <div onClick={onClickIssueTitle} css={{ cursor: "pointer" }}>
              <Txt typography="medium20" color={color.neutral.text.strong}>
                {/* 이슈 제목 */}
                {issue.title}
              </Txt>
            </div>
            <div css={{ display: "flex", gap: "4px" }}>
              {issue.labels.map((label, idx) => (
                <Label  isDark={label.isDark} key={idx} label={label} />
              ))}
            </div>
          </div>
          <div css={contentBottom}>
            <Txt typography="medium16" color={color.neutral.text.weak}>
              #{issue.number}
            </Txt>
            <Txt typography="medium16" color={color.neutral.text.weak}>
              이 이슈가 {getTimeDifference(issue.createdTime)}, {issue.author}
              님에 의해 작성되었습니다.
            </Txt>
            <div css={{ display: "flex", alignItems: "center", gap: "8px" }}>
              <Icon type="milestone" color={color.neutral.text.weak} />
              <Txt typography="medium16" color={color.neutral.text.weak}>
                {issue.milestoneTitle}
              </Txt>
            </div>
          </div>
        </div>
        <img css={userImageStyle} src={issue.authorUrl} alt="profileImage" />
      </div>
    </div>
  );
}
