import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { TabButton } from "../components/common/TabButton";
import { useState } from "react";
import { Button } from "../components/common/Button";
// import { color } from "../constants/colors";
import { fonts } from "../components/util/Txt";
import { LabelDetail } from "../components/Label/LabelDetail";
import { LabelElement } from "../components/Label/LabelElement";
import { useNavigate } from "react-router-dom";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../contexts/ThemeContext";
import { Alert } from "../components/util/Alert";

export type LabelType = {
  id: number;
  title: string;
  description: string;
  backgroundColor: string;
  isDark: boolean;
};

export function LabelPage() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();
  const [isLeftSelected, setIsLeftSelected] = useState(true);
  const [isAddLabelOpen, setIsAddLabelOpen] = useState(false);
  const AddLabelButtonStatus = isAddLabelOpen ? "disabled" : "enabled";

  const onClickAddLabelButton = () => {
    setIsAddLabelOpen(true);
  };
  const onClickCompleteButton = () => {
    setIsAddLabelOpen(false);
  };
  const onClickCancelButton = () => {
    setIsAddLabelOpen(false);
  };

  const onClickMilestoneButton = () => {
    console.log("마일스톤 버튼 클릭");
    navigate("/milestone");
  };

  const onClickLeftTab = () => {
    setIsLeftSelected(true);
  };
  const onClickRightTab = () => {
    setIsLeftSelected(false);
    onClickMilestoneButton();
  };

  const leftText = "레이블";
  const rightText = "마일스톤";
  const labelCount = labelList.length;
  const milestoneCount = 2;

  const leftTabProps = {
    leftIcon: "label",
    leftText: `${leftText}` + `(${labelCount})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText: `${rightText}` + `(${milestoneCount})`,
    onClickRightTab: onClickRightTab,
  };

  return (
    <Background>
      <Header />
      <div
        css={{
          display: "flex",
          width: "100%",
          height: "100%",
          boxSizing: "border-box",
          flexDirection: "column",
          padding: "32px 80px",
        }}>
        <div css={{ display: "flex", justifyContent: "space-between" }}>
          <TabButton
            isLeftSelected={isLeftSelected}
            leftTabProps={leftTabProps}
            rightTabProps={rightTabProps}
          />
          <div className="addLabelButton" onClick={onClickAddLabelButton}>
            <Button
              status={AddLabelButtonStatus}
              type="contained"
              size="S"
              text="레이블 추가"
              icon="plus"
            />
          </div>
        </div>
        <div
          css={{
            display: "flex",
            flexDirection: "column",
            gap: "24px",
            position: "relative",
            top: "24px",
          }}>
          {isAddLabelOpen ? (
            <LabelDetail
              mode="add"
              onClickCompleteButton={onClickCompleteButton}
              onClickCancelButton={onClickCancelButton}
            />
          ) : null}
          <div
            css={{
              position: "relative",
              // top: "24px",
              display: "flex",
              flexDirection: "column",
              borderRadius: "16px",
              border: `1px solid ${color.neutral.border.default}`,
              overflow: "hidden",
            }}>
            <div
              css={{
                display: "flex",
                alignItems: "center",
                padding: "0 32px",
                boxSizing: "border-box",
                height: "64px",
                color: color.neutral.text.default,
                backgroundColor: color.neutral.surface.default,
                ...fonts.bold16,
              }}>
              {labelCount}개의 레이블
            </div>
            {labelList.map((label) => {
              return <LabelElement key={label.id} label={label} />;
            })}
          </div>
        </div>
      </div>
      <Alert text="정말 이 레이블을 삭제하시겠습니까?" />
    </Background>
  );
}

const labelList = [
  {
    id: 1,
    title: "Label",
    description: "레이블1 설명",
    backgroundColor: "#FEFEFE",
    isDark: true,
  },
  {
    id: 2,
    title: "documentation",
    description: "레이블2 설명",
    backgroundColor: "#0025E6",
    isDark: false,
  },
  {
    id: 3,
    title: "bug",
    description: "레이블3 설명",
    backgroundColor: "#FF3B30",
    isDark: false,
  },
];
