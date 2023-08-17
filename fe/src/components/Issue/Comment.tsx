import { css, useTheme } from "@emotion/react";

import { ColorScheme } from "../../contexts/ThemeContext";

import { fonts } from "../../constants/fonts";
import { Txt } from "../util/Txt";
import { CommentType } from "../../pages/IssueDetailPage";
import { getTimeDifference } from "./IssueElement";
import { Button } from "../common/Button";
import { useState } from "react";
// import { TextInput } from "../common/TextInput";
// import { TextArea } from "../common/TextArea";

const container = (color: ColorScheme, isEditing: boolean) => css`
  display: flex;
  flex-direction: column;
  border: 1px solid
    ${isEditing
      ? color.neutral.border.defaultActive
      : color.neutral.border.default};
  border-radius: 16px;
  box-sizing: border-box;
  width: 912px;
  overflow: hidden;
`;

const header = (color: ColorScheme) => css`
  display: flex;
  justify-content: space-between;
  padding: 16px;
  width: 912px
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

const buttonContainer = css`
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  background-color: transparent;
`;

export function Comment({
  comment,
  authorId,
}: {
  comment: CommentType;
  authorId: string;
}) {
  const [isEditing, setIsEditing] = useState(false);

  const onClickEditButton = () => {
    setIsEditing(true);
  };

  const onClickCancelButton = () => {
    setIsEditing(false);
  };

  const color = useTheme() as ColorScheme;
  return (
    <>
      <div css={container(color, isEditing)}>
        <div css={header(color)}>
          <div css={{ display: "flex", gap: "8px" }}>
            <img
              css={{
                width: "32px",
                height: "32px",
                borderRadius: "50%",
                cover: "fit",
              }}
              src={comment.authorImg}
            />
            <Txt typography="medium16" color={color.neutral.text.default}>
              {comment.author}
            </Txt>
            <Txt typography="medium16" color={color.neutral.text.weak}>
              {getTimeDifference(comment.createdTime)}
            </Txt>
          </div>
          <div
            css={{
              display: "flex",
              gap: "16px",
              height: "32px",
              justifyContent: "flex-end",
              alignItems: "center",
            }}>
            {authorId === comment.author && (
              <div
                css={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  width: "56px",
                  height: "24px",
                  gap: "16px",
                  ...fonts.medium12,
                  color: color.neutral.text.weak,
                  backgroundColor: color.neutral.surface.bold,
                  borderRadius: "16px",
                  border: `1px solid ${color.neutral.border.default}`,
                  boxSizing: "border-box",
                  padding: "0 8px",
                }}>
                작성자
              </div>
            )}
            <Button
              onClick={onClickEditButton}
              type="ghost"
              size="S"
              text="편집"
              icon="edit"
              textColor={color.neutral.text.default}
            />
            <Button
              type="ghost"
              size="S"
              text="반응"
              icon="smile"
              textColor={color.neutral.text.default}
            />
          </div>
        </div>
        {isEditing ? (
          // <CommentTextArea color={color}/>
          <div></div>
        ) : (
          <div css={textContent(color)}>{comment.content}</div>
        )}
      </div>
      {isEditing ? (
        <div className="buttonContainer" css={buttonContainer}>
          <Button
            onClick={onClickCancelButton}
            type="outline"
            size="S"
            icon="xSquare"
            text="편집 취소"
          />
          <Button
            // onClick={handleClickCompleteButton}
            // status={isEditing ? "enabled" : "disabled"}
            type="contained"
            size="S"
            icon="edit"
            text="편집 완료"
          />
        </div>
      ) : null}
    </>
  );
}

// function CommentTextArea(color: ColorScheme) {
//   return (
//     <>
//       <div css={{}}>
//         <div css={{ padding: "16px 16px", paddingBottom: 0 }}>
//           <textarea
//             onChange={onChange}
//             css={TextAreaStyle(color, areaHeight, areaMaxHeight)}
//             onFocus={onFocus}
//             onBlur={onBlur}
//             placeholder="코멘트를 입력하세요"
//           />
//         </div>
//         <div css={fileInsertArea(color)}>
//           <div css={paperclipContainer} onClick={triggerFileInput}>
//             <Icon type="paperclip" color={color.neutral.text.default} />
//             <Txt typography="medium12" color={color.neutral.text.default}>
//               파일 첨부하기
//             </Txt>
//             <input
//               type="file"
//               ref={fileInputRef}
//               onChange={handleFileChange}
//               style={{ display: "none" }}
//             />
//             {selectedFileName && <div>{selectedFileName}</div>}
//           </div>
//         </div>
//       </div>
//     </>
//   );
// }

// const TextAreaStyle = (
//   color: ColorScheme,
//   areaHeight: string,
//   areaMaxHeight: string
// ) => css`
//   width: 100%;
//   height: ${areaHeight};
//   min-height: 64px;
//   max-height: ${areaMaxHeight};
//   border: none;
//   outline: none;
//   resize: vertical;
//   padding: 0;
//   box-sizing: border-box;
//   background-color: transparent;
//   overflow: scroll
//   color: ${color.neutral.text.default};
//   font-size: ${fonts.medium16.fontSize};
//   font-weight: ${fonts.medium16.fontWeight};

//   ::placeholder {
//     color: ${color.neutral.text.weak};
//   }
// `;

// const fileInsertArea = (color: ColorScheme) => css`
//   display: flex;
//   padding: 0 16px;
//   align-items: center;
//   width: 100%;
//   height: 52px;
//   border-top: 1px dashed ${color.neutral.border.default};
// `;

// const paperclipContainer = css`
//   display: flex;
//   gap: 4px;
//   height: 32px;
//   align-items: center;
//   cursor: pointer;
// `;
