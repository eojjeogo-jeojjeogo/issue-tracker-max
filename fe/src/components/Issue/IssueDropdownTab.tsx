import { IssueData } from "../../pages/IssuePage";
import { IssueOption } from "./IssueOption";

const getLabelsInIssue = (issue: IssueData) => {
  const uniqueLabels: { title: string; icon: null; color: string }[] = [];

  issue.issues.forEach((issueItem) => {
    issueItem.labels.forEach((label) => {
      const isDuplicate = uniqueLabels.some(
        (existingLabel) =>
          existingLabel.title === label.title &&
          existingLabel.icon === label.backgroundColor
      );

      if (!isDuplicate) {
        uniqueLabels.push({
          title: label.title,
          icon: null,
          color: label.backgroundColor,
        });
      }
    });
  });
  return uniqueLabels;
};

export function IssueDropdownTab({ issue }: { issue: IssueData }) {
  const authorsInDropdown = [
    ...new Set(
      issue.issues.map((issue) => {
        return { title: issue.author, icon: issue.authorUrl, color: null };
      })
    ),
  ];

  const labelsInDropdown = getLabelsInIssue(issue);

  const milestonesInDropdown = [
    ...new Set(
      issue.issues.map((issue) => {
        return { title: issue.milestoneTitle, icon: null, color: null };
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

  return (
    <div
      css={{
        display: "flex",
        gap: "32px",
        height: "32px",
        alignItems: "center",
      }}>
      <IssueOption title="담당자" items={assigneeItems} />
      <IssueOption title="레이블" items={labelItems} />
      <IssueOption title="마일스톤" items={milestoneItems} />
      <IssueOption title="작성자" items={authorItems} />
    </div>
  );
}
