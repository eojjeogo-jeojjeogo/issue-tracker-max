import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { Button } from "./Button";

const getTextColorFrom = ({
  isLeft,
  isLeftSelected,
  color,
}: {
  isLeft: boolean;
  isLeftSelected: boolean | undefined;
  color: ColorScheme;
}) => {
  if (isLeftSelected === undefined) {
    return color.neutral.text.default;
  } else if (isLeftSelected) {
    return isLeft ? color.neutral.text.strong : color.neutral.text.default;
  } else {
    return isLeft ? color.neutral.text.default : color.neutral.text.strong;
  }
};

const getBackgroundColorFrom = ({
  isLeft,
  isLeftSelected,
  color,
}: {
  isLeft: boolean;
  isLeftSelected: boolean | undefined;
  color: ColorScheme;
}) => {
  if (isLeftSelected === undefined) {
    return {
      backgroundColor: "",
    };
  } else if (isLeftSelected) {
    return {
      backgroundColor: isLeft ? color.neutral.surface.bold : "",
    };
  } else {
    return {
      backgroundColor: isLeft ? "" : color.neutral.surface.bold,
    };
  }
};

const getStatusFrom = ({
  isLeftSelected,
  isLeft,
}: {
  isLeftSelected: boolean | undefined;
  isLeft: boolean;
}) => {
  if (isLeftSelected === undefined) {
    return "enabled";
  }
  if (isLeftSelected) {
    return isLeft ? "selected" : "enabled";
  } else {
    return isLeft ? "enabled" : "selected";
  }
};

export function TabButton({
  isLeftSelected,
  leftTabProps,
  rightTabProps,
}: {
  isLeftSelected?: boolean;
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

  const leftTextColor = getTextColorFrom({
    isLeft: true,
    isLeftSelected,
    color,
  });

  const rightTextColor = getTextColorFrom({
    isLeft: false,
    isLeftSelected,
    color,
  });

  const buttonsContainer = {
    display: "flex",
    width: "320px",
    height: "40px",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: "12px",
    border: `1px solid ${color.neutral.border.default}`,
    overflow: "hidden",
  };

  const buttonWrapper = {
    cursor: "pointer",
    display: "flex",
    width: "160px",
    height: "100%",
    justifyContent: "center",
    alignItems: "center",
  };

  return (
    <div css={{ ...buttonsContainer }}>
      <div
        css={{
          ...buttonWrapper,
          ...getBackgroundColorFrom({ isLeft: true, isLeftSelected, color }),
          borderRight: `1px solid ${color.neutral.border.default}`,
        }}>
        <Button
          status={getStatusFrom({ isLeftSelected, isLeft: true })}
          onClick={onClickLeftTab}
          textColor={leftTextColor}
          icon={leftIcon}
          type="ghost"
          size="M"
          text={leftText}
        />
      </div>
      <div
        css={{
          ...buttonWrapper,
          ...getBackgroundColorFrom({ isLeft: false, isLeftSelected, color }),
        }}>
        <Button
          status={getStatusFrom({ isLeftSelected, isLeft: false })}
          onClick={onClickRightTab}
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
