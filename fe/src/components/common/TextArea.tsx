import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useContext, useRef, useState } from "react";
import { InputState } from "./TextInput";
import { fonts } from "../../constants/fonts";
import { Icon } from "./Icon";
import { Txt } from "../util/Txt";
import { SERVER } from "../../constants/url";
import { TokenContext } from "../../contexts/TokenContext";

const TextAreaContainer = ({
  color,
  height,
  inputState,
}: {
  color: ColorScheme;
  height: string;
  inputState: InputState;
}) => css`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 912px;
  height: ${height};
  min-height: 184px;
  border-radius: 16px;
  overflow: hidden;
  box-sizing: border-box;
  border: ${inputState === "active"
    ? `1px solid ${color.neutral.border.defaultActive}`
    : inputState === "error"
    ? `1px solid ${color.danger.border.default}`
    : "none"};
  background-color: ${inputState === "enabled"
    ? color.neutral.surface.bold
    : inputState === "disabled"
    ? color.neutral.surface.bold
    : color.neutral.surface.strong};
`;

const TextAreaStyle = (
  color: ColorScheme,
  areaHeight: string,
  areaMaxHeight: string
) => css`
  width: 100%;
  height: ${areaHeight};
  min-height: 64px;
  max-height: ${areaMaxHeight};
  border: none;
  outline: none;
  resize: vertical;
  padding: 0;
  box-sizing: border-box;
  background-color: transparent;
  overflow: scroll
  color: ${color.neutral.text.default};
  font-size: ${fonts.medium16.fontSize};
  font-weight: ${fonts.medium16.fontWeight};

  ::placeholder {
    color: ${color.neutral.text.weak};
  }
`;

const fileInsertArea = (color: ColorScheme) => css`
  display: flex;
  padding: 0 16px;
  align-items: center;
  width: 100%;
  height: 52px;
  border-top: 1px dashed ${color.neutral.border.default};
`;

const paperclipContainer = css`
  display: flex;
  gap: 4px;
  height: 32px;
  align-items: center;
  cursor: pointer;
`;

export function TextArea({
  height,
  areaHeight,
  areaMaxHeight,
  onChange,
}: {
  height: string;
  areaHeight: string;
  areaMaxHeight: string;
  onChange?: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
  const [inputState, setInputState] = useState<InputState>("enabled");
  const [selectedFileName, setSelectedFileName] = useState<string | null>(null);

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const color = useTheme() as ColorScheme;
  const fileInputRef = useRef<HTMLInputElement>(null);

  const onFocus = () => {
    setInputState("active");
  };

  const onBlur = () => {
    setInputState("enabled");
  };

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      const selectedFile = e.target.files[0];
      setSelectedFileName(selectedFile.name);

      const formData = new FormData();
      formData.append("file", selectedFile);

      try {
        const response = await fetch(`${SERVER}/api/images`, {
          method: "POST",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
          body: formData,
        });

        if (response.ok) {
          console.log("File uploaded successfully");
        } else {
          console.error("Error uploading file");
        }
      } catch (error) {
        console.error("There was an error uploading the file", error);
      }
    }
  };

  const triggerFileInput = () => {
    fileInputRef.current?.click();
  };

  return (
    <>
      <div css={TextAreaContainer({ color, height, inputState })}>
        <div css={{ padding: "16px 16px", paddingBottom: 0 }}>
          <textarea
            onChange={onChange}
            css={TextAreaStyle(color, areaHeight, areaMaxHeight)}
            onFocus={onFocus}
            onBlur={onBlur}
            placeholder="코멘트를 입력하세요"
          />
        </div>
        <div css={fileInsertArea(color)}>
          <div css={paperclipContainer} onClick={triggerFileInput}>
            <Icon type="paperclip" color={color.neutral.text.default} />
            <Txt typography="medium12" color={color.neutral.text.default}>
              파일 첨부하기
            </Txt>
            <input
              type="file"
              ref={fileInputRef}
              onChange={handleFileChange}
              style={{ display: "none" }}
            />
            {selectedFileName && <div>{selectedFileName}</div>}
          </div>
        </div>
      </div>
    </>
  );
}
