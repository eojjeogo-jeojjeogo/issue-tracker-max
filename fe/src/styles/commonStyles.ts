import { ColorScheme } from "../contexts/ThemeContext";
import { fonts } from "../constants/fonts";
import { css } from "@emotion/react";

export const tableContainer = css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  position: relative;
  top: 24px;
`;

export const tableStyle = (color: ColorScheme) => css`
  position: relative;
  display: flex;
  flex-direction: column;
  border-radius: 16px;
  border: 1px solid ${color.neutral.border.default};
  overflow: hidden;
`;

export const tableHeaderStyle = (color: ColorScheme) => css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 32px;
  box-sizing: border-box;
  height: 64px;
  color: ${color.neutral.text.default};
  background-color: ${color.neutral.surface.default};
  ${fonts.bold16},
`;

export const textInputTitle = (color: ColorScheme) => css`
  color: ${color.neutral.text.weak};
  display: flex;
  gap: 8px;
  min-width: 64px;
  ${fonts.medium12};
`;

export const openTabContainer = css`
  display: flex;
  height: 32px;
  justify-content: space-between;
  align-items: center;
  gap: 24px;
`;
