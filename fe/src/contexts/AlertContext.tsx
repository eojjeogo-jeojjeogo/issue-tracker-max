import { createContext, useState, ReactNode } from "react";

type AlertContextType = {
  isLabelAlertOpen: boolean;
  setIsLabelAlertOpen: (isOpen: boolean) => void;
  deleteElementId: number;
  setDeleteElementId: (id: number) => void;
  editElementId: number;
  setEditElementId: (id: number) => void;
  currentType: AlertType;
  setCurrentType: (type: AlertType) => void;
  shouldFetchAgain: boolean;
  setShouldFetchAgain: (shouldFetchAgain: boolean) => void;
};

type AlertProviderProps = {
  children: ReactNode;
};

type AlertType = "label" | "milestone" | "issue" | undefined;

export const AlertContext = createContext<AlertContextType | undefined>(
  undefined
);

export function AlertProvider({ children }: AlertProviderProps) {
  const [isLabelAlertOpen, setIsLabelAlertOpen] = useState(false);
  const [deleteElementId, setDeleteElementId] = useState<number>(-1);
  const [editElementId, setEditElementId] = useState<number>(-1);
  const [currentType, setCurrentType] = useState<AlertType>();

  const [shouldFetchAgain, setShouldFetchAgain] = useState(false);

  const value: AlertContextType = {
    isLabelAlertOpen,
    setIsLabelAlertOpen,
    deleteElementId,
    setDeleteElementId,
    editElementId,
    setEditElementId,
    currentType,
    setCurrentType,
    shouldFetchAgain,
    setShouldFetchAgain,
  };

  return (
    <AlertContext.Provider value={value}>{children}</AlertContext.Provider>
  );
}
