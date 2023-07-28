import { useEffect, useState } from "react";
// import { color } from "../../constants/colors";
import { LabelType } from "../../pages/LabelPage";
import { DetailTextInput } from "../common/DetailTextInput";
import { Button } from "../common/Button";
import { Icon } from "../common/Icon";
import { Txt, fonts } from "../util/Txt";
import { ColorSelector } from "./ColorSelector";
import { Label } from "./Label";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useTheme } from "@emotion/react";
import { Dropdown } from "../common/Dropdown";

export function LabelDetail({
  mode,
  label,
  onClickCancelButton,
  onClickCompleteButton,
}: {
  mode: string;
  label?: LabelType;
  onClickCancelButton?: () => void;
  onClickCompleteButton?: () => void;
}) {
  const color = useTheme() as ColorScheme;
  const [selectedColor, setSelectedColor] = useState<string>(
    label ? label?.backgroundColor : color.palette.offWhite
  );
  const [isEditCompleted, setIsEditCompleted] = useState(false);
  const [inputTitle, setInputTitle] = useState(label ? label.title : "");
  const [inputDesc, setInputDesc] = useState(label ? label.description : "");
  const [isDark, setIsDark] = useState(label ? label.isDark : true);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const onClickDropdownOption = () => {
    setIsDark(!isDark);
  };

  const onClickDropdownButton = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const onChangeTitleInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputTitle(e.target.value);
    if (e.target.value.length > 0 && inputDesc.length > 0) {
      setIsEditCompleted(true);
    } else {
      setIsEditCompleted(false);
    }
  };

  const onChangeDescInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputDesc(e.target.value);
    if (e.target.value.length > 0 && inputTitle.length > 0) {
      setIsEditCompleted(true);
    } else {
      setIsEditCompleted(false);
    }
  };

  const handleClickCompleteButton = () => {
    if (isEditCompleted) {
      onClickCompleteButton && onClickCompleteButton();
      console.log(
        `{
      "title": ${inputTitle},
      "description": ${inputDesc} ,
      "backgroundColor": ${selectedColor},
      "isDark": ${true}
    }`
      );
    }
  };

  const getRandomColor = () => {
    return colorList[Math.floor(Math.random() * colorList.length)];
  };

  const onClickRefreshButton = () => {
    const selectedColor = getRandomColor();
    setSelectedColor(selectedColor);
  };

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      const eventTarget = e.target as HTMLElement;

      if (isDropdownOpen && eventTarget.closest(".dropdown") === null) {
        onClickDropdownButton();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isDropdownOpen, onClickDropdownButton]);

  const colorList = [
    "#B60205",
    "#D93F0B",
    "#FBCA04",
    "#0E8A16",
    "#006B75",
    "#1D76DB",
    "#0052CC",
    "#5319E7",
    "#E99695",
    "#F9D0C4",
    "#FEF2C0",
    "#C2E0C6",
    "#BEDADC",
    "#C4DEF6",
    "#BED3F3",
    "#D4C4FB",
  ];

  return (
    <div
      css={{
        display: "flex",
        flexDirection: "column",
        position: "relative",
        width: "100%",
        height: "337px",
        borderTop: label ? `1px solid ${color.neutral.border.default}` : "none",
        border: label ? "" : `1px solid ${color.neutral.border.default}`,
        borderRadius: label ? "0" : "16px",
        backgroundColor: color.neutral.surface.strong,
        padding: "32px",
        gap: "24px",
        boxSizing: "border-box",
      }}>
      <Txt typography="bold20" color={color.neutral.text.strong}>
        {label ? "레이블 편집" : "새로운 레이블 추가"}
      </Txt>
      <div className="contentContainer" css={{ display: "flex", gap: "24px" }}>
        <div
          className="preview"
          css={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "283px",
            height: "153px",
            borderRadius: "11px",
            border: `1px solid ${color.neutral.border.default}`,
          }}>
          <Label
            mode={mode}
            isEditCompleted={isEditCompleted}
            labelTitle={inputTitle}
            randomColor={selectedColor}
            label={label}
            isDark={isDark}
          />
        </div>
        <div
          className="inputContainer"
          css={{
            display: "flex",
            flexDirection: "column",
            width: "904px",
            gap: "16px",
          }}>
          <DetailTextInput
            mode={mode}
            title="이름"
            inputText={inputTitle}
            onChange={onChangeTitleInput}
            placeholder="레이블의 이름을 입력하세요"
          />
          <DetailTextInput
            mode={mode}
            title="설명(선택)"
            inputText={inputDesc}
            onChange={onChangeDescInput}
            placeholder="레이블에 대한 설명을 입력하세요"
          />
          <div
            className="colorSelectorContainer"
            css={{ display: "flex", gap: "24px", alignItems: "center" }}>
            <ColorSelector
              onClickRefreshButton={onClickRefreshButton}
              randomColor={selectedColor}
            />
            <div>
              <div
                css={{
                  display: "flex",
                  alignItems: "center",
                  ...fonts.medium16,
                  color: color.neutral.text.default,
                  height: "32px",
                  gap: "4px",
                }}>
                텍스트 색상
                <div
                  css={{ cursor: "pointer" }}
                  onClick={onClickDropdownButton}>
                  <Icon type="chevronDown" color={color.neutral.text.default} />
                </div>
              </div>
              <Dropdown
                onClick={onClickDropdownOption}
                isDark={isDark}
                isDropdownOpen={isDropdownOpen}
                title="텍스트 색상"
                items={dropdownItems}
                multiSelect={false}
              />
            </div>
          </div>
        </div>
      </div>
      <div
        className="buttonContainer"
        css={{
          display: "flex",
          justifyContent: "flex-end",
          gap: "16px",
          backgroundColor: "transparent",
        }}>
        <div className="cancelButton" onClick={onClickCancelButton}>
          <Button type="outline" size="S" icon="xSquare" text="취소" />
        </div>
        <Button
          onClick={handleClickCompleteButton}
          status={isEditCompleted ? "enabled" : "disabled"}
          type="contained"
          size="S"
          icon="plus"
          text={label ? "편집 완료" : "완료"}
        />
      </div>
    </div>
  );
}

const dropdownItems = ["밝은색", "어두운색"];
