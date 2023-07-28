// import { color } from "../../constants/colors";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
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
  const color = useTheme() as ColorScheme;

  const { leftIcon, leftText, onClickLeftTab } = leftTabProps;
  const { rightIcon, rightText, onClickRightTab } = rightTabProps;

  const leftTextColor = isLeftSelected
    ? color.neutral.text.strong
    : color.neutral.text.default;
  const rightTextColor = isLeftSelected
    ? color.neutral.text.default
    : color.neutral.text.strong;

  const buttonsWrapper = {
    display: "flex",
    width: "320px",
    height: "40px",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: "12px",
    border: `1px solid ${color.neutral.border.default}`,
    overflow: "hidden",
  };

  const leftButtonWrapper = {
    cursor: "pointer",
    display: "flex",
    width: "160px",
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
    borderRight: `1px solid ${color.neutral.border.default}`,
  };

  const rightButtonWrapper = {
    cursor: "pointer",
    display: "flex",
    width: "160px",
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
  };

  return (
    <div css={{ ...buttonsWrapper }}>
      <div
        onClick={onClickLeftTab}
        css={{
          ...leftButtonWrapper,
          ...(isLeftSelected ? fonts.bold16 : fonts.medium16),
          backgroundColor: isLeftSelected ? color.neutral.surface.bold : "",
        }}>
        <Button
          textColor={leftTextColor}
          icon={leftIcon}
          type="ghost"
          size="M"
          text={leftText}
        />
      </div>
      <div
        onClick={onClickRightTab}
        css={{
          ...rightButtonWrapper,
          ...(isLeftSelected ? fonts.medium16 : fonts.bold16),
          backgroundColor: isLeftSelected ? "" : color.neutral.surface.bold,
        }}>
        <Button
          textColor={rightTextColor}
          icon={rightIcon}
          type="ghost"
          size="M"
          text={rightText}
        />
      </div>
    </div>
  );
}
