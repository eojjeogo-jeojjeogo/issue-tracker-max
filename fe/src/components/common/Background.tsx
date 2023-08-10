import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { DarkModeButton } from "./DarkModeButton";
import { useNavigate } from "react-router-dom";
import { fonts } from "../../constants/fonts";
import { Alert } from "../util/Alert";

const background = (color: ColorScheme) => css`
  display: flex;
  flex-direction: column;
  position: relative;
  margin: 0 auto;
  align-items: center;
  width: 1440px;
  height: 1024px;
  background-color: ${color.neutral.surface.default};
  box-sizing: border-box;
`;

const navTab = (color: ColorScheme) => css`
  width: 225px;
  height: 30px;
  position: absolute;
  bottom: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  color: ${color.neutral.text.weak};
  border: 1px solid ${color.neutral.border.default};
  border-radius: 16px;
  ${fonts.bold16};
`;

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
    <div css={background(color)}>
      {children}
      <DarkModeButton />
      <div css={navTab(color)}>
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
      <Alert />
    </div>
  );
}
