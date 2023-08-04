import { css, useTheme } from "@emotion/react";
import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { MainArea } from "../components/common/MainArea";
import { fonts } from "../constants/fonts";
import { ColorScheme } from "../contexts/ThemeContext";
import ProfileImage from "../assets/ProfileImage.png";
import { TextInput } from "../components/common/TextInput";

import { AddBox } from "../components/Issue/AddBox";
import { TextArea } from "../components/common/TextArea";
import { Button } from "../components/common/Button";
import { Icon } from "../components/common/Icon";
import { Txt } from "../components/util/Txt";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const newIssueContentContainer = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: space-between;
  width: 100%;
  height: 664px;
  box-sizing: border-box;
  border-top: 1px solid ${color.neutral.border.default};
  border-bottom: 1px solid ${color.neutral.border.default};
`;

const newIssueMainContent = css`
  display: flex;
  width: 100%;
  height: 616px;
  gap: 24px;

  box-sizing: border-box;
`;

const userImageStyle = css`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
`;

const inputContainer = css`
  display: flex;
  flex-direction: column;
  width: 912px;
  height: 616px;
  gap: 8px;
`;

export function NewIssuePage() {
  const [inputTitle, setInputTitle] = useState<string>("");
  const [inputContent, setInputContent] = useState<string>("");
  const [isValidate, setIsValidate] = useState<boolean>(false);

  // const [assigneesList, setAssigneesList] = useState<string[]>([]);
  // const [labelsList, setLabelsList] = useState<string[]>([]);
  // const [milestoneList, setMilestoneList] = useState<string[]>([]);

  // const addBoxValue = {
  //   assigneesList,
  //   setAssigneesList,
  //   labelsList,
  // };

  const navigate = useNavigate();

  const onClickCancelButton = () => {
    navigate("/issues");
  };

  const onClickCompleteButton = () => {
    console.log(inputTitle, inputContent); //API 연결 전 임시사용
  };

  const onChangeInputTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputTitle(e.target.value);
    if (e.target.value.length > 0 && inputContent.length > 0) {
      setIsValidate(true);
    } else setIsValidate(false);
  };

  const onChangeTextAreaContent = (
    e: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setInputContent(e.target.value);
    if (e.target.value.length > 0 && inputTitle.length > 0) {
      setIsValidate(true);
    } else setIsValidate(false);
  };

  const color = useTheme() as ColorScheme;
  return (
    <Background>
      <Header />
      <MainArea>
        <div css={{ display: "flex", flexDirection: "column", gap: "24px" }}>
          <div css={{ width: "100%", height: "48px", ...fonts.bold32 }}>
            새로운 이슈 작성
          </div>
          <div css={newIssueContentContainer(color)}>
            <div css={newIssueMainContent}>
              <div>
                <img css={userImageStyle} src={ProfileImage} alt="profile" />
              </div>
              <div css={inputContainer}>
                <TextInput
                  onChange={onChangeInputTitle}
                  placeholder="제목"
                  width="912px"
                  height={56}
                />
                <TextArea
                  onChange={onChangeTextAreaContent}
                  height="552px"
                  areaHeight="500px"
                  areaMaxHeight="480px"
                />
              </div>
              <AddBox mode="add" />
            </div>
          </div>
          <div
            css={{ display: "flex", gap: "32px", justifyContent: "flex-end" }}>
            <div
              onClick={onClickCancelButton}
              css={{
                cursor: "pointer",
                display: "flex",
                gap: "4px",
                alignItems: "center",
              }}>
              <Icon type="xSquare" color={color.neutral.text.default} />
              <Txt typography="medium16" color={color.neutral.text.default}>
                작성 취소
              </Txt>
            </div>
            <Button
              onClick={onClickCompleteButton}
              type="contained"
              size="L"
              status={isValidate ? "enabled" : "disabled"}
              text="완료"
            />
          </div>
        </div>
      </MainArea>
    </Background>
  );
}
