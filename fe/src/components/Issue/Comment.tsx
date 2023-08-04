import { css, useTheme } from "@emotion/react";

import { ColorScheme } from "../../contexts/ThemeContext";

import { fonts } from "../../constants/fonts";
import { Txt } from "../util/Txt";
import { CommentType } from "../../pages/IssueDetailPage";
import { getTimeDifference } from "./IssueElement";

const container = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  border: 1px solid ${color.neutral.border.default};
  border-radius: 16px;
  box-sizing: border-box;
  width: 912px;
  overflow: hidden;
`;

const header = (color: ColorScheme) => css`
  display: flex;
  justify-content: space-between;
  padding: 16px;
  width: 100%;
  height: 32px;

  background-color: ${color.neutral.surface.default};
`;

const textContent = (color: ColorScheme) => css`
  display: flex;
  width: 912px;
  border: none;
  outline: none;
  padding: 16px 24px 24px 24px;
  min-height: 24px;
  align-items: center;
  // box-sizing: border-box;
  font-size: ${fonts.medium16.fontSize};
  color: ${color.neutral.text.default};
  background-color: ${color.neutral.surface.strong};
`;

export function Comment({ comment }: { comment: CommentType }) {
  const color = useTheme() as ColorScheme;
  return (
    <div css={container(color)}>
      <div css={header(color)}>
        <div css={{ display: "flex", gap: "8px" }}>
          <img
            css={{
              width: "32px",
              height: "32px",
              borderRadius: "50%",
              cover: "fit",
            }}
            src="https://images.mypetlife.co.kr/content/uploads/2023/01/04154159/AdobeStock_101112878-scaled.jpeg"
          />
          <Txt typography="medium16" color={color.neutral.text.default}>
            {comment.author}
          </Txt>
          <Txt typography="medium16" color={color.neutral.text.weak}>
            {getTimeDifference(comment.createdTime)}
          </Txt>
        </div>
      </div>
      <div css={textContent(color)}>{comment.content}</div>
      {/* <TextArea
        placeholder="덧) 선택 미션의 경우, 원래 프로젝트 기간 동안 못하는 것이 (삐빅-)정상입니다."
        css={textContent(color)}></TextArea> */}
    </div>
  );
}
