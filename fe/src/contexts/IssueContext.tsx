import { createContext, useState, ReactNode } from "react";

type AssigneeType = {
  id: number;
  nickName: string;
  imgUrl: string;
}[];

type LabelType = {
  id: number;
  title: string;
  description: string;
  backgroundColor: string;
  textColor: string;
}[];

type MilestoneType =
  | {
      id: number;
      issueClosedCount: number;
      issueOpenedCount: number;
    }
  | undefined;

type IssueContextType = {
  assigneeList: AssigneeType;
  setAssigneeList: React.Dispatch<React.SetStateAction<AssigneeType>>;
  labelList: LabelType;
  setLabelList: React.Dispatch<React.SetStateAction<LabelType>>;
  milestone: MilestoneType;
  setMilestone: React.Dispatch<React.SetStateAction<MilestoneType>>;
};

type IssueProviderProps = {
  children: ReactNode;
};

export const IssueContext = createContext<IssueContextType | undefined>(
  undefined
);

export function IssueProvider({ children }: IssueProviderProps) {
  const [assigneeList, setAssigneeList] = useState<AssigneeType>([]);
  const [labelList, setLabelList] = useState<LabelType>([]);
  const [milestone, setMilestone] = useState<MilestoneType>();

  const value: IssueContextType = {
    assigneeList,
    setAssigneeList,
    labelList,
    setLabelList,
    milestone,
    setMilestone,
  };

  return (
    <IssueContext.Provider value={value}>{children}</IssueContext.Provider>
  );
}
