import { color } from "../../constants/colors";
import { fonts } from "../util/Txt";
import { Button } from "./Button";

export function TabButton({
  isLeftSelected,
  leftTabProps,
  rightTabProps,
}: {
  isLeftSelected: boolean;
  leftTabProps: {
    leftIcon: string;
    leftText: string;
    onClickLeftTab: () => void;
  };
  rightTabProps: {
    rightIcon: string;
    rightText: string;
    onClickRightTab: () => void;
  };
}) {
  const { leftIcon, leftText, onClickLeftTab } = leftTabProps;
  const { rightIcon, rightText, onClickRightTab } = rightTabProps;

  return (
    <div
      css={{
        display: "flex",
        width: "320px",
        height: "40px",
        justifyContent: "center",
        alignItems: "center",
        borderRadius: "12px",
        border: `1px solid ${color.neutral.border.default}`,
      }}>
      <div
        onClick={onClickLeftTab}
        css={{
          cursor: "pointer",
          display: "flex",
          width: "100%",
          height: "100%",
          justifyContent: "center",
          alignItems: "center",
          borderRight: `1px solid ${color.neutral.border.default}`,
          ...(isLeftSelected ? fonts.bold16 : fonts.medium16),
          backgroundColor: isLeftSelected ? color.neutral.surface.bold : "",
        }}>
        <Button
          textColor={
            isLeftSelected
              ? color.neutral.text.strong
              : color.neutral.text.default
          }
          icon={leftIcon}
          type="ghost"
          size="M"
          text={leftText}
          flexible="flexible"
        />
      </div>
      <div
        onClick={onClickRightTab}
        css={{
          cursor: "pointer",
          display: "flex",
          width: "100%",
          height: "100%",
          justifyContent: "center",
          alignItems: "center",
          ...(isLeftSelected ? fonts.medium16 : fonts.bold16),
          backgroundColor: isLeftSelected ? "" : color.neutral.surface.bold,
        }}>
        <Button
          textColor={
            isLeftSelected
              ? color.neutral.text.default
              : color.neutral.text.strong
          }
          icon={rightIcon}
          type="ghost"
          size="M"
          text={rightText}
          flexible="flexible"
        />
      </div>
    </div>
  );
}
