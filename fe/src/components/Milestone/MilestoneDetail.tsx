import { css, useTheme } from "@emotion/react";

import { ColorScheme } from "../../contexts/ThemeContext";
import { Milestone } from "../../pages/MilestonePage";
import { DetailTextInput } from "../common/DetailTextInput";
import { Txt } from "../util/Txt";
import { Button } from "../common/Button";
import { useContext, useEffect, useState } from "react";
import { MILESTONE_URL, SERVER } from "../../constants/url";
import { AlertContext } from "../../contexts/AlertContext";
import { TokenContext } from "../../contexts/TokenContext";

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

const isValidDate = (dateStr: string) => {
  const parts = dateStr.split("-");

  if (parts.length < 3) {
    return false;
  }

  const year = parseInt(parts[0], 10);
  const month = parseInt(parts[1], 10);
  const day = parseInt(parts[2], 10);

  if (month < 1 || month > 12) {
    return false;
  }

  if (day < 1 || day > 31) {
    return false;
  }

  if (month === 2) {
    if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
      if (day > 29) {
        return false;
      }
    } else {
      if (day > 28) {
        return false;
      }
    }
  }

  if ((month === 4 || month === 6 || month === 9 || month === 11) && day > 30) {
    return false;
  }

  return true;
};

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
    milestone ? milestone.dueDate : ""
  );
  const [isDateValid, setIsDateValid] = useState(false);

  const AlertContextValue = useContext(AlertContext)!;
  const { editElementId, setShouldFetchAgain } = AlertContextValue;

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const color = useTheme() as ColorScheme;

  useEffect(() => {
    if ((isAddMode && inputTitle && isDateValid) || inputDesc) {
      setIsEditCompleted(true);
    } else {
      setIsEditCompleted(false);
    }
  }, [inputTitle, inputDesc, isDateValid]);

  const onChangeTitleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputTitle(e.target.value);
  };

  const onChangeDescInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputDesc(e.target.value);
  };

  const onChangeDueDayInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;

    let onlyDigits = value.replace(/[^0-9]/g, "");

    if (onlyDigits.length > 8) {
      onlyDigits = onlyDigits.substring(0, 8);
    }

    let formattedValue = onlyDigits;

    if (onlyDigits.length > 4) {
      formattedValue =
        onlyDigits.substring(0, 4) + "-" + onlyDigits.substring(4);
    }

    if (onlyDigits.length > 6) {
      formattedValue =
        formattedValue.substring(0, 7) + "-" + formattedValue.substring(7);
    }

    setInputDueDay(formattedValue);
    if (isValidDate(formattedValue)) {
      setIsDateValid(true);
    } else {
      setIsDateValid(false);
    }
  };

  const isAddMode = mode === "add";

  const formatToDotSeparatedDate = (dueDate: string) => {
    return dueDate.replace(/-/g, ".");
  };

  const handleClickCompleteButton = async () => {
    if (isEditCompleted) {
      const checkedInputDueDate = formatToDotSeparatedDate(inputDueDay);

      const milestoneToSend = {
        title: inputTitle,
        description: inputDesc,
        dueDate: checkedInputDueDate,
      };

      try {
        const response = await fetch(
          isAddMode
            ? `${SERVER}${MILESTONE_URL}`
            : `${SERVER}${MILESTONE_URL}/${editElementId}`,
          {
            method: isAddMode ? "POST" : "PATCH",
            headers: {
              "Authorization": "Bearer " + accessToken,
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
      setShouldFetchAgain(true);
      onClickCompleteButton && onClickCompleteButton();
    }
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
              isDate={true}
              onChange={onChangeDueDayInput}
              mode={mode}
              title="완료일(선택)"
              inputText={inputDueDay}
              placeholder="YYYY.MM.DD"
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
