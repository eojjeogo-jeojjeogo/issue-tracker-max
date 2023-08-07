import { useContext, useEffect, useRef, useState } from "react";
import { LabelType } from "../../pages/LabelPage";
import { DetailTextInput } from "../common/DetailTextInput";
import { Button } from "../common/Button";
import { Icon } from "../common/Icon";
import { Txt } from "../util/Txt";
import { ColorSelector } from "./ColorSelector";
import { Label } from "./Label";
import { ColorScheme } from "../../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { Dropdown } from "../common/Dropdown";
import { fonts } from "../../constants/fonts";
import { useOutsideClick } from "../../hooks/useOutsideClick";
import { LABEL_URL, SERVER } from "../../constants/url";
import { AlertContext } from "../../contexts/AlertContext";
import { colorList } from "../../constants/colors";

const labelDetail = (color: ColorScheme, label?: LabelType) => css`
  display: flex;
  flex-direction: column;
  position: relative;
  width: 100%;
  height: 337px;
  border-top: ${label ? `1px solid ${color.neutral.border.default}` : "none"};
  border: ${label ? "" : `1px solid ${color.neutral.border.default}`};
  border-radius: ${label ? "0" : "16px"};
  background-color: ${color.neutral.surface.strong};
  padding: 32px;
  gap: 24px;
  box-sizing: border-box;
`;

const preview = (color: ColorScheme) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 283px;
  height: 153px;
  border-radius: 11px;
  border: 1px solid ${color.neutral.border.default};
`;

const dropdownContainer = css`
  position: absolute;
  left: 50%;
  transform: translate(-40px, 8px);
`;

const dropdownTitle = (color: ColorScheme) => css`
  display: flex;
  align-items: center;
  font-size: ${fonts.medium16.fontSize};
  font-weight: ${fonts.medium16.fontWeight};
  color: ${color.neutral.text.default};
  height: 32px;
  gap: 4px;
  cursor: pointer;
`;

const buttonContainer = css`
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  background-color: transparent;
`;

const inputContainer = css`
  display: flex;
  flex-direction: column;
  width: 904px;
  gap: 16px;
`;

const TEXT_LIGHT = "밝은색";
const TEXT_DARK = "어두운색";

const textColorFilterItems = [
  { title: TEXT_LIGHT, icon: null, color: null },
  { title: TEXT_DARK, icon: null, color: null },
];

const getRandomColor = () => {
  return colorList[Math.floor(Math.random() * colorList.length)];
};

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
  const [isEditCompleted, setIsEditCompleted] = useState(false);
  const [inputTitle, setInputTitle] = useState(label ? label.title : "");
  const [inputDesc, setInputDesc] = useState(label ? label.description! : "");
  const [isDark, setIsDark] = useState(label ? label.isDark : true);
  const [filterSelected, setFilterSelected] = useState<string>(
    label ? (label.isDark ? TEXT_DARK : TEXT_LIGHT) : TEXT_DARK
  );
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const AlertContextValue = useContext(AlertContext)!;
  const { editElementId } = AlertContextValue!;

  const dropdownRef = useRef<HTMLDivElement>(null);
  const dropdownOpenRef = useRef<HTMLDivElement>(null);


  const alertContextValue = useContext(AlertContext)!;
  const { setShouldFetchAgain } = alertContextValue;


  const color = useTheme() as ColorScheme;
  const [selectedColor, setSelectedColor] = useState<string>(
    label ? label?.backgroundColor : color.palette.offWhite
  );

  useEffect(() => {
    if (filterSelected === TEXT_LIGHT) {
      setIsDark(false);
    } else {
      setIsDark(true);
    }
  }, [filterSelected]);

  useOutsideClick(dropdownRef, [dropdownOpenRef], () => {
    setIsDropdownOpen(!isDropdownOpen);
  });

  const isAddMode = mode === "add";

  const onClickDropdownOption = (item: {
    // 아래와같은 반복되는 객체형태의 타입이 있는데 아직 드롭다운으로 전달할 데이터 구조를 통일하지 못해서 임시방편으로 두고있습니다 ㅠㅠ
    title: string;
    icon: string | null;
    color: string | null;
  }) => {
    setFilterSelected(item.title);
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

  const onClickRefreshButton = () => {
    const selectedColor = getRandomColor();
    setSelectedColor(selectedColor);
  };

  const handleClickCompleteButton = async () => {
    if (isEditCompleted) {

      const payload = {
        title: inputTitle,
        description: inputDesc,
        backgroundColor: selectedColor,
        isDark: filterSelected === "어두운색" ? true : false,
      };

      const url = isAddMode
        ? `${SERVER}${LABEL_URL}`
        : `${SERVER}${LABEL_URL}/${editElementId}`;
      const method = isAddMode ? "POST" : "PATCH";

      try {
        const response = await fetch(url, {
          method: method,
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        });

        if (!response.ok) {
          console.log("레이블 추가 실패");
        }
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
      } finally {
        setIsEditCompleted(false);

      }
    }
    setShouldFetchAgain(true);
    onClickCompleteButton && onClickCompleteButton();

  };

  return (
    <div css={labelDetail(color, label)}>
      <Txt typography="bold20" color={color.neutral.text.strong}>
        {label ? "레이블 편집" : "새로운 레이블 추가"}
      </Txt>
      <div className="contentContainer" css={{ display: "flex", gap: "24px" }}>
        <div css={preview(color)}>
          <Label
            mode={mode}
            isEditCompleted={isEditCompleted}
            labelTitle={inputTitle}
            randomColor={selectedColor}
            label={label}
            isDark={isDark}
          />
        </div>
        <div className="inputContainer" css={inputContainer}>
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
            <div ref={dropdownOpenRef} onClick={onClickDropdownButton}>
              <div css={dropdownTitle(color)}>
                <Txt typography="medium16" color={color.neutral.text.default}>
                  텍스트 색상
                </Txt>
                <div>
                  <Icon type="chevronDown" color={color.neutral.text.default} />
                </div>
              </div>
              <div css={dropdownContainer}>
                <Dropdown
                  ref={dropdownRef}
                  onClick={onClickDropdownOption}
                  isDropdownOpen={isDropdownOpen}
                  title="텍스트 색상"
                  items={textColorFilterItems}
                  multiSelect={false}
                  filterSelected={filterSelected}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="buttonContainer" css={buttonContainer}>
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
