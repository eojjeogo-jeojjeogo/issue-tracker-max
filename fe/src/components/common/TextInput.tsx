import { useState } from "react";
import { color } from "../../constants/colors";
import { fonts } from "../util/Txt";

export function TextInput({
  isPassword,
  height,
  placeholder,
  onChange,
}: {
  isPassword?: boolean;
  height: number;
  placeholder: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}) {
  const [inputState, setInputState] = useState<
    "enabled" | "active" | "disabled" | "error"
  >("enabled");

  const onFocus = () => {
    setInputState("active");
  };

  const onBlur = () => {
    setInputState("enabled");
  };

  return (
    <>
      <div
        css={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "start",
          position: "relative",
          width: "320px",
          height: `${height}px`,
          border:
            inputState === "active"
              ? `1px solid ${color.neutral.border.defaultActive}`
              : inputState === "error"
              ? `1px solid ${color.danger.border.default}}`
              : "none",
          padding: "0 16px",
          borderRadius: "16px",
          boxSizing: "border-box",
          backgroundColor:
            inputState === "enabled"
              ? color.neutral.surface.bold
              : inputState === "disabled"
              ? color.neutral.surface.bold
              : color.neutral.surface.strong,
        }}>
        {inputState === "active" && (
          <div
            css={{
              ...fonts.medium12,

              color: color.neutral.text.weak,
            }}>
            {placeholder}
          </div>
        )}
        <input
          onFocus={onFocus}
          onBlur={onBlur}
          onChange={onChange}
          placeholder={placeholder}
          css={{
            ...fonts.medium16,
            outline: "none",
            width: "288px",
            height: "24px",
            color:
              inputState === "enabled"
                ? color.neutral.text.default
                : inputState === "disabled"
                ? color.neutral.text.weak
                : color.neutral.text.strong,
            border: "none",
            backgroundColor: "transparent",
          }}
          type={isPassword ? "password" : "text"}
        />
      </div>
    </>
  );
}
