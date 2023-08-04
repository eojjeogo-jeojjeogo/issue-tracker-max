import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { LabelType } from "../../pages/LabelPage";
import { fonts } from "../../constants/fonts";

type Props = {
  label?: LabelType;
  isDark?: boolean;
  labelTitle?: string;
  randomColor?: string;
  isEditCompleted?: boolean;
  mode?: string;
};

const getLabelName = (
  //서버에서 받는 데이터 구조가 아직 통일되지 않아서 한 번에 예외처리를 다 해야해서 통일 후 수정 예정
  label?: LabelType,
  labelTitle?: string,
  isEditCompleted?: boolean
) => {
  const defaultLabelTitle = labelTitle || "Label";
  if (isEditCompleted) return labelTitle;
  if (label) {
    if (label.title) return label.title;
    if (label.name) return label.name;
  }
  return defaultLabelTitle;
};

const getChosenColor = ({
  randomColor,
  label,
  color,
}: {
  randomColor?: string;
  label?: LabelType;
  color: ColorScheme;
}) => {
  if (randomColor) {
    return randomColor;
  } else {
    if (label) {
      return label.backgroundColor;
    } else {
      return color.palette.offWhite;
    }
  }
};

const labelStyle = (
  color: ColorScheme,
  label: any,
  chosenColor?: string,

  isDark?: boolean
) => css`
  border: ${label
    ? label.backgroundColor === "#FEFEFE"
      ? `1px solid ${color.neutral.border.default}`
      : "none"
    : `1px solid ${color.neutral.border.default}`};
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 16px;
  padding: 0px 12px;
  box-sizing: border-box;
  background-color: ${chosenColor};
  width: max-content;
  height: 24px;
  white-space: nowrap;
  font-size: ${fonts.medium12.fontSize};
  font-weight: ${fonts.medium12.fontWeight};

  color: ${isDark ? color.neutral.text.weak : color.brand.text.default};
`;

export function Label({
  isDark,
  label,
  labelTitle,
  randomColor,
  isEditCompleted,
}: Props) {
  const color = useTheme() as ColorScheme;

  const labelName = getLabelName(label, labelTitle, isEditCompleted);

  const chosenColor = getChosenColor({
    randomColor,
    label,
    color,
  });

  return (
    <div css={labelStyle(color, label, chosenColor, isDark)}>{labelName}</div>
  );
}
