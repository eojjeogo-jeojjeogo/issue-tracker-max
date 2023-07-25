import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { TabButton } from "../components/common/TabButton";
import { useState } from "react";
import { Button } from "../components/common/Button";
import { color } from "../constants/colors";
import { fonts } from "../components/util/Txt";

export function LabelPage() {
  const labelList = [
    {
      id: 1,
      title: "Label",
      description: "레이블1 설명",
      color: "#FEFEFE",
    },
    {
      id: 2,
      title: "documentation",
      description: "레이블2 설명",
      color: "#0025E6",
    },
    {
      id: 3,
      title: "bug",
      description: "레이블3 설명",
      color: "#FF3B30",
    },
  ];

  const leftText = "레이블";
  const rightText = "마일스톤";
  const labelCount = labelList.length;
  const milestoneCount = 2;

  const [isLeftSelected, setIsLeftSelected] = useState(true);

  const onClickLeftTab = () => {
    setIsLeftSelected(true);
  };
  const onClickRightTab = () => {
    setIsLeftSelected(false);
  };

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
          <Button
            type="contained"
            size="S"
            text="레이블 추가"
            icon="plus"
            flexible="fixed"
          />
        </div>
        <div
          css={{
            position: "relative",
            top: "24px",
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
              backgroundColor: color.neutral.surface.default,
              ...fonts.bold16,
            }}>
            {labelCount}개의 레이블
          </div>
          {labelList.map((label) => {
            return (
              <div
                css={{
                  display: "flex",
                  justifyContent: "space-evenly",
                  alignItems: "center",
                  gap: "32px",
                  width: "100%",
                  height: "96px",
                  backgroundColor: color.neutral.surface.strong,
                  borderTop: `1px solid ${color.neutral.border.default}`,
                  ...fonts.medium12,
                }}>
                <div
                  className="labelWrapper"
                  css={{ width: "178px", height: "24px" }}>
                  <div
                    className="label"
                    css={{
                      border:
                        label.color === "#FEFEFE"
                          ? `1px solid ${color.neutral.border.default}`
                          : "none",
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                      borderRadius: "16px",
                      padding: "0px 12px",
                      boxSizing: "border-box",
                      backgroundColor: label.color,
                      width: "max-content",
                      height: "24px",
                      whiteSpace: "nowrap",
                      ...fonts.medium12,
                      color:
                        label.color === "#FEFEFE"
                          ? color.neutral.text.weak
                          : color.brand.text.default,
                    }}>
                    {label.title}
                  </div>
                </div>
                <div
                  className="description"
                  css={{
                    width: "870px",
                    height: "24px",
                    color: color.neutral.text.weak,
                    ...fonts.medium16,
                  }}>
                  {label.description}
                </div>
                <div
                  className="buttonTab"
                  css={{ display: "flex", width: "106px", height: "32px" }}>
                  <Button
                    icon="edit"
                    type="ghost"
                    size="S"
                    text="편집"
                    flexible="fixed"
                  />
                  <Button
                    icon="trash"
                    type="ghost"
                    size="S"
                    text="삭제"
                    flexible="fixed"
                    textColor={color.danger.text.default}
                  />
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </Background>
  );
}
