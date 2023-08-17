import { useContext, useEffect } from "react";
import { LOGIN_CODE_URL, SERVER } from "../constants/url";
import { useNavigate } from "react-router-dom";
import { TokenContext } from "../contexts/TokenContext";
import { Background } from "../components/common/Background";
import { LoadingBar } from "../components/common/LoadingBar";
import { Header } from "../components/Header/Header";

export function Auth() {
  // const [code, _] = useState("");

  const tokenContextValue = useContext(TokenContext)!;
  const { setRefreshToken, setAccessToken, handleSetProfileImage } =
    tokenContextValue;

  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");

    if (code) {
      // setCode(code);
      sendCodeToServer(code);
    }
  }, []);

  const sendCodeToServer = async (code: string) => {
    console.log(code);
    if (code) {
      try {
        const response = await fetch(`${SERVER}${LOGIN_CODE_URL}/github`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ accessCode: code }),
        });
        const data = await response.json();
        console.log(data);
        if (response.ok) {
          // document.cookie = `accessToken=${data.tokens.accessToken};path=/;max-age=3600`; // 1시간 후 만료
          document.cookie = `refreshToken=${data.tokens.refreshToken};path=/;max-age=604800`; // 1주일 후 만료

          setAccessToken(data.tokens.accessToken);
          setRefreshToken(data.tokens.refreshToken);
          handleSetProfileImage(data.imgUrl);

          navigate("/issues");
        }
        // navigate("/issues");
      } catch (error) {
        console.log(error);
      }
    }
  };

  return (
    <Background>
      <LoadingBar />
      <Header />
    </Background>
  );
}
