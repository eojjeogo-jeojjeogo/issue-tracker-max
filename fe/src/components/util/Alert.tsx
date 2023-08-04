import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useContext } from "react";
import { AlertContext } from "../../contexts/AlertContext";
import { Button } from "../common/Button";
import { fonts } from "../../constants/fonts";
import {
  ISSUE_URL,
  LABEL_URL,
  MILESTONE_URL,
  SERVER,
} from "../../constants/url";

const alertStyle = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  gap: 24px;
  top: 50%;
  left: 50%;
  width: 424px;
  height: 180px;
  padding: 32px;
  box-sizing: border-box;
  color: ${color.neutral.text.strong};
  border: 1px solid ${color.neutral.border.default};
  border-radius: 16px;
  background-color: ${color.neutral.surface.strong};
  ...${fonts.bold20};
  transform: translate(-50%, -50%);
`;

const alertContentContainer = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
`;

export function Alert() {
  const color = useTheme() as ColorScheme;
  const AlertContextValue = useContext(AlertContext)!;
  const {
    isLabelAlertOpen,
    setIsLabelAlertOpen,
    deleteElementId,
    currentType,
    setCurrentType,
  } = AlertContextValue!;

  if (isLabelAlertOpen === false) return;

  const onClickCancelButton = () => {
    setIsLabelAlertOpen(false);
  };

  const onClickOKbutton = async () => {
    const selectedUrl = getSelectedUrl(currentType!);
    const url = `${SERVER}${selectedUrl}/${deleteElementId}`;

    try {
      const response = await fetch(url, {
        method: "DELETE",
      });
      if (!response.ok) {
        console.log("fail");
      }
    } catch (error) {
      console.error("API 요청 중 에러 발생:", error);
    } finally {
      setIsLabelAlertOpen(false);
    }

    setIsLabelAlertOpen(false);
    setCurrentType(undefined);
    window.location.reload();
  };

  const getSelectedUrl = (currentType: string) => {
    switch (currentType) {
      case "label":
        return LABEL_URL;
      case "milestone":
        return MILESTONE_URL;
      case "issue":
        return ISSUE_URL;
    }
  };

  const getAlertText = (currentType: string) => {
    switch (currentType) {
      case "label":
        return "정말 이 레이블을 삭제하시겠습니까?";
      case "milestone":
        return "정말 이 마일스톤을 삭제하시겠습니까?";
      case "issue":
        return "정말 이 이슈를 삭제하시겠습니까?";
    }
  };

  const text = getAlertText(currentType!);

  return (
    <div css={alertStyle(color)}>
      <div css={alertContentContainer}>{text}</div>
      <div className="buttonContainer" css={{ display: "flex", gap: "16px" }}>
        <Button
          onClick={onClickCancelButton}
          type="outline"
          size="S"
          text="취소"
        />
        <Button
          onClick={onClickOKbutton}
          type="contained"
          size="S"
          text="확인"
        />
      </div>
    </div>
  );
}
