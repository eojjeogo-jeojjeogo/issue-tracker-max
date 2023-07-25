import LogotypeMedium from "../../assets/LogotypeMedium.svg";
import ProfileImage from "../../assets/ProfileImage.png";

export function Header() {
  return (
    <div
      css={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        padding: "27px 80px",
        width: "100%",
        height: "94px",
        boxSizing: "border-box",
      }}>
      <div css={{ position: "relative", width: "199px", height: "40px" }}>
        <img
          src={LogotypeMedium}
          alt="logo"
          css={{
            maxWidth: "100%",
            height: "auto",
          }}
        />
      </div>
      <img
        src={ProfileImage}
        alt="profile"
        css={{
          width: "32px",
          height: "32px",
          borderRadius: "50%",
          objectFit: "cover",
        }}
      />
    </div>
  );
}
