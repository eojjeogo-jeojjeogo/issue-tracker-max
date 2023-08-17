// import ProfileImage from "../../assets/ProfileImage.png";
import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useNavigate } from "react-router-dom";
import { Icon } from "../common/Icon";
import { useContext } from "react";
import { TokenContext } from "../../contexts/TokenContext";
import { fonts } from "../../constants/fonts";

const headerStyle = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 27px 80px;
  width: 100%;
  height: 94px;
  box-sizing: border-box;
`;

const logoStyle = css`
  width: 199px;
  height: 40px;
  cursor: pointer;
  position: relative;
`;

const imageStyle = css`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
`;
export function Header() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();

  const tokenContextValue = useContext(TokenContext)!;
  const { accessToken, profileImage } = tokenContextValue;

  // useEffect(() => {
  //   if (!accessToken) navigate("/login");
  // }, [accessToken]);

  const onClickLogo = () => {
    navigate("/issues");
  };

  const onClickProfile = () => {
    navigate("/profile");
  };
  return (
    <div css={headerStyle}>
      <div onClick={onClickLogo} css={logoStyle}>
        <Icon type="logoTypeMedium" color={color.neutral.text.strong} />
      </div>
      {accessToken ? (
        <img
          onClick={onClickProfile}
          src={profileImage}
          alt="profile"
          css={imageStyle}
        />
      ) : (
        <div css={{ ...fonts.medium12, color: color.neutral.text.weak }}>
          로그인이 필요합니다.
        </div>
      )}
    </div>
  );
}
