import ProfileImage from "../../assets/ProfileImage.png";
import { css, useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useNavigate } from "react-router-dom";
import { Icon } from "../common/Icon";

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
`;
export function Header() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();

  const onClickLogo = () => {
    navigate("/issues");
    window.location.reload();
  };
  return (
    <div css={headerStyle}>
      <div onClick={onClickLogo} css={logoStyle}>
        <Icon type="logoTypeMedium" color={color.neutral.text.strong} />
      </div>
      <img src={ProfileImage} alt="profile" css={imageStyle} />
    </div>
  );
}
