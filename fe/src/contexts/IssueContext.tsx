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
  shouldFetchAgain: boolean;
  setShouldFetchAgain: React.Dispatch<React.SetStateAction<boolean>>;

  selectedLabelFilter: number[];
  setSelectedLabelFilter: React.Dispatch<React.SetStateAction<number[]>>;
  selectedAssigneeFilter: number[];
  setSelectedAssigneeFilter: React.Dispatch<React.SetStateAction<number[]>>;
  selectedMilestoneFilter: number[];
  setSelectedMilestoneFilter: React.Dispatch<React.SetStateAction<number[]>>;
  selectedAuthorFilter: number[];
  setSelectedAuthorFilter: React.Dispatch<React.SetStateAction<number[]>>;
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
  const [shouldFetchAgain, setShouldFetchAgain] = useState<boolean>(false);

  const [selectedLabelFilter, setSelectedLabelFilter] = useState<number[]>([]);
  const [selectedAssigneeFilter, setSelectedAssigneeFilter] = useState<
    number[]
  >([]);
  const [selectedMilestoneFilter, setSelectedMilestoneFilter] = useState<
    number[]
  >([]);
  const [selectedAuthorFilter, setSelectedAuthorFilter] = useState<number[]>(
    []
  );

  const handleSetAssigneeList = (id: number[]) => {
    setAssigneeList(id);
  };

  const handleSetLabelList = (id: number[]) => {
    setLabelList(id);
  };

  const handleSetMilestoneList = (id: number[]) => {
    console.log(id[0]);
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
    shouldFetchAgain,
    setShouldFetchAgain,

    selectedLabelFilter,
    setSelectedLabelFilter,
    selectedAssigneeFilter,
    setSelectedAssigneeFilter,
    selectedMilestoneFilter,
    setSelectedMilestoneFilter,
    selectedAuthorFilter,
    setSelectedAuthorFilter,
  };

  return (
    <IssueContext.Provider value={value}>{children}</IssueContext.Provider>
  );
}
