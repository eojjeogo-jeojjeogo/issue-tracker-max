import { useNavigate } from "react-router-dom";
import { Header } from "../components/Header/Header";
import { Background } from "../components/common/Background";
import { useContext, useEffect, useState } from "react";
import { TabButton } from "../components/common/TabButton";
import { Button } from "../components/common/Button";
import { ColorScheme } from "../contexts/ThemeContext";
import { useTheme } from "@emotion/react";
import {
  openTabContainer,
  tableContainer,
  tableHeaderStyle,
  tableStyle,
} from "../styles/commonStyles";
import { MilestoneElement } from "../components/Milestone/MilestoneElement";
import { LoadingBar } from "../components/common/LoadingBar";
import { MilestoneDetail } from "../components/Milestone/MilestoneDetail";
import { MainArea } from "../components/common/MainArea";
import {
  COMMON_URL,
  FILTER_CLOSED_URL,
  FILTER_OPEN_URL,
  MILESTONE_URL,
  SERVER,
} from "../constants/url";
import { AlertContext } from "../contexts/AlertContext";

type MilestonesData = {
  milestoneOpenedCount: number;
  milestoneClosedCount: number;
  milestones: {
    id: number;
    title: string;
    description: string;
    dueDate: string;
    issueClosedCount: number;
    issueOpenedCount: number;
  }[];
};

export type Milestone = MilestonesData["milestones"][0];

export type TotalCount = {
  labelsCount: number;
  milestonesCount: number;
};

export function MilestonePage() {
  const [totalCount, setTotalCount] = useState<TotalCount | undefined>();
  const [milestones, setMilestones] = useState<MilestonesData | undefined>();
  const [loading, setLoading] = useState(true);
  const [isLeftSelected, setIsLeftSelected] = useState(false);
  const [isOpenSelected, setIsOpenSelected] = useState(true);
  const [isAddMilestoneOpen, setIsAddMilestoneOpen] = useState(false);
  const [milestoneStatusChange, setMilestoneStatusChange] = useState(false);

  const alertContextValue = useContext(AlertContext)!;
  const { shouldFetchAgain, setShouldFetchAgain } = alertContextValue;

  const navigate = useNavigate();
  const color = useTheme() as ColorScheme;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const totalCountResponse = await fetch(`${SERVER}${COMMON_URL}`);

        const milestoneResponse = await fetch(
          `${SERVER}${MILESTONE_URL}${
            isOpenSelected ? FILTER_OPEN_URL : FILTER_CLOSED_URL
          }`
        );
        const totalCountData = await totalCountResponse.json();
        const milestoneData = await milestoneResponse.json();

        setTotalCount(totalCountData);
        setMilestones(milestoneData);
        setLoading(false);
      } catch (error) {
        console.error("API 요청 중 에러 발생:", error);
        setLoading(false);
      }
    };

    fetchData();
    setShouldFetchAgain(false);
    setMilestoneStatusChange(false);
  }, [shouldFetchAgain, isOpenSelected, milestoneStatusChange]);

  const AddMilestoneButtonStatus = isAddMilestoneOpen ? "disabled" : "enabled";

  const onClickLabelButton = () => {
    navigate("/label");
  };

  const onClickAddMilestoneButton = () => {
    setIsAddMilestoneOpen(true);
  };

  const onClickCompleteButton = () => {
    setIsAddMilestoneOpen(false);
  };
  const onClickCancelButton = () => {
    setIsAddMilestoneOpen(false);
  };

  const onClickLeftTab = () => {
    setIsLeftSelected(true);
    onClickLabelButton();
  };
  const onClickRightTab = () => {
    setIsLeftSelected(false);
  };

  const onClickOpenTab = () => {
    setIsOpenSelected(true);
  };
  const onClickClosedTab = () => {
    setIsOpenSelected(false);
  };

  const onStatusChange = () => {
    setMilestoneStatusChange(true);
  };

  const leftTabProps = {
    leftIcon: "label",
    leftText: "레이블" + `(${totalCount ? totalCount.labelsCount : ""})`,
    onClickLeftTab: onClickLeftTab,
  };
  const rightTabProps = {
    rightIcon: "milestone",
    rightText:
      "마일스톤" + `(${totalCount ? totalCount?.milestonesCount : ""})`,
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
          <div className="addLabelButton" onClick={onClickAddMilestoneButton}>
            <Button
              status={AddMilestoneButtonStatus}
              type="contained"
              size="S"
              text="마일스톤 추가"
              icon="plus"
            />
          </div>
        </div>
        <div css={tableContainer}>
          {isAddMilestoneOpen ? (
            <MilestoneDetail
              mode="add"
              onClickCompleteButton={onClickCompleteButton}
              onClickCancelButton={onClickCancelButton}
            />
          ) : null}
          <div css={tableStyle(color)}>
            <div css={tableHeaderStyle(color)}>
              <div css={{ display: "flex" }}>
                <div css={openTabContainer}>
                  <Button
                    onClick={onClickOpenTab}
                    status={isOpenSelected ? "selected" : "enabled"}
                    icon="alertCircle"
                    type="ghost"
                    size="M"
                    text={`열린 마일스톤(${
                      milestones ? milestones.milestoneOpenedCount : ""
                    })`}
                  />
                  <Button
                    onClick={onClickClosedTab}
                    status={isOpenSelected ? "enabled" : "selected"}
                    icon="archive"
                    type="ghost"
                    size="M"
                    text={`닫힌 마일스톤(${
                      milestones ? milestones.milestoneClosedCount : ""
                    })`}
                  />
                </div>
              </div>
            </div>
            {milestones?.milestones.map((milestone) => (
              <MilestoneElement
                key={milestone.id}
                onStatusChange={onStatusChange}
                isOpenSelected={isOpenSelected}
                milestones={milestone}
              />
            ))}
          </div>
        </div>
      </MainArea>
    </Background>
  );
}
