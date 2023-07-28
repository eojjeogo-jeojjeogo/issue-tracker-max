import { createContext, useState, ReactNode } from "react";

type LabelContextType = {
  isLabelAlertOpen: boolean;
  setIsLabelAlertOpen: (isOpen: boolean) => void;
  deleteElementId: number;
  setDeleteElementId: (id: number) => void;
};

type LabelProviderProps = {
  children: ReactNode;
};

export const LabelContext = createContext<LabelContextType | undefined>(
  undefined
);

export function LabelProvider({ children }: LabelProviderProps) {
  const [isLabelAlertOpen, setIsLabelAlertOpen] = useState(false);
  const [deleteElementId, setDeleteElementId] = useState<number>(-1);

  const value: LabelContextType = {
    isLabelAlertOpen,
    setIsLabelAlertOpen,
    deleteElementId,
    setDeleteElementId,
  };

  return (
    <LabelContext.Provider value={value}>{children}</LabelContext.Provider>
  );
}
