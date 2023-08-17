import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { TabButton } from "../components/common/TabButton";
import { useContext, useEffect, useState } from "react";
import { Button } from "../components/common/Button";
import { LabelDetail } from "../components/Label/LabelDetail";
import { LabelElement } from "../components/Label/LabelElement";
import { useNavigate } from "react-router-dom";
import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../contexts/ThemeContext";

// import { fonts } from "../constants/fonts";
import { tableHeaderStyle, tableStyle } from "../styles/commonStyles";
import { MainArea } from "../components/common/MainArea";
import { TotalCount } from "./MilestonePage";
import { LoadingBar } from "../components/common/LoadingBar";
import { COMMON_URL, LABEL_URL, SERVER } from "../constants/url";
import { AlertContext } from "../contexts/AlertContext";
import { TokenContext } from "../contexts/TokenContext";
// import { TokenContext } from "../contexts/TokenContext";

const tableContainer = css`
  display: flex;
  flex-direction: column;
  gap: 24px;
  position: relative;
  top: 24px;
`;

export type LabelType = {
  id?: number;
  title?: string;
  name?: string;
  description?: string;
  backgroundColor: string;
  color?: string;
  isDark?: boolean;
  textColor?: string | number;
};

export function LabelPage() {
  const [totalCount, setTotalCount] = useState<TotalCount | undefined>();
  const [labels, setLabels] = useState<LabelType[] | undefined>();
  const [loading, setLoading] = useState(true);
  const [isLeftSelected, setIsLeftSelected] = useState(true);
  const [isAddLabelOpen, setIsAddLabelOpen] = useState(false);

  const alertContextValue = useContext(AlertContext)!;
  const { shouldFetchAgain, setShouldFetchAgain } = alertContextValue;

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken } = tokenContextValue;

  const navigate = useNavigate();
  const color = useTheme() as ColorScheme;
  const labelTable = tableStyle(color);
  const labelTableHeader = tableHeaderStyle(color);

  if (!accessToken) navigate("/login");

  // const getCookieValue = (name: string): string | null => {
  //   const value = `; ${document.cookie}`;
  //   const parts = value.split(`; ${name}=`);

  //   // 쿠키 이름에 해당하는 값이 없을 경우, null 반환
  //   if (parts.length < 2) return null;

  //   // 첫 번째 매치된 값만 반환
  //   return parts[1].split(";")[0];
  // };

  useEffect(() => {
    // const accessTokenFromCookie = getCookieValue("accessToken");

    const headers = {
      "Authorization": "Bearer " + accessToken,
      "Content-Type": "application/json",
    };

    const fetchData = async () => {
      try {
        setLoading(true);
        const totalCountResponse = await fetch(`${SERVER}${COMMON_URL}`, {
          headers,
        });
        const labelsResponse = await fetch(`${SERVER}${LABEL_URL}`, {
          headers,
        });
        const totalCountData = await totalCountResponse.json();
        const labelsData = await labelsResponse.json();

        setTotalCount(totalCountData);
        setLabels(labelsData);
        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        navigate("/login");
      }
    };

    fetchData();
    setShouldFetchAgain(false);
  }, [shouldFetchAgain]);

  const leftText = "레이블";
  const rightText = "마일스톤";

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
    navigate("/milestone");
  };

  const onClickLeftTab = () => {
    setIsLeftSelected(true);
  };
  const onClickRightTab = () => {
    setIsLeftSelected(false);
    onClickMilestoneButton();
  };

  const leftTabProps = {
    leftIcon: "label",
    leftText: `${leftText}` + `(${totalCount ? totalCount.labelsCount : ""})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText:
      `${rightText}` + `(${totalCount ? totalCount?.milestonesCount : ""})`,
    onClickRightTab: onClickRightTab,
  };

  if (loading) {
    return (
      <Background>
        <LoadingBar />
        <Header />
      </Background>
    );
  }
  return (
    <Background>
      <Header />
      <MainArea>
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
        <div css={tableContainer}>
          {isAddLabelOpen ? (
            <LabelDetail
              mode="add"
              onClickCompleteButton={onClickCompleteButton}
              onClickCancelButton={onClickCancelButton}
            />
          ) : null}
          <div css={labelTable}>
            <div css={labelTableHeader}>
              {totalCount?.labelsCount}개의 레이블
            </div>
            {labels?.map((label) => (
              <LabelElement key={label.id} label={label} />
            ))}
          </div>
        </div>
      </MainArea>
    </Background>
  );
}
