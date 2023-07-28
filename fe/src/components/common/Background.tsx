// import { color } from "../../constants/colors";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { DarkModeButton } from "./DarkModeButton";
import { useNavigate } from "react-router-dom";
import { fonts } from "../util/Txt";

export function Background({ children }: { children: React.ReactNode }) {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();

  const onClickLogin = () => {
    navigate("/login");
  };

  const onClickRegister = () => {
    navigate("/register");
  };

  const onClickLabel = () => {
    navigate("/label");
  };
  return (
    <>
      <div
        css={{
          display: "flex",
          flexDirection: "column",

          margin: "0 auto",
          position: "relative",
          alignItems: "center",
          width: "1440px",
          height: "1024px",
          backgroundColor: color.neutral.surface.default,
          overflowY: "auto",
          boxSizing: "border-box",
        }}>
        {children}
        <DarkModeButton />
        <div
          css={{
            width: "225px",
            height: "30px",
            position: "absolute",
            bottom: "16px",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            gap: "16px",
            color: color.neutral.text.weak,
            border: `1px solid ${color.neutral.border.default}`,
            borderRadius: "16px",
            ...fonts.bold16,
          }}>
          <div css={{ cursor: "pointer" }} onClick={onClickLogin}>
            로그인
          </div>
          <div css={{ cursor: "pointer" }} onClick={onClickRegister}>
            회원가입
          </div>
          <div css={{ cursor: "pointer" }} onClick={onClickLabel}>
            레이블
          </div>
        </div>
      </div>
    </>
  );
}
