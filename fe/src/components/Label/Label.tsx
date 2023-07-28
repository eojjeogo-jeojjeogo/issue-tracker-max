import { useTheme } from "@emotion/react";
// import { color } from "../../constants/colors";
import { ColorScheme } from "../../contexts/ThemeContext";
import { LabelType } from "../../pages/LabelPage";
import { fonts } from "../util/Txt";

export function Label({
  label,
  isDark,
  labelTitle,
  randomColor,
  isEditCompleted, // mode,
}: {
  label?: LabelType;
  isDark?: boolean;
  labelTitle?: string;
  randomColor?: string;
  isEditCompleted?: boolean;
  mode?: string;
}) {
  const color = useTheme() as ColorScheme;

  const addModeLabelTitle = labelTitle ? labelTitle : "Label";

  // const textColor = label
  //   ? label.isDark
  //     ? color.neutral.text.weak
  //     : color.brand.text.default
  //   : isDark
  //   ? color.neutral.text.weak
  //   : color.brand.text.default;

  const labelName = isEditCompleted
    ? labelTitle
    : label
    ? label.title
    : addModeLabelTitle;

  return (
    <div
      className="label"
      css={{
        border: label
          ? label.backgroundColor === "#FEFEFE"
            ? `1px solid ${color.neutral.border.default}`
            : "none"
          : `1px solid ${color.neutral.border.default}`,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        textAlign: "center",
        borderRadius: "16px",
        padding: "0px 12px",
        boxSizing: "border-box",
        backgroundColor: randomColor
          ? randomColor
          : label
          ? label.backgroundColor
          : color.palette.offWhite,
        width: "max-content",
        height: "24px",
        whiteSpace: "nowrap",
        ...fonts.medium12,
        color: isDark ? color.neutral.text.weak : color.brand.text.default,
      }}>
      {/* {isEditCompleted ? labelTitle : label ? label.title : addModeLabelTitle} */}
      {labelName}
    </div>
  );
}
