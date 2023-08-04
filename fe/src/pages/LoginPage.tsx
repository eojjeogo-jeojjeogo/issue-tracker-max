import { Txt } from "../components/util/Txt";
import { Button } from "../components/common/Button";
import { useNavigate } from "react-router-dom";
import { TextInput } from "../components/common/TextInput";
import { useEffect, useState } from "react";
import { Background } from "../components/common/Background";
import { ColorScheme } from "../contexts/ThemeContext";
import { useTheme } from "@emotion/react";
import { Icon } from "../components/common/Icon";

export function LoginPage() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);

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

  useEffect(() => {
    if (id && password) {
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [id, password]);

  return (
    <Background>
      <div
        onClick={onClickLogo}
        css={{ cursor: "pointer", position: "absolute", top: "264px" }}>
        <Icon type="logoTypeLarge" color={color.neutral.text.strong} />
      </div>
      <button
        css={{
          margin: "0 auto",
          position: "absolute",
          top: "400px",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: "320px",
          height: "56px",
          color: color.brand.text.weak,
          backgroundColor: color.neutral.surface.default,
          border: `1px solid ${color.brand.border.default}`,
          borderRadius: "16px",
          boxSizing: "border-box",
        }}>
        <Txt typography="medium20" color={color.brand.text.weak}>
          GitHub 계정으로 로그인
        </Txt>
      </button>
      <div
        css={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          textAlign: "center",
          position: "absolute",

          top: "470px",
          width: "100%",
          height: "24px",
          color: color.neutral.text.weak,
        }}>
        or
      </div>
      <div
        css={{
          display: "flex",
          alignItems: "center",
          flexDirection: "column",
          gap: "16px",
          position: "absolute",
          top: "512px",
        }}>
        <TextInput
          onChange={onChangeId}
          width="100%"
          height={56}
          placeholder="아이디"
        />
        <TextInput
          onChange={onChangePassword}
          width="100%"
          height={56}
          placeholder="비밀번호"
        />
        <button
          css={{
            position: "relative",
            opacity: isFormValid ? 1 : 0.3,
            cursor: isFormValid ? "pointer" : "",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "320px",
            height: "56px",
            color: color.brand.text.weak,
            backgroundColor: color.brand.surface.default,
            border: "none",
            borderRadius: "16px",
          }}>
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
