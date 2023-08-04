import { Txt } from "../components/util/Txt";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button } from "../components/common/Button";
import { TextInput } from "../components/common/TextInput";
import { Background } from "../components/common/Background";
import { ColorScheme } from "../contexts/ThemeContext";
import { useTheme } from "@emotion/react";
import { Icon } from "../components/common/Icon";
import { fonts } from "../constants/fonts";

export function RegisterPage() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [isFormValid, setIsFormValid] = useState(false);
  const [isWrongPassword, setIsWrongPassword] = useState(false);

  const onClickLogo = () => {
    navigate("/issues");
  };

  const onChangeEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const onChangeId = (e: React.ChangeEvent<HTMLInputElement>) => {
    setId(e.target.value);
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onChangeCheckPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCheckPassword(e.target.value);
  };

  const onClickRegisterButton = () => {
    if (isFormValid) {
      console.log("가입정보", email, id, password, checkPassword); //API 연결전 임시 사용
      navigate("/");
    }
  };

  useEffect(() => {
    if (password !== checkPassword) {
      setIsFormValid(false);
      setIsWrongPassword(true);
      return;
    } else {
      setIsWrongPassword(false);
    }

    if (email && id && password && checkPassword) {
      setIsWrongPassword(false);
      setIsFormValid(true);
    } else {
      setIsFormValid(false);
    }
  }, [email, id, password, checkPassword]);

  return (
    <Background>
      <div
        css={{
          position: "relative",
          display: "flex",
          flexDirection: "column",
          top: "264px",
        }}>
        <div
          onClick={onClickLogo}
          css={{ cursor: "pointer", position: "relative" }}>
          <Icon type="logoTypeLarge" color={color.neutral.text.strong} />
        </div>
        <div
          className="inputContainer"
          css={{
            position: "relative",
            alignItems: "center",
            top: "32px",
            display: "flex",
            flexDirection: "column",
            gap: "16px",
          }}>
          <TextInput
            width="100%"
            height={56}
            placeholder="이메일"
            onChange={onChangeEmail}
          />
          <TextInput
            width="100%"
            height={56}
            placeholder="아이디"
            onChange={onChangeId}
          />
          <TextInput
            width="100%"
            isPassword={true}
            height={56}
            placeholder="비밀번호"
            onChange={onChangePassword}
          />
          <TextInput
            width="100%"
            isPassword={true}
            height={56}
            placeholder="비밀번호 확인"
            onChange={onChangeCheckPassword}
          />
          <button
            onClick={onClickRegisterButton}
            css={{
              opacity: isFormValid ? 1 : 0.3,
              position: "relative",
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
              회원가입
            </Txt>
          </button>
        </div>

        <div
          onClick={onClickLogo}
          css={{
            cursor: "pointer",
            display: "flex",
            justifyContent: "center",
            position: "relative",
            top: "48px",
          }}>
          <Button
            type="ghost"
            size="M"
            status="enabled"
            text="로그인하기"></Button>
        </div>
        {isWrongPassword && (
          <div
            css={{
              display: "flex",
              justifyContent: "center",

              position: "relative",
              top: "48px",
              ...fonts.medium16,
              color: color.danger.border.default,
            }}>
            비밀번호가 일치하지 않습니다.
          </div>
        )}
      </div>
    </Background>
  );
}
