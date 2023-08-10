import { Txt } from "../components/util/Txt";
import { Button } from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { TextInput } from "../components/common/TextInput";
import { useEffect, useState } from "react";
import { Background } from "../components/common/Background";
import { ColorScheme } from "../contexts/ThemeContext";
import { css, useTheme } from "@emotion/react";
import { Icon } from "../components/common/Icon";
import { LOGIN_CODE_URL, SERVER } from "../constants/url";

const ButtonStyle = (color: ColorScheme) => css`
  margin: 0 auto;
  position: absolute;
  top: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 320px;
  height: 56px;
  color: ${color.brand.text.weak};
  background-color: ${color.neutral.surface.default};
  border: 1px solid ${color.brand.border.default};
  border-radius: 16px;
  box-sizing: border-box;
  cursor: pointer;
`;

const loginInputArea = (color: ColorScheme) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  position: absolute;
  top: 470px;
  width: 100%;
  height: 24px;
  color: ${color.neutral.text.weak};
`;

const inputContainer = css`
  display: flex;
  align-items: center;
  flex-direction: column;
  gap: 16px;
  position: absolute;
  top: 512px;
`;

const loginButton = (color: ColorScheme, isFormValid: boolean) => css`
  position: relative;
  opacity: ${isFormValid ? 1 : 0.3};
  cursor: ${isFormValid ? "pointer" : "auto"};
  display: flex;
  justify-content: center;
  align-items: center;
  width: 320px;
  height: 56px;
  color: ${color.brand.text.weak};
  background-color: ${color.brand.surface.default};
  border: none;
  border-radius: 16px;
`;

// const GITHUB_CLIENT_ID = "a2c2a003c21c55bf1c3c";

// 찰리 client ID
const GITHUB_CLIENT_ID = "f3baa022e59aef7557ea";

// const REDIRECT_URI = "http://localhost:5173/login";
const REDIRECT_URI = "http://54.180.146.130/login/oauth2/code/github";
// const REDIRECT_URI =
//   "http://issue-tracker-team3.s3-website.ap-northeast-2.amazonaws.com/login";

export function LoginPage() {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);

  const isIdValid = id.length >= 6 && id.length <= 12;
  const isPasswordValid = password.length >= 6 && password.length <= 12;

  useEffect(() => {
    if (isIdValid && isPasswordValid) {
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [id, password]);

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");
    console.log(code);
    if (code) {
      sendCodeToServer(code);
    }
  }, []);

  const color = useTheme() as ColorScheme;

  const navigate = useNavigate();

  const onGitHubLoginCLick = () => {
    window.location.href = `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&scope=user`;
    // window.location.href = `https://github.com/login/oauth/authorize?client_id=f3baa022e59aef7557ea&scope=id,name,email,avatar_url`;
  };

  const onClickLogo = () => {
    navigate("/issues");
  };

  const onChangeId = (e: React.ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value);
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onClickRegisterButton = () => {
    navigate("/register");
  };

  const sendCodeToServer = async (code: string) => {
    console.log(code);
    try {
      const response = await fetch(`${SERVER}${LOGIN_CODE_URL}/${code}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ accessCode: code }),
      });
      const data = await response.json();
      console.log(data);
      if (response.ok) {
        localStorage.setItem("token", data.token);
        navigate("/issues");
      }
      navigate("/issues");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Background>
      <div
        onClick={onClickLogo}
        css={{ cursor: "pointer", position: "absolute", top: "264px" }}>
        <Icon type="logoTypeLarge" color={color.neutral.text.strong} />
      </div>
      <button css={ButtonStyle(color)} onClick={onGitHubLoginCLick}>
        <Txt typography="medium20" color={color.brand.text.weak}>
          GitHub 계정으로 로그인
        </Txt>
      </button>
      <div css={loginInputArea(color)}>or</div>
      <div css={inputContainer}>
        <TextInput
          isFormValid={isIdValid}
          inputValue={id}
          caption="아이디는 6자 이상 12자 이하로 입력해주세요."
          onChange={onChangeId}
          width="100%"
          height={56}
          placeholder="아이디"
        />
        <TextInput
          isFormValid={isPasswordValid}
          inputValue={password}
          caption="비밀번호는 6자 이상 12자 이하로 입력해주세요."
          onChange={onChangePassword}
          width="100%"
          height={56}
          placeholder="비밀번호"
        />
        <button css={loginButton(color, isFormValid)}>
          <Txt typography="medium20" color={color.brand.text.default}>
            아이디로 로그인
          </Txt>
        </button>
      </div>
      <div
        onClick={onClickRegisterButton}
        css={{
          top: "728px",
          cursor: "pointer",
          display: "flex",
          justifyContent: "center",
          position: "absolute",
        }}>
        <Button type="ghost" size="M" status="enabled" text="회원가입"></Button>
      </div>
    </Background>
  );
}
