import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { fonts } from "./Txt";
import { useContext } from "react";
import { LabelContext } from "../../contexts/LabelContext";
import { Button } from "../common/Button";

export function Alert({ text }: { text: string }) {
  const color = useTheme() as ColorScheme;
  const labelContextValue = useContext(LabelContext)!;
  const { isLabelAlertOpen, setIsLabelAlertOpen } = labelContextValue!;

  if (isLabelAlertOpen === false) return;

  const onClickCancelButton = () => {
    setIsLabelAlertOpen(false);
  };

  const onClickOKbutton = () => {
    console.log("삭제했음");
    setIsLabelAlertOpen(false);
  };

  return (
    <div
      css={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        gap: "24px",
        top: "50%",
        left: "50%",
        width: "424px",
        height: "180px",
        padding: "32px",
        boxSizing: "border-box",
        color: color.neutral.text.strong,
        border: `1px solid ${color.neutral.border.default}`,
        borderRadius: "16px",
        backgroundColor: color.neutral.surface.strong,
        ...fonts.bold20,
        transform: "translate(-50%, -50%)",
      }}>
      <div
        css={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: "100%",
          height: "100%",
        }}>
        {text}
      </div>
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
