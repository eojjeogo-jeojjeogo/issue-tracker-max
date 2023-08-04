import { css, useTheme } from "@emotion/react";

import { ColorScheme } from "../../contexts/ThemeContext";
import { Milestone } from "../../pages/MilestonePage";
import { DetailTextInput } from "../common/DetailTextInput";
import { Txt } from "../util/Txt";
import { Button } from "../common/Button";
import { useContext, useState } from "react";
import { MILESTONE_URL, SERVER } from "../../constants/url";
import { AlertContext } from "../../contexts/AlertContext";

const milestoneDetailStyle = (color: ColorScheme, milestone?: Milestone) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  width: 100%;
  height: 337px;
  border-top: ${milestone
    ? `1px solid ${color.neutral.border.default}`
    : "none"};
  border: ${milestone ? "" : `1px solid ${color.neutral.border.default}`};
  border-radius: ${milestone ? "0" : "16px"};
  background-color: ${color.neutral.surface.strong};
  padding: 32px;
  gap: 24px;
  box-sizing: border-box;
`;

const inputContainer = css`
  display: flex;
  flex-direction: column;
  width: 1216px;
  gap: 16px;
`;

const buttonContainer = css`
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  background-color: transparent;
`;

export function MilestoneDetail({
  mode,
  milestone,
  onClickCancelButton,
  onClickCompleteButton,
}: {
  mode: string;
  milestone?: Milestone;
  onClickCancelButton?: () => void;
  onClickCompleteButton?: () => void;
}) {
  const [isEditCompleted, setIsEditCompleted] = useState(false);
  const [inputTitle, setInputTitle] = useState(
    milestone ? milestone.title : ""
  );
  const [inputDesc, setInputDesc] = useState(
    milestone ? milestone.description : ""
  );
  const [inputDueDay, setInputDueDay] = useState(
    milestone ? milestone.dueDay : ""
  );

  const AlertContextValue = useContext(AlertContext)!;
  const { editElementId } = AlertContextValue;

  const color = useTheme() as ColorScheme;

  const onChangeTitleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputTitle(e.target.value);
    if (e.target.value.length > 0 && inputDesc.length > 0) {
      setIsEditCompleted(true);
    } else {
      setIsEditCompleted(false);
    }
  };

  const onChangeDescInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputDesc(e.target.value);
    if (e.target.value.length > 0 && inputTitle.length > 0) {
      setIsEditCompleted(true);
    } else {
      setIsEditCompleted(false);
    }
  };

  const onChangeDueDayInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputDueDay(e.target.value);
  };

  const isAddMode = mode === "add";

  const handleClickCompleteButton = async () => {
    if (isEditCompleted) {
      const milestoneToSend = {
        title: inputTitle,
        description: inputDesc,
        dueDate: inputDueDay,
      };

      try {
        const response = await fetch(
          isAddMode
            ? `${SERVER}${MILESTONE_URL}`
            : `${SERVER}${MILESTONE_URL}/${editElementId}`,
          {
            method: isAddMode ? "POST" : "PATCH",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(milestoneToSend),
          }
        );

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
      }
    }
    onClickCompleteButton && onClickCompleteButton();
  };

  return (
    <div css={milestoneDetailStyle(color, milestone)}>
      <Txt typography="bold20" color={color.neutral.text.strong}>
        {milestone ? "마일스톤 편집" : "새로운 마일스톤 추가"}
      </Txt>
      <div className="contentContainer" css={{ display: "flex", gap: "24px" }}>
        <div css={inputContainer}>
          <div css={{ display: "flex", gap: "16px" }}>
            <DetailTextInput
              onChange={onChangeTitleInput}
              mode={mode}
              title="이름"
              inputText={inputTitle}
              placeholder="마일스톤의 이름을 입력하세요"
            />
            <DetailTextInput
              onChange={onChangeDueDayInput}
              mode={mode}
              title="완료일(선택)"
              inputText={inputDueDay}
              placeholder="YYYY. MM. DD"
            />
          </div>
          <DetailTextInput
            onChange={onChangeDescInput}
            mode={mode}
            title="설명(선택)"
            inputText={inputDesc}
            placeholder="마일스톤에 대한 설명을 입력하세요"
          />
        </div>
      </div>
      <div className="buttonContainer" css={buttonContainer}>
        <div className="cancelButton" onClick={onClickCancelButton}>
          <Button type="outline" size="S" icon="xSquare" text="취소" />
        </div>
        <Button
          onClick={handleClickCompleteButton}
          status={isEditCompleted ? "enabled" : "disabled"}
          type="contained"
          size="S"
          icon="plus"
          text="완료"
        />
      </div>
    </div>
  );
}
