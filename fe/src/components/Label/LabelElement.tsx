import { useContext, useEffect, useState } from "react";
import { Button } from "../common/Button";
// import { fonts } from "../util/Txt";
import { LabelDetail } from "./LabelDetail";
import { LabelType } from "../../pages/LabelPage";
import { Label } from "./Label";
import { ColorScheme } from "../../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { AlertContext } from "../../contexts/AlertContext";
import { fonts } from "../../constants/fonts";

const LabelElementStyle = (color: ColorScheme) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 32px;
  padding: 0 32px;
  box-sizing: border-box;
  width: 100%;
  height: 96px;
  background-color: ${color.neutral.surface.strong};
  border-top: 1px solid ${color.neutral.border.default};
  font-size: ${fonts.medium12.fontSize};
  font-weight: ${fonts.medium12.fontWeight};
`;
const description = (color: ColorScheme) => css`
  display: flex;
  align-items: center;
  width: 870px;
  height: 24px;
  color: ${color.neutral.text.weak};
  font-size: ${fonts.medium16.fontSize};
  font-weight: ${fonts.medium16.fontWeight};
`;

const buttonTab = css`
  display: flex;
  gap: 24px;
  width: 106px;
  height: 32px;
  justify-content: space-between;
  align-items: center;
`;

export function LabelElement({ label }: { label: LabelType }) {
  const [isEditMode, setIsEditMode] = useState(false);

  useEffect(() => {
    if (isEditMode === false) {
    }
  }, [isEditMode]);

  const AlertContextValue = useContext(AlertContext)!;
  const {
    setIsLabelAlertOpen,
    setDeleteElementId,
    setEditElementId,
    setCurrentType,
  } = AlertContextValue!;

  const color = useTheme() as ColorScheme;

  const onClickEditButton = () => {
    setIsEditMode(true);
    setCurrentType("label");
    if (label.id) setEditElementId(label.id);
  };

  const onClickCancelButton = () => {
    setIsEditMode(false);
  };

  const onClickCompleteButton = () => {
    setIsEditMode(false);
  };

  const onClickDeleteButton = () => {
    setIsLabelAlertOpen(true);
    setDeleteElementId(label.id!);
    setCurrentType("label");
  };

  if (isEditMode)
    return (
      <LabelDetail
        mode="edit"
        label={label}
        onClickCompleteButton={onClickCompleteButton}
        onClickCancelButton={onClickCancelButton}
      />
    );

  return (
    <div key={label.id} css={LabelElementStyle(color)}>
      <div className="labelWrapper" css={{ width: "178px", height: "24px" }}>
        <Label isDark={label.isDark} label={label} />
      </div>
      <div className="description" css={description(color)}>
        {label.description}
      </div>
      <div css={buttonTab}>
        <Button
          onClick={onClickEditButton}
          icon="edit"
          type="ghost"
          size="S"
          text="편집"
        />
        <Button
          onClick={onClickDeleteButton}
          icon="trash"
          type="ghost"
          size="S"
          text="삭제"
          textColor={color.danger.text.default}
        />
      </div>
    </div>
  );
}
