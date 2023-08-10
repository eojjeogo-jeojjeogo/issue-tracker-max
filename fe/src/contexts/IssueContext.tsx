import { createContext, useState, ReactNode } from "react";

type IssueContextType = {
  assigneeList: number[];
  setAssigneeList: React.Dispatch<React.SetStateAction<number[]>>;
  labelList: number[];
  setLabelList: React.Dispatch<React.SetStateAction<number[]>>;
  milestoneList: number[];
  setMilestoneList: React.Dispatch<React.SetStateAction<number[]>>;
  handleSetAssigneeList: (id: number[]) => void;
  handleSetLabelList: (id: number[]) => void;
  handleSetMilestoneList: (id: number[]) => void;
};

type IssueProviderProps = {
  children: ReactNode;
};

export const IssueContext = createContext<IssueContextType | undefined>(
  undefined
);

export function IssueProvider({ children }: IssueProviderProps) {
  const [assigneeList, setAssigneeList] = useState<number[]>([]);
  const [labelList, setLabelList] = useState<number[]>([]);
  const [milestoneList, setMilestoneList] = useState<number[]>([]);

  const handleSetAssigneeList = (id: number[]) => {
    setAssigneeList(id);
  };

  const handleSetLabelList = (id: number[]) => {
    setLabelList(id);
  };

  const handleSetMilestoneList = (id: number[]) => {
    setMilestoneList(id);
  };

  const value: IssueContextType = {
    assigneeList,
    setAssigneeList,
    labelList,
    setLabelList,
    milestoneList,
    setMilestoneList,
    handleSetAssigneeList,
    handleSetLabelList,
    handleSetMilestoneList,
  };

  return (
    <IssueContext.Provider value={value}>{children}</IssueContext.Provider>
  );
}
